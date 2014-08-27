package com.example.projdiab2014;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Actividade_Refeicao extends ActionBarActivity {
	private Intent intent2;
	private Spinner spinner_tiporef, spinner_talimento;
	DBAdapter myDb;
	private String talimento, trefeicao;
	private Button btnLista;
	private ListView listView, listaFinal;
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> adapter2;
	private ImageButton btnguardar;
	private String str = "";
	private float calorias = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividade_refeicao);

		intent2 = getIntent();

		openDB();

		// permitir que navegue ate Home
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		// Spinner element
		spinner_tiporef = (Spinner) findViewById(R.id.spinner_tiporef);
		spinner_talimento = (Spinner) findViewById(R.id.spinner_talimento);
		btnLista = (Button) findViewById(R.id.btnLista);
		listView = (ListView) findViewById(R.id.list);
		listaFinal = (ListView) findViewById(R.id.listafinal);
		btnguardar = (ImageButton) findViewById(R.id.button1);

		listView.setEmptyView(findViewById(R.id.emptyResults));
		listaFinal.setEmptyView(findViewById(R.id.emptyResults2));

		// Loading spinner data from database
		loadSpinnerData();
		loadSpinnerData_talimento();

		adapter2 = new ArrayAdapter<String>(Actividade_Refeicao.this,
				android.R.layout.simple_list_item_1);
		listaFinal.setAdapter(adapter2);

		spinner_tiporef.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {

				trefeicao = parent.getItemAtPosition(position).toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spinner_talimento
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v,
							int position, long id) {

						talimento = parent.getItemAtPosition(position)
								.toString();

						mostraAlimentos(talimento);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		btnLista.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// if(!(adapter2.isEmpty())){
				SparseBooleanArray checked = listView.getCheckedItemPositions();
				ArrayList<String> selectedItems = new ArrayList<String>();

				if (!(checked == null)) {
					for (int i = 0; i < checked.size(); i++) {
						// Item position in adapter
						int position = checked.keyAt(i);
						// Add sport if it is checked i.e.) == TRUE!
						if (checked.valueAt(i))
							selectedItems.add(adapter.getItem(position));
					}
				}

				String strSeparator = ",";
				String separaString = null;
				for (int i = 0; i < selectedItems.size(); i++) {
					separaString = (selectedItems.get(i)).substring(0,
							selectedItems.get(i).indexOf(" "));

					str = str + separaString + strSeparator;
					adapter2.add(selectedItems.get(i));
					adapter2.notifyDataSetChanged();

				}

			}
		});

		btnguardar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// String msg = null;

				// try {

				int firstVisibleRow = listaFinal.getFirstVisiblePosition();
				int lastVisibleRow = listaFinal.getLastVisiblePosition();
				String position = null;
				String separaString = null;
				for (int i = firstVisibleRow; i <= lastVisibleRow; i++) {
					// Item position in adapter
					position = listaFinal.getItemAtPosition(i).toString();

					separaString = position.substring(0,
							position.indexOf("    "));

					Cursor cursor = myDb.getCaloria(separaString);
					while (cursor.isAfterLast() == false) {

						calorias = calorias
								+ Float.parseFloat(cursor.getString(0));

						cursor.moveToNext();
					}
					cursor.close();
				}

				if (calorias > 0 && !(trefeicao == null)) {
					myDb.inserirRefeicao(trefeicao, str, calorias);
					launchRingDialog(arg0);

					// listaFinal.setAdapter(null);
				} else {
					Toast.makeText(getApplicationContext(),
							"Preencha a refeição", Toast.LENGTH_SHORT).show();
				}
			}

		});

		listaFinal
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						adapter2.remove((String) listaFinal.getAdapter()
								.getItem(position));
					}
				});

	}

	private void mostraAlimentos(String talimento) {

		ListView listaAlimentos = (ListView) findViewById(R.id.list);
		List<String> alimentos = new ArrayList<String>();

		Cursor cursorAlimento = myDb.getAlimentos(talimento);

		while (cursorAlimento.isAfterLast() == false) {

			String alimento = cursorAlimento.getString(1);

			Float caloria1 = null;
			Cursor cursor = myDb.getCaloria(alimento);
			if (cursor.moveToFirst()) {

				caloria1 = Float.parseFloat(cursor.getString(0));
			}

			alimentos.add(alimento + "         " + caloria1 + " kcal");
			cursor.close();
			cursorAlimento.moveToNext();

		}
		cursorAlimento.close();

		adapter = new ArrayAdapter<String>(this,

		android.R.layout.simple_list_item_multiple_choice, alimentos);

		listaAlimentos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		listaAlimentos.setAdapter(adapter);

	}

	/**
	 * Function to load the spinner data from SQLite database
	 * */
	private void loadSpinnerData() {
		// database handler
		DBAdapter db = new DBAdapter(getApplicationContext());

		// Spinner Drop down elements
		List<String> lables = db.getAllLabels();

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner_tiporef.setAdapter(dataAdapter);
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

	public void launchRingDialog(View view) {

		final ProgressDialog ringProgressDialog = ProgressDialog.show(
				Actividade_Refeicao.this, "A guardar refeição ...", "Ingeriu "
						+ String.valueOf(calorias) + " Kcal", true);

		ringProgressDialog.setCancelable(true);

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					// Here you should write your time consuming task...

					// Let the progress ring for 10 seconds...

					Thread.sleep(1500);
					finish();
					startActivity(getIntent());

				} catch (Exception e) {

				}

				ringProgressDialog.dismiss();

			}

		}).start();

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

		case (R.id.action_menuhist):
			intent2 = new Intent(this, Actividade_MenuHistorico.class);
			startActivity(intent2);
			break;

		case (R.id.action_graf):
			intent2 = new Intent(this, Actividade_Grafico.class);
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
