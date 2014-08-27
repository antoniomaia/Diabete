package com.example.projdiab2014;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class Actividade_MenuHistorico extends ActionBarActivity {

	private Intent intent2;

	ImageButton move_hist_valor, move_hist_ref;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividade_menuhistorico);

		// enable the home button
		ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);

		move_hist_valor = (ImageButton) findViewById(R.id.move_hist_valor);
		move_hist_ref = (ImageButton) findViewById(R.id.move_hist_ref);

		move_hist_valor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent2 = new Intent(getApplicationContext(),
						Actividade_HistoricoValor.class);
				startActivity(intent2);

			}
		});

		move_hist_ref.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent2 = new Intent(getApplicationContext(),
						Actividade_HistoricoRefeicao.class);
				startActivity(intent2);

			}
		});

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

		case android.R.id.home:
			Intent intent = new Intent(this, Actividade_Principal.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;

		}

		return super.onOptionsItemSelected(item);

	}

}
