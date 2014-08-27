package com.example.projdiab2014;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class Actividade_HistoricoRefeicao extends ActionBarActivity {

	private Intent intent2;
	DBAdapter myDb;

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividade_historico);

		intent2 = getIntent();
		getSupportActionBar().setHomeButtonEnabled(true);

		openDB();

		myDb = new DBAdapter(Actividade_HistoricoRefeicao.this);
		myDb.open();

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

	}

	/*
	 * Preparing the list data
	 */
	@SuppressLint("SimpleDateFormat")
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		Cursor cursorAno = myDb.getAnosDistintosRef();

		int i = 0;
		while (cursorAno.isAfterLast() == false) {
			String mes = null;
			String dia = null;
			String ano = null;

			ano = cursorAno.getString(0);

			Cursor cursor = myDb.getMesAnoRef(ano);

			while (cursor.isAfterLast() == false) {
				int a = Integer.parseInt(cursor.getString(0));

				switch (a) {
				case 1:
					mes = "Janeiro";
					break;
				case 2:
					mes = "Fevereiro";
					break;
				case 3:
					mes = "Março";
					break;
				case 4:
					mes = "Abril";
					break;
				case 5:
					mes = "Maio";
					break;
				case 6:
					mes = "Junho";
					break;
				case 7:
					mes = "Julho";
					break;
				case 8:
					mes = "Agosto";
					break;
				case 9:
					mes = "Setembro";
					break;
				case 10:
					mes = "Outubro";
					break;
				case 11:
					mes = "Novembro";
					break;
				case 12:
					mes = "Dezembro";
					break;
				default:
					mes = "Més Inválido";
					break;

				}

				Cursor cursorDia = myDb.getDiaRef(ano, cursor.getString(0));

				while (cursorDia.isAfterLast() == false) {

					dia = cursorDia.getString(0);

					listDataHeader.add(dia + " " + mes + " de " + ano);
					List<String> nome_mes = new ArrayList<String>();

					Cursor cursorRefeicaoData = myDb.getRefeicaoData(ano,
							cursor.getString(0), dia);
					while (cursorRefeicaoData.isAfterLast() == false) {
						String tiporef = cursorRefeicaoData.getString(0);
						String calorias = cursorRefeicaoData.getString(1);

						nome_mes.add("[" + tiporef + "]" + "Total Calorias: "
								+ calorias + " kcal");
						cursorRefeicaoData.moveToNext();
					}
					cursorRefeicaoData.close();

					listDataChild.put(listDataHeader.get(i), nome_mes);
					i++;
					cursorDia.moveToNext();

				}
				cursorDia.close();

				cursor.moveToNext();

			}

			cursorAno.moveToNext();
			cursor.close();
		}

		cursorAno.close();

		if (i == 0) {
			finish();
			Toast.makeText(getApplicationContext(), "Sem dados para mostrar",
					Toast.LENGTH_SHORT).show();
		}

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

		case android.R.id.home:
			Intent intent = new Intent(this, Actividade_Principal.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		}

		return super.onOptionsItemSelected(item);

	}
}