package com.example.projdiab2014;

import java.util.Calendar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.DatePicker;
import android.widget.RadioButton;

public class Actividade_InformacaoPessoal extends ActionBarActivity {

	private Intent intent2;
	private ImageButton botao;
	private EditText nome, peso, altura, email, contemerg;
	private RadioGroup sexo;
	DBAdapter myDb;
	private String peso1, altura1, sexo1, sangue_1, tipodiab_1;
	private int contemerg2;
	private double peso2, altura2;
	private TextView datanasc;
	private int mYear;
	private int mMonth;
	private int mDay;
	private Spinner sangue, tipodiab;
	static final int DATE_DIALOG_ID = 1;
	private Button imc;
	final Context context = this;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividade_informacaopessoal);

		intent2 = getIntent();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		// permitir que navegue ate Home
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		openDB();
		botao = (ImageButton) findViewById(R.id.button1);
		nome = (EditText) findViewById(R.id.editText1);
		sexo = (RadioGroup) findViewById(R.id.radioGroup1);
		peso = (EditText) findViewById(R.id.editText4);
		altura = (EditText) findViewById(R.id.editText5);
		sangue = (Spinner) findViewById(R.id.spinner2);
		tipodiab = (Spinner) findViewById(R.id.spinner3);
		email = (EditText) findViewById(R.id.editText3);
		contemerg = (EditText) findViewById(R.id.editText6);
		imc = (Button) findViewById(R.id.imc);

		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.Sangue, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		sangue.setAdapter(adapter);

		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.TipoDiab, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tipodiab.setAdapter(adapter2);

		datanasc = (TextView) findViewById(R.id.dateDisplay);
		datanasc.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

		// o que acontece ao tocar na data
		datanasc.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// preenche os campos quando utilizador ja existe
		Cursor cursor = myDb.getAllRowsUtente();
		if (cursor == null)
			return;

		try {
			if (cursor.moveToFirst()) {

				nome.setText(cursor.getString(1));
				sexo.check(cursor.getInt(2));
				datanasc.setText(cursor.getString(3));
				peso.setText(cursor.getString(4));
				altura.setText(cursor.getString(5));
				((RadioButton) sexo.findViewWithTag(cursor.getString(2)))
						.setChecked(true);
				sangue.setSelection(adapter.getPosition(cursor.getString(6)));
				tipodiab.setSelection(adapter2.getPosition(cursor.getString(7)));
				email.setText(cursor.getString(8));
				contemerg.setText(cursor.getString(9));

			}
		} finally {
			cursor.close();
		}

		sexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
				sexo1 = checkedRadioButton.getTag().toString();
			}
		});

		sangue.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				sangue_1 = parent.getItemAtPosition(position).toString();
				// showToast("You Selected Item :: " + name);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		tipodiab.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				tipodiab_1 = parent.getItemAtPosition(position).toString();
				// showToast("You Selected Item :: " + name);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		// permite guardar os dados
		botao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String nome1 = nome.getText().toString();
				String datanasc1 = datanasc.getText().toString();
				String email1 = email.getText().toString();
				String peso0 = peso.getText().toString();
				String altura0 = altura.getText().toString();
				String contemerg1 = contemerg.getText().toString();

				String msg = null;

				try {

					contemerg2 = Integer.parseInt(contemerg1);
					peso2 = Double.parseDouble(peso0);
					altura2 = Double.parseDouble(altura0);

				} catch (NumberFormatException e) {
					System.out.println("Erro");
				}

				try {

					Cursor cursor = myDb.getAllRowsUtente();

					if (cursor.getCount() > 0 && cursor.getCount() < 2) {

						if (sexo1 == null) {
							sexo1 = cursor.getString(2);
						}

						myDb.updateUtente(1, nome1, sexo1, datanasc1, peso2,
								altura2, sangue_1, tipodiab_1, email1,
								contemerg2);
						msg = "Dados Pessoais Actualizados";
					} else {
						myDb.inserirUtente(nome1, sexo1, datanasc1, peso2,
								altura2, sangue_1, tipodiab_1, email1,
								contemerg2);
						msg = "Dados Pessoais Guardados";
					}
				} catch (Exception e) {
					msg = "Erro!";
				}
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
						.show();
			}
		});

		imc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Double valorimc = null, pesoimc = null, alturaimc = null;
				String msg = null, msg2 = null;

				peso1 = peso.getText().toString();
				altura1 = altura.getText().toString();

				if (!peso1.equals("") && !altura1.equals("")) {
					if (!peso1.equals("0") && !altura1.equals("0")) {

						try {

							pesoimc = Double.parseDouble(peso1);
							alturaimc = Double.parseDouble(altura1);

							valorimc = pesoimc / (alturaimc * alturaimc);
							if (valorimc < 18.5) {
								msg2 = "Você está abaixo do peso ideal";
							} else if (valorimc >= 18.5 && valorimc < 25) {
								msg2 = "Parabéns - você está em seu peso normal!";
							} else if (valorimc >= 25 && valorimc < 30) {
								msg2 = "Você está acima do seu peso (sobrepeso)";
							} else if (valorimc >= 30 && valorimc < 35) {
								msg2 = "Obesidade grau I";
							} else if (valorimc >= 35 && valorimc < 40) {
								msg2 = "Obesidade grau II";
							} else if (valorimc >= 40) {
								msg2 = "Obesidade grau III";
							}

							msg = String.format("IMC: %.1f", valorimc);

						} catch (NumberFormatException e) {
							System.out.println("Erro");
						}
					} else {
						msg = "Introduza o seu peso e altura";
					}

				} else {
					msg = "Introduza o seu peso e altura";

				}

				if (msg2 == null) {
					msg2 = " ";
				}

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set title
				alertDialogBuilder.setTitle("Cálculo de IMC");

				// set dialog message
				alertDialogBuilder
						.setMessage(msg + "\n" + msg2)
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, close
										// current activity
										dialog.cancel();

									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		});

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {

		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);

		}
		return null;
	}

	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {

		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}

	private void updateDisplay() {
		datanasc.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	private void openDB() {
		myDb = new DBAdapter(this);
		myDb.open();
	}

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
