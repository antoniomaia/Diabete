package com.example.projdiab2014;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class Actividade_HistoricoValor extends ActionBarActivity {

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

		myDb = new DBAdapter(Actividade_HistoricoValor.this);
		myDb.open();

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		expListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@SuppressLint("NewApi")
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
					int groupPosition = ExpandableListView
							.getPackedPositionGroup(id);
					int childPosition = ExpandableListView
							.getPackedPositionChild(id);

					String a = String.valueOf(expListView
							.getExpandableListAdapter().getChild(groupPosition,
									childPosition));
					// listAdapter.removeChild(groupPosition,childPosition);

					String valor = a.substring(a.indexOf("[") + 1,
							a.indexOf(']'));
					String valor_dia = a.substring(a.lastIndexOf("Dia") + 4,
							a.indexOf("as") - 2);
					String valor_hora = a.substring(a.indexOf("as") + 3);
					myDb.apagaHistValor(valor, valor_dia, valor_hora);

					// listDataHeader.remove(groupPosition);
					listDataChild.remove(childPosition);
					// dialogDelete.dismiss();
					listAdapter.notifyDataSetChanged();
					// expListView.invalidateViews();

					finish();
					startActivity(getIntent());

					expListView.setAdapter(listAdapter);
					for (int i = 0; i < listAdapter.getGroupCount(); i++)
						expListView.expandGroup(i);
					// expListView.expandGroup(groupPosition, true);
					// expListView.setSelectedGroup(groupPosition);

					// prepareListData();
					Toast.makeText(getApplicationContext(),
							"Apagado com sucesso", Toast.LENGTH_SHORT).show();

					return true;
				}

				return false;
			}

		});

	}

	/*
	 * Preparing the list data
	 */
	@SuppressLint("SimpleDateFormat")
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		Cursor cursorAno = myDb.getAnosDistintos();

		int i = 0;
		while (cursorAno.isAfterLast() == false) {

			String ano = cursorAno.getString(0);

			Cursor cursor = myDb.getMesDesseAno(ano);
			String mes = null;

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
					mes = "Mês Inválido";
					break;
				}

				listDataHeader.add(mes + " de " + ano);
				List<String> nome_mes = new ArrayList<String>();

				Cursor cursorValorMes = myDb.getValorData(ano,
						cursor.getString(0));
				while (cursorValorMes.isAfterLast() == false) {
					String b = cursorValorMes.getString(0);
					String dateStr = cursorValorMes.getString(1);

					SimpleDateFormat toFullDate = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date fullDate = null;
					try {
						fullDate = toFullDate.parse(dateStr);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SimpleDateFormat sdf = new SimpleDateFormat(
							"'Dia 'dd ' as ' HH:mm");
					String shortTimeStr = sdf.format(fullDate);

					nome_mes.add("[" + b + "]" + shortTimeStr);
					cursorValorMes.moveToNext();
				}
				cursorValorMes.close();
				listDataChild.put(listDataHeader.get(i), nome_mes);

				i++;
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