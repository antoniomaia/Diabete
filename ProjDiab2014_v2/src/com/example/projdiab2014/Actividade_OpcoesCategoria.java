package com.example.projdiab2014;

import java.util.List;

import android.content.Context;
import android.content.Intent;
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

public class Actividade_OpcoesCategoria extends ActionBarActivity {
	private Intent intent2;
	private String tipoali;
	DBAdapter myDb;

	// Spinner element
	Spinner spinner_talimento;

	// Add button
	ImageButton btnAdd_talimento, btnRemove_talimento;

	// Input text
	EditText inputLabel_talimento;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividade_opcoescategoria);

		openDB();

		intent2 = getIntent();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		// permitir que navegue ate Home
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		// Spinner element
		spinner_talimento = (Spinner) findViewById(R.id.spinner_talimento);

		// add button
		btnAdd_talimento = (ImageButton) findViewById(R.id.btn_add_talimento);
		btnRemove_talimento = (ImageButton) findViewById(R.id.btnRemove_talimento);

		// new label input field
		inputLabel_talimento = (EditText) findViewById(R.id.input_label_talimento);

		// // Spinner click listener
		// spinner_talimento = (Spinner) findViewById(R.id.spinner);

		// Loading spinner data from database
		loadSpinnerData_talimento();

		/**
		 * Add new label button click listener
		 * */
		btnAdd_talimento.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String label = inputLabel_talimento.getText().toString();

				if (label.trim().length() > 0) {
					// database handler
					DBAdapter db = new DBAdapter(getApplicationContext());

					// inserting new label into database
					db.insertLabel_talimento(label);

					Toast.makeText(getApplicationContext(),
							"Inserido com sucesso", Toast.LENGTH_SHORT).show();

					// making input filed text to blank
					inputLabel_talimento.setText("");

					// Hiding the keyboard
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							inputLabel_talimento.getWindowToken(), 0);

					// loading spinner with newly added data
					loadSpinnerData_talimento();
				} else {
					Toast.makeText(getApplicationContext(),
							"Preencha o formul‡rio", Toast.LENGTH_SHORT).show();
				}

			}

		});

		spinner_talimento
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v,
							int position, long id) {

						tipoali = parent.getItemAtPosition(position).toString();

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		btnRemove_talimento.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				try {

					myDb.deleteRowCategoriaAlimento(tipoali);
					Toast.makeText(getApplicationContext(),
							"Apagado com sucesso", Toast.LENGTH_SHORT).show();

				} catch (NumberFormatException e) {

				}

				loadSpinnerData_talimento();
			}
		});

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
