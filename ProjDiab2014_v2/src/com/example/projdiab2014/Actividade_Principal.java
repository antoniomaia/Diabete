package com.example.projdiab2014;

import java.io.IOException;
import java.util.Calendar;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Actividade_Principal extends ActionBarActivity {

	private ImageButton btn1;
	private Intent intent2;
	private EditText valorglicemico;
	private TextView peqalmoco, almoco, jantar, refjantar, refalmoco,
			refpeqalmoco;
	DBAdapter myDb;
	private ImageButton chamar_emerg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividade_principal);

		AssetDatabaseHelper dbHelper = new AssetDatabaseHelper(
				getBaseContext(), DBAdapter.DATABASE_NAME);
		try {
			dbHelper.importIfNotExist();
		} catch (IOException e) {
			e.printStackTrace();
		}

		openDB();

		// enable the home button
		ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		btn1 = (ImageButton) findViewById(R.id.button1);

		valorglicemico = (EditText) findViewById(R.id.editText2);
		peqalmoco = (TextView) findViewById(R.id.peqalmoco);
		almoco = (TextView) findViewById(R.id.almoco);
		jantar = (TextView) findViewById(R.id.jantar);
		refjantar = (TextView) findViewById(R.id.crefjantar);
		refalmoco = (TextView) findViewById(R.id.crefalmoco);
		refpeqalmoco = (TextView) findViewById(R.id.crefpeqalmoco);
		chamar_emerg = (ImageButton) findViewById(R.id.chamarEmerg);

		preencherTabela();

		btn1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				String valor = valorglicemico.getText().toString();
				Double valor2;

				String msg = null;

				try {
					valor2 = Double.parseDouble(valor);

					if (valor2 < 70) {
						msg = "Valor alarmante!";
					} else if (valor2 >= 70 && valor2 < 110) {
						msg = "Valor recomendavel em jejum!";
					} else if (valor2 >= 110 && valor2 < 145) {
						msg = "Valor recomendavel para 1 a 2 horas apos as refeicoes!";
					} else if (valor2 >= 145) {
						msg = "Nivel de glicemia esta alto (hiperglicemia)!";
					}

					myDb.insertRow(valor2);
					preencherTabela();
					valorglicemico.setText("");

				} catch (NumberFormatException e) {
					msg = "Introduza um valor!";
				}
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
						.show();
				// Hiding the keyboard
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(valorglicemico.getWindowToken(), 0);

			}

		});

		chamar_emerg.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String contacto = null;
				Cursor cpegaEmerg = myDb.pegaEmerg();
				if (cpegaEmerg.moveToFirst()) {
					contacto = cpegaEmerg.getString(0);
				}
				cpegaEmerg.close();

				if (contacto.length() == 9) {
					try {

						Intent call = new Intent(Intent.ACTION_CALL);

						call.setData(Uri.parse("tel:" + contacto));
						startActivity(call);

					} catch (ActivityNotFoundException activityException) {
						Log.e("", "Chamada falhou");

					}
				} else {
					Toast.makeText(getApplicationContext(), "Número inválido",
							Toast.LENGTH_SHORT).show();
				}

				Toast.makeText(getApplicationContext(), "A iniciar chamada",
						Toast.LENGTH_SHORT).show();

			}

		});

	}

	public void preencherTabela() {
		Calendar c = Calendar.getInstance();
		int ano = c.get(Calendar.YEAR);
		int mes = c.get(Calendar.MONTH) + 1;
		int dia = c.get(Calendar.DAY_OF_MONTH);

		String cpeqalmocomed = null;
		String mes2 = null;
		if (mes <= 9) {
			mes2 = "0" + mes;
		}
		Cursor cpeqalmoco = myDb.calcpeqalmoco(ano, mes2, dia);

		if (cpeqalmoco.moveToFirst() == true) {
			cpeqalmocomed = cpeqalmoco.getString(0);

			if (cpeqalmocomed == null) {
				peqalmoco.setText("0");

			} else {
				Double a = Double.parseDouble(cpeqalmocomed);
				peqalmoco.setText(String.format("%.1f", a));
			}

		}

		cpeqalmoco.close();

		Cursor calmoco = myDb.calcalmoco(ano, mes2, dia);
		String valor = null;
		if (cpeqalmoco.moveToFirst()) {
			valor = calmoco.getString(0);

			if (valor == null) {
				almoco.setText("0");

			} else {
				Double a = Double.parseDouble(valor);
				almoco.setText(String.format("%.1f", a));
			}
		}
		calmoco.close();

		Cursor cjantar = myDb.calcjantar(ano, mes2, dia);

		String valor2 = null;
		if (cjantar.moveToFirst()) {
			valor2 = cjantar.getString(0);

			if (valor2 == null) {
				jantar.setText("0");

			} else {
				Double a = Double.parseDouble(valor2);
				jantar.setText(String.format("%.1f", a));
			}
		}
		cjantar.close();

		Cursor crefjantar = myDb.calcrefjantar(ano, mes2, dia);

		String valor3 = null;
		if (crefjantar.moveToFirst()) {
			valor3 = crefjantar.getString(0);

			if (valor3 == null) {
				refjantar.setText("0");

			} else {
				Double a = Double.parseDouble(valor3);
				refjantar.setText(String.format("%.1f", a));
			}
		}
		crefjantar.close();

		Cursor crefalmoco = myDb.calcrefalmoco(ano, mes2, dia);

		String valor4 = null;
		if (crefalmoco.moveToFirst()) {
			valor4 = crefalmoco.getString(0);

			if (valor4 == null) {
				refalmoco.setText("0");

			} else {
				Double a = Double.parseDouble(valor4);
				refalmoco.setText(String.format("%.1f", a));
			}
		}
		crefalmoco.close();

		Cursor crefpeqalmoco = myDb.calcrefpeqalmoco(ano, mes2, dia);

		String valor5 = null;
		if (crefpeqalmoco.moveToFirst()) {
			valor5 = crefpeqalmoco.getString(0);

			if (valor5 == null) {
				refpeqalmoco.setText("0");

			} else {
				Double a = Double.parseDouble(valor5);
				refpeqalmoco.setText(String.format("%.1f", a));
			}
		}
		crefpeqalmoco.close();

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

		}

		return super.onOptionsItemSelected(item);

	}
}
