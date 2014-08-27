package com.example.projdiab2014;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Actividade_OpcoesAlimento extends ActionBarActivity {
	private Intent intent2;
	private String tipoali, alimento;
	private ArrayAdapter<String> adapter;
	private String array_spinner[];
	DBAdapter myDb;
	private Spinner listaAlimentos;

	ImageButton addalimento, removealimento;

	// Spinner element
	Spinner spinner_talimento, spinner_alimento;

	// Input text
	EditText inputLabel_talimento, inputLabel_alimento, inputLabel_caloria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividade_opcoesalimento);

		openDB();

		intent2 = getIntent();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		// permitir que navegue ate Home
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		listaAlimentos = (Spinner) findViewById(R.id.spinner_alimento);
		// Spinner element
		spinner_talimento = (Spinner) findViewById(R.id.spinner_talimento);

		loadSpinnerData_talimento();

		spinner_talimento
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v,
							int position, long id) {

						tipoali = parent.getItemAtPosition(position).toString();

						mostraAlimentos(tipoali);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		addalimento = (ImageButton) findViewById(R.id.btn_add_alimento);
		removealimento = (ImageButton) findViewById(R.id.btnRemove_alimento);

		inputLabel_alimento = (EditText) findViewById(R.id.input_label_alimento);
		inputLabel_caloria = (EditText) findViewById(R.id.input_label_caloria);

		// mostraAlimentos(tipoali);

		/**
		 * Add new label button click listener
		 * */
		addalimento.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String label = null;
				Double label2 = null;
				try {
					label = inputLabel_alimento.getText().toString();
					label2 = Double.parseDouble(inputLabel_caloria.getText()
							.toString());
				} catch (NumberFormatException e) {
					Toast.makeText(getApplicationContext(),
							"Preencha o formul‡rio", Toast.LENGTH_SHORT).show();
				}

				if (label.trim().length() > 0) {
					// database handler
					DBAdapter db = new DBAdapter(getApplicationContext());

					// inserting new label into database
					db.insertLabel_alimento(label, label2, tipoali);

					Toast.makeText(getApplicationContext(),
							"Inserido com sucesso", Toast.LENGTH_SHORT).show();

					// making input filed text to blank
					inputLabel_alimento.setText("");
					inputLabel_caloria.setText("");

					// Hiding the keyboard
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							inputLabel_alimento.getWindowToken(), 0);

					// loading spinner with newly added data
					mostraAlimentos(tipoali);
					// loadSpinnerData_alimento();
				} else {
					Toast.makeText(getApplicationContext(),
							"Preencha o formul‡rio", Toast.LENGTH_SHORT).show();
				}

			}
		});

		listaAlimentos.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {

				alimento = parent.getItemAtPosition(position).toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

		removealimento.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				try {

					myDb.deleteRowAlimento(alimento);

					Toast.makeText(getApplicationContext(),
							"Apagado com sucesso", Toast.LENGTH_SHORT).show();

				} catch (Exception e) {

				}

				mostraAlimentos(tipoali);

			}
		});

	}

	private void mostraAlimentos(String talimento) {

		Cursor cursor_alimento = myDb.getAlimento(talimento);
		array_spinner = new String[cursor_alimento.getCount()];

		int i = 0;
		while (cursor_alimento.isAfterLast() == false) {

			String alimen = cursor_alimento.getString(0);

			array_spinner[i] = alimen;
			i++;
			cursor_alimento.moveToNext();

		}
		cursor_alimento.close();

		ArrayAdapter<String> alimentos = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, array_spinner);
		alimentos
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		listaAlimentos.setAdapter(alimentos);

	}

	private void loadSpinnerData_talimento() {
		// database handler
		DBAdapter db = new DBAdapter(getApplicationContext());

		// Spinner Drop down elements
		List<String> lables = db.getAllLabels_talimento();

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner_talimento.setAdapter(dataAdapter);

		mostraAlimentos(tipoali);
	}

	public void onBackPressed() {
		intent2 = new Intent(this, Actividade_Opcoes.class);
		startActivity(intent2);
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
			break;

		case (R.id.action_equi):
			intent2 = new Intent(this, Actividade_EquipaDeApoio.class);
			startActivity(intent2);
			break;

		case (R.id.action_ref):
			intent2 = new Intent(this, Actividade_Refeicao.class);
			startActivity(intent2);
			break;
		case (R.id.action_graf):
			intent2 = new Intent(this, Actividade_Grafico.class);
			startActivity(intent2);
			break;

		case (R.id.action_menuhist):
			intent2 = new Intent(this, Actividade_MenuHistorico.class);
			startActivity(intent2);
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
