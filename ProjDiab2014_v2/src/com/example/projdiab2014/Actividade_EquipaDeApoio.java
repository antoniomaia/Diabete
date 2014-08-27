package com.example.projdiab2014;

import com.example.projdiab2014.DBAdapter;

import android.support.v7.app.ActionBarActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class Actividade_EquipaDeApoio extends ActionBarActivity {

	private Spinner spinner_medicos;
	private Intent intent2;
	private ImageButton botao, botao1, botao2;
	private EditText especialidade, nomemedico, contacto;
	private String medico;
	private String numero;

	private ArrayAdapter<String> adapter;
	DBAdapter myDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividade_equipadeapoio);

		openDB();

		// permitir que navegue ate Home

		getSupportActionBar().setHomeButtonEnabled(true);

		intent2 = getIntent();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		botao = (ImageButton) findViewById(R.id.inserir);
		botao1 = (ImageButton) findViewById(R.id.apagar);
		botao2 = (ImageButton) findViewById(R.id.buttonCall);

		especialidade = (EditText) findViewById(R.id.especialidade);
		nomemedico = (EditText) findViewById(R.id.nomemedico);
		contacto = (EditText) findViewById(R.id.contacto);
		spinner_medicos = (Spinner) findViewById(R.id.medicos);

		loadSpinnerDataEspecialidade();

		botao.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				String especialidade1 = especialidade.getText().toString();
				String nomemedico1 = nomemedico.getText().toString();
				String contacto1 = contacto.getText().toString();

				if (!(especialidade1.matches("") && nomemedico1.matches("") && contacto1
						.matches(""))) {

					Cursor cursorEspecialidade = myDb.getMedico(especialidade1);
					String esp = null;

					if (cursorEspecialidade.moveToFirst()) {
						esp = cursorEspecialidade.getString(0);
					}

					cursorEspecialidade.close();

					if (esp == null) {

						try {

							myDb.insertRowMedico(especialidade1, nomemedico1,
									contacto1);

							Toast.makeText(getApplicationContext(),
									"Inserido com sucesso", Toast.LENGTH_SHORT)
									.show();
							especialidade.setText("");
							nomemedico.setText("");
							contacto.setText("");

						} catch (NumberFormatException e) {

						}
					} else {
						Toast.makeText(getApplicationContext(), "Ja existe",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Preencha o formul‡rio", Toast.LENGTH_SHORT).show();
				}

				loadSpinnerDataEspecialidade();

			}

		});

		spinner_medicos.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {

				medico = parent.getItemAtPosition(position).toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		botao1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if (!(medico == null)) {

					try {

						myDb.deleteRowEspecialidade(medico);

						Toast.makeText(getApplicationContext(),
								"Apagado com sucesso", Toast.LENGTH_SHORT)
								.show();

					} catch (NumberFormatException e) {

					}

					loadSpinnerDataEspecialidade();
					startActivity(intent2);
				} else {
					Toast.makeText(getApplicationContext(),
							"Sem elementos para apagar", Toast.LENGTH_SHORT)
							.show();
				}
			}

		});

		botao2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if (!(medico == null)) {

					Cursor cursorMedico = myDb.getContactoMedico(medico);

					if (cursorMedico.moveToFirst()) {
						numero = cursorMedico.getString(0);
					}
					cursorMedico.close();

					if (numero.length() == 9) {
						try {

							Intent call = new Intent(Intent.ACTION_CALL);

							call.setData(Uri.parse("tel:" + numero));
							startActivity(call);

						} catch (ActivityNotFoundException activityException) {
							Log.e("", "Chamada falhou");

						}
					} else {
						Toast.makeText(getApplicationContext(),
								"Nœmero inv‡lido", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "Sem nœmero",
							Toast.LENGTH_SHORT).show();
				}

				Toast.makeText(getApplicationContext(), "A iniciar chamada",
						Toast.LENGTH_SHORT).show();
			}

		});

		loadSpinnerDataEspecialidade();

		spinner_medicos.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {

				medico = parent.getItemAtPosition(position).toString();

				mostraMedicos(medico);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

	}

	private void mostraMedicos(String medico) {

		ListView listaDados = (ListView) findViewById(R.id.dados);
		List<String> Medico = new ArrayList<String>();

		Cursor cursorMedico = myDb.getDadosMedico(medico);

		while (cursorMedico.isAfterLast() == false) {

			String nome = cursorMedico.getString(2);
			String contacto = cursorMedico.getString(3);

			String a = "Nome: " + nome + "\n" + "Contacto: " + contacto;

			Medico.add(a);

			cursorMedico.moveToNext();
		}

		cursorMedico.close();

		adapter

		= new ArrayAdapter<String>(this,

		android.R.layout.simple_list_item_1,

		Medico);

		listaDados.setAdapter(adapter);

	}

	private void loadSpinnerDataEspecialidade() {
		// database handler
		DBAdapter db = new DBAdapter(getApplicationContext());

		// Spinner Drop down elements
		List<String> lables = db.getAllLabelsMedicos();

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner_medicos.setAdapter(dataAdapter);
		// mostraMedicos(medico);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDB();
	}

	private void openDB() {
		myDb = new DBAdapter(this);
		myDb.open();
	}

	private void closeDB() {
		myDb.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.diab2014_activity_1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case (R.id.action_menuhist):
			intent2 = new Intent(this, Actividade_MenuHistorico.class);
			startActivity(intent2);
			break;

		case (R.id.action_opcoes):
			intent2 = new Intent(this, Actividade_Opcoes.class);
			startActivity(intent2);
			break;

		case (R.id.action_sair):
			System.exit(0);
			break;

		case (R.id.action_infpe):
			intent2 = new Intent(this, Actividade_InformacaoPessoal.class);
			startActivity(intent2);
			finish();
			break;

		case (R.id.action_equi):
			intent2 = new Intent(this, Actividade_EquipaDeApoio.class);
			startActivity(intent2);
			finish();
			break;

		case (R.id.action_ref):
			intent2 = new Intent(this, Actividade_Refeicao.class);
			startActivity(intent2);
			finish();
			break;

		case (R.id.action_graf):
			intent2 = new Intent(this, Actividade_Grafico.class);
			startActivity(intent2);
			finish();
			break;

		case android.R.id.home:
			Intent intent = new Intent(this, Actividade_Principal.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}
