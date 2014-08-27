package com.example.projdiab2014;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Actividade_Opcoes extends ActionBarActivity {
	private Intent intent2;
	private String tiporef;
	DBAdapter myDb;

	// Add button
	Button ref, cat, ali;

	// Input text
	EditText inputLabel, inputLabel_talimento, inputLabel_alimento,
			inputLabel_caloria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividade_opcoes);

		openDB();

		intent2 = getIntent();

		// permitir que navegue ate Home
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		// add button
		ref = (Button) findViewById(R.id.ref);
		cat = (Button) findViewById(R.id.cat);
		ali = (Button) findViewById(R.id.ali);

		// new label input field
		inputLabel = (EditText) findViewById(R.id.input_label);

		/**
		 * Add new label button click listener
		 * */
		ref.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(Actividade_Opcoes.this,
						Actividade_OpcoesRefeicao.class);
				startActivity(intent);
				finish();

			}
		});

		cat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(Actividade_Opcoes.this,
						Actividade_OpcoesCategoria.class);
				startActivity(intent);
				finish();

			}
		});

		ali.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(Actividade_Opcoes.this,
						Actividade_OpcoesAlimento.class);
				startActivity(intent);
				// finish();

			}
		});

	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// On selecting a spinner item
		String label = parent.getItemAtPosition(position).toString();

		// Showing selected spinner item
		Toast.makeText(parent.getContext(), "You selected: " + label,
				Toast.LENGTH_LONG).show();

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

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
