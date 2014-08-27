package com.example.projdiab2014;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class Actividade_Grafico extends ActionBarActivity {

	DBAdapter myDb;

	private Intent intent2;
	private String array_spinner[];
	private String mesSpinner;
	private Button nova_pesquisa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividade_grafico);

		intent2 = getIntent();
		openDB();

		// myDb = new DBAdapter(Diab2014Activity_exe.this);
		// myDb.open();

		// permitir que navegue ate Home
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		Cursor cursorAno = myDb.getAnosDistintos();
		int j = 0;
		while (cursorAno.isAfterLast() == false) {

			String ano = cursorAno.getString(0);

			Cursor cursor = myDb.getMesDesseAno(ano);
			String mes = null;
			array_spinner = new String[cursor.getCount()];
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

				array_spinner[j] = mes + " " + ano;

				j++;
				cursor.moveToNext();

			}

			cursorAno.moveToNext();
		}

		if (!(array_spinner == null)) {
			mostrar_alertdialog_spinners();
		} else {
			finish();
			Toast.makeText(getApplicationContext(), "Sem dados para mostrar",
					Toast.LENGTH_SHORT).show();
		}

	}

	private void mostrar_alertdialog_spinners() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		TextView title = new TextView(this);
		title.setText("Seleciona o mês:");
		title.setPadding(10, 10, 10, 10);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(Color.rgb(0, 153, 204));
		title.setTextSize(23);
		builder.setCustomTitle(title);

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout_spinners = inflater.inflate(R.layout.spinner_dialog, null);
		Spinner sp_meses = (Spinner) layout_spinners
				.findViewById(R.id.spinner1);

		builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				grafico(mesSpinner);
			}
		});
		// Setting Negative "NO" Button
		builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// finish();
				dialog.cancel();
				Intent intent = new Intent(Actividade_Grafico.this,
						Actividade_Principal.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(intent);
			}
		});

		builder.setView(layout_spinners);
		builder.setCancelable(false);
		builder.show();

		// Preenche o spinner do dialog
		ArrayAdapter<String> adaptador_textos = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, array_spinner);
		adaptador_textos
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The
																							// drop
																							// down
																							// view
		sp_meses.setAdapter(adaptador_textos);
		sp_meses.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {

				mesSpinner = parent.getItemAtPosition(position).toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		nova_pesquisa = (Button) findViewById(R.id.button1);
		nova_pesquisa.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				finish();
				startActivity(getIntent());

			}
		});

	}

	private void grafico(String mes) {

		String mes_num = null;
		String mes_nome = mes.substring(0, mes.lastIndexOf(" ") + 1);
		int ano = Integer.parseInt(mes.substring(mes.lastIndexOf(" ") + 1));

		if (mes_nome.contains("Janeiro")) {
			mes_num = "01";
		} else if (mes_nome.contains("Fevereiro")) {
			mes_num = "02";
		} else if (mes_nome.contains("Março")) {
			mes_num = "03";
		} else if (mes_nome.contains("Abril")) {
			mes_num = "04";
		} else if (mes_nome.contains("Maio")) {
			mes_num = "05";
		} else if (mes_nome.contains("Junho")) {
			mes_num = "06";
		} else if (mes_nome.contains("Julho")) {
			mes_num = "07";
		} else if (mes_nome.contains("Agosto")) {
			mes_num = "08";
		} else if (mes_nome.contains("Setembro")) {
			mes_num = "09";
		} else if (mes_nome.contains("Outubro")) {
			mes_num = "10";
		} else if (mes_nome.contains("Novembro")) {
			mes_num = "11";
		} else if (mes_nome.contains("Dezembro")) {
			mes_num = "12";
		}

		int dias_graf = 0;
		if (mes_num == "01" || mes_num == "03" || mes_num == "05"
				|| mes_num == "07" || mes_num == "08" || mes_num == "10"
				|| mes_num == "12") {
			dias_graf = 30;
		} else if (mes_num == "04" || mes_num == "06" || mes_num == "09"
				|| mes_num == "11") {
			dias_graf = 29;
		} else if (mes_num == "02") {
			dias_graf = 28;
		}

		// desenhar linha dos valores glicemicos (total por dia)
		int num = 31;
		GraphViewData[] data = new GraphViewData[num];
		GraphViewData[] data_ref = new GraphViewData[num];

		for (int i = 0; i < num; i++) {

			Cursor c1 = myDb.getValorDia(ano, mes_num, i);
			int a = 0;

			while (c1.isAfterLast() == false) {
				a = a + Integer.parseInt(c1.getString(0));

				c1.moveToNext();
			}
			c1.close();

			data[i] = new GraphViewData(i, a);

		}

		// desenhar linha das calorias (total por dia) das refeicoes
		for (int i = 0; i < num; i++) {

			Cursor c2 = myDb.getValorCaloria(ano, mes_num, i);
			int a = 0;

			while (c2.isAfterLast() == false) {
				a = a + Integer.parseInt(c2.getString(0));

				c2.moveToNext();
			}
			c2.close();

			data_ref[i] = new GraphViewData(i, a);

		}

		GraphView graphView = new LineGraphView(this, "Média Mensal");

		GraphViewSeries seriesRnd = new GraphViewSeries("Valores Glicémicos",
				new GraphViewSeriesStyle(Color.BLUE, 3), data);
		GraphViewSeries seriesRnd2 = new GraphViewSeries("Calorias Refeições",
				new GraphViewSeriesStyle(Color.RED, 3), data_ref);
		// add data
		graphView.addSeries(seriesRnd);
		graphView.addSeries(seriesRnd2);
		// set view port, start=2, size=40
		graphView.setViewPort(1, dias_graf);
		graphView.getGraphViewStyle().setNumHorizontalLabels(7);
		graphView.setScrollable(false);
		// optional - activate scaling / zooming
		graphView.setScalable(false);
		// set legend
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.MIDDLE);
		graphView.setLegendWidth(300);

		// set styles
		graphView.getGraphViewStyle().setGridColor(Color.GRAY);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);

		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);

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
