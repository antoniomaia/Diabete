// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.example.projdiab2014;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

	// ///////////////////////////////////////////////////////////////////
	// Constants & Data
	// ///////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";

	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	/*
	 * CHANGE 1:
	 */
	// TODO: Setup your fields here:
	// Tabela ValorGlicÔøΩmico
	public static final String KEY_VALORGLICEMICO = "valorglicemico";
	public static final String KEY_DATAHORA = "datahora";

	// Tabela Utente
	public static final String KEY_NOME = "nome";
	public static final String KEY_SEXO = "sexo";
	public static final String KEY_DATANASC = "datanasc";
	public static final String KEY_PESO = "peso";
	public static final String KEY_ALTURA = "altura";
	public static final String KEY_SANGUE = "sangue";
	public static final String KEY_TIPODIAB = "tipodiab";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_CONTEMERG = "contemerg";

	// Tabela TipoRefeicao
	public static final String KEY_TIPO = "tipo";

	// Tabela TipoAlimento
	public static final String KEY_ROWID_TIPOA = "_idTipoA";
	public static final String KEY_TIPOALIMENTO = "tipoalimento";

	// Tabela Alimento
	public static final String KEY_ALIMENTO = "alimento";
	public static final String KEY_CALORIA = "caloria";
	public static final String KEY_TIPOALI = "tipoali";

	// Tabela Refei√ß√£o
	public static final String KEY_TIPOREF = "tiporef";
	public static final String KEY_ALIMENTOS = "alimentos";
	public static final String KEY_CALORIAS = "calorias";
	public static final String KEY_DH = "datahora";

	// Tabela Equipa de Apoio
	public static final String KEY_ESPECIALIDADE = "especialidade";
	public static final String KEY_NOMEMEDICO = "nomemedico";
	public static final String KEY_CONTACTO = "contacto";

	// public static final String KEY_NOME = "nome";

	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_VALORGLICEMICO = 1;
	public static final int COL_DATAHORA = 2;

	public static final int COL_NOME = 1;
	public static final int COL_SEXO = 2;
	public static final int COL_DATANASC = 3;
	public static final int COL_PESO = 4;
	public static final int COL_ALTURA = 5;
	public static final int COL_SANGUE = 6;
	public static final int COL_TIPODIAB = 7;
	public static final int COL_EMAIL = 8;
	public static final int COL_CONTEMERG = 9;

	public static final int COL_TIPO = 1;

	public static final int COL_ROWID_TIPOA = 0;
	public static final int COL_TIPOALIMENTO = 1;

	public static final int COL_ALIMENTO = 1;
	public static final int COL_CALORIA = 2;
	public static final int COL_TIPOALI = 3;

	public static final int COL_TIPOREF = 1;
	public static final int COL_ALIMENTOS = 2;
	public static final int COL_CALORIAS = 3;
	public static final int COL_DH = 4;

	public static final int COL_ESPECILIDADE = 1;
	public static final int COL_NOMEMEDICO = 2;
	public static final int COL_CONTACTO = 3;

	public static final String[] ALL_KEYS = new String[] { KEY_ROWID,
			KEY_VALORGLICEMICO, KEY_DATAHORA };
	public static final String[] ALL_KEYS_UTENTE = new String[] { KEY_ROWID,
			KEY_NOME, KEY_SEXO, KEY_DATANASC, KEY_PESO, KEY_ALTURA, KEY_SANGUE,
			KEY_TIPODIAB, KEY_EMAIL, KEY_CONTEMERG };
	public static final String[] ALL_KEYS_TIPOREFEICAO = new String[] {
			KEY_ROWID, KEY_TIPO };
	public static final String[] ALL_KEYS_TIPOALIMENTO = new String[] {
			KEY_ROWID_TIPOA, KEY_TIPOALIMENTO };
	public static final String[] ALL_KEYS_ALIMENTO = new String[] { KEY_ROWID,
			KEY_ALIMENTO, KEY_CALORIA, KEY_TIPOALI };
	public static final String[] ALL_KEYS_REFEICAO = new String[] { KEY_ROWID,
			KEY_TIPOREF, KEY_ALIMENTOS, KEY_CALORIAS, KEY_DH };
	public static final String[] ALL_KEYS_EQUIPAAPOIO = new String[] {
			KEY_ROWID, KEY_ESPECIALIDADE, KEY_NOMEMEDICO, KEY_CONTACTO };

	// DB info: it's name, and the table we are using (just one).
	public static final String DATABASE_NAME = "MyDb";
	public static final String DATABASE_TABLE = "ValorGlicemico";
	public static final String DATABASE_TABLE_UTENTE = "Utente";
	public static final String DATABASE_TABLE_TIPOREFEICAO = "TipoRefeicao";
	public static final String DATABASE_TABLE_TIPOALIMENTO = "TipoAlimento";
	public static final String DATABASE_TABLE_ALIMENTO = "Alimento";
	public static final String DATABASE_TABLE_REFEICAO = "Refeicao";
	public static final String DATABASE_TABLE_EQUIPAAPOIO = "EquipaApoio";

	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE_SQL = "create table "
			+ DATABASE_TABLE
			+ " ("
			+ KEY_ROWID
			+ " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			// - Key is the column name you created above.
			// - {type} is one of: text, integer, real, blob
			// (http://www.sqlite.org/datatype3.html)
			// - "not null" means it is a required field (must be given a
			// value).
			// NOTE: All must be comma separated (end of line!) Last one must
			// have NO comma!!
			+ KEY_VALORGLICEMICO + " double not null, " + KEY_DATAHORA
			+ " text not null"

			// Rest of creation:
			+ ");";

	private static final String DATABASE_CREATE_SQL_1 = "create table "
			+ DATABASE_TABLE_UTENTE
			+ " ("
			+ KEY_ROWID
			+ " integer primary key, "

			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			// - Key is the column name you created above.
			// - {type} is one of: text, integer, real, blob
			// (http://www.sqlite.org/datatype3.html)
			// - "not null" means it is a required field (must be given a
			// value).
			// NOTE: All must be comma separated (end of line!) Last one must
			// have NO comma!!
			+ KEY_NOME + " text not null, " + KEY_SEXO + " text not null,"
			+ KEY_DATANASC + " text not null," + KEY_PESO + " double not null,"
			+ KEY_ALTURA + " double not null," + KEY_SANGUE + " text not null,"
			+ KEY_TIPODIAB + " text not null," + KEY_EMAIL + " text not null,"
			+ KEY_CONTEMERG + " integer not null"

			// Rest of creation:
			+ ");";

	private static final String DATABASE_CREATE_SQL_TIPOREFEICAO = "create table "
			+ DATABASE_TABLE_TIPOREFEICAO
			+ " ("
			+ KEY_ROWID
			+ " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			// - Key is the column name you created above.
			// - {type} is one of: text, integer, real, blob
			// (http://www.sqlite.org/datatype3.html)
			// - "not null" means it is a required field (must be given a
			// value).
			// NOTE: All must be comma separated (end of line!) Last one must
			// have NO comma!!
			+ KEY_TIPO + " text not null "

			// Rest of creation:
			+ ");";

	private static final String DATABASE_CREATE_SQL_TIPOALIMENTO = "create table "
			+ DATABASE_TABLE_TIPOALIMENTO
			+ " ("
			+ KEY_ROWID
			+ " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			// - Key is the column name you created above.
			// - {type} is one of: text, integer, real, blob
			// (http://www.sqlite.org/datatype3.html)
			// - "not null" means it is a required field (must be given a
			// value).
			// NOTE: All must be comma separated (end of line!) Last one must
			// have NO comma!!
			+ KEY_TIPOALIMENTO + " text not null "

			// Rest of creation:
			+ ");";

	private static final String DATABASE_CREATE_SQL_ALIMENTO = "create table "
			+ DATABASE_TABLE_ALIMENTO
			+ " ("
			+ KEY_ROWID
			+ " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			// - Key is the column name you created above.
			// - {type} is one of: text, integer, real, blob
			// (http://www.sqlite.org/datatype3.html)
			// - "not null" means it is a required field (must be given a
			// value).
			// NOTE: All must be comma separated (end of line!) Last one must
			// have NO comma!!
			+ KEY_ALIMENTO + " text not null," + KEY_CALORIA
			+ " double not null," + KEY_TIPOALI + " text not null"

			// Rest of creation:
			+ ");";

	private static final String DATABASE_CREATE_SQL_REFEICAO = "create table "
			+ DATABASE_TABLE_REFEICAO
			+ " ("
			+ KEY_ROWID
			+ " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			// - Key is the column name you created above.
			// - {type} is one of: text, integer, real, blob
			// (http://www.sqlite.org/datatype3.html)
			// - "not null" means it is a required field (must be given a
			// value).
			// NOTE: All must be comma separated (end of line!) Last one must
			// have NO comma!!
			+ KEY_TIPOREF + " text not null," + KEY_ALIMENTOS
			+ " text not null," + KEY_CALORIAS + " float not null,"
			+ KEY_DATAHORA + " text not null"

			// Rest of creation:
			+ ");";

	private static final String DATABASE_CREATE_SQL_EQUIPAAPOIO = "create table "
			+ DATABASE_TABLE_EQUIPAAPOIO
			+ " ("
			+ KEY_ROWID
			+ " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			// - Key is the column name you created above.
			// - {type} is one of: text, integer, real, blob
			// (http://www.sqlite.org/datatype3.html)
			// - "not null" means it is a required field (must be given a
			// value).
			// NOTE: All must be comma separated (end of line!) Last one must
			// have NO comma!!
			+ KEY_ESPECIALIDADE
			+ " text not null, "
			+ KEY_NOMEMEDICO
			+ " text not null," + KEY_CONTACTO + " text not null"
			// Rest of creation:
			+ ");";

	// Context of application who uses us.
	private final Context context;

	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	// ///////////////////////////////////////////////////////////////////
	// Public methods:
	// ///////////////////////////////////////////////////////////////////

	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}

	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}

	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}

	// Add a new set of values to the database.
	public long insertRow(Double valorglicemico) {
		/*
		 * CHANGE 3:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:

		TimeZone tz = TimeZone.getTimeZone("GMT+01:00");
		Calendar c = Calendar.getInstance(tz);
		String time = String.format("%02d", c.get(Calendar.YEAR)) + "-"
				+ String.format("%02d", (c.get(Calendar.MONTH) + 1)) + "-"
				+ String.format("%02d", c.get(Calendar.DAY_OF_MONTH)) + " "
				+ String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) + ":"
				+ String.format("%02d", c.get(Calendar.MINUTE)) + ":"
				+ String.format("%02d", c.get(Calendar.SECOND));

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_VALORGLICEMICO, valorglicemico);
		initialValues.put(KEY_DATAHORA, time);

		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}

	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));
			} while (c.moveToNext());
		}
		c.close();
	}

	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null,
				null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Return all data in the database.
	public Cursor getAllRowsValorGlicemico() {
		String where = null;
		Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null,
				null, KEY_ROWID + " DESC", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getAllRowsUtente() {
		String where = null;
		Cursor c = db.query(true, DATABASE_TABLE_UTENTE, ALL_KEYS_UTENTE,
				where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null,
				null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId, String valorglicemico, String datahora) {
		String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_VALORGLICEMICO, valorglicemico);
		newValues.put(KEY_DATAHORA, datahora);

		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}

	public long inserirUtente(String nome, String sexo, String datanasc,
			double peso, double altura, String sangue, String tipodiab,
			String email, int contemerg) {
		/*
		 * CHANGE 3:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NOME, nome);
		initialValues.put(KEY_SEXO, sexo);
		initialValues.put(KEY_DATANASC, datanasc);
		initialValues.put(KEY_PESO, peso);
		initialValues.put(KEY_ALTURA, altura);
		initialValues.put(KEY_SANGUE, sangue);
		initialValues.put(KEY_TIPODIAB, tipodiab);
		initialValues.put(KEY_EMAIL, email);
		initialValues.put(KEY_CONTEMERG, contemerg);

		// Insert it into the database.
		return db.insert(DATABASE_TABLE_UTENTE, null, initialValues);
	}

	public boolean updateUtente(long rowId, String nome, String sexo,
			String datanasc, double peso, double altura, String sangue,
			String tipodiab, String email, int contemerg) {
		String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_NOME, nome);
		newValues.put(KEY_SEXO, sexo);
		newValues.put(KEY_DATANASC, datanasc);
		newValues.put(KEY_PESO, peso);
		newValues.put(KEY_ALTURA, altura);
		newValues.put(KEY_SANGUE, sangue);
		newValues.put(KEY_TIPODIAB, tipodiab);
		newValues.put(KEY_EMAIL, email);
		newValues.put(KEY_CONTEMERG, contemerg);

		// Insert it into the database.
		return db.update(DATABASE_TABLE_UTENTE, newValues, where, null) != 0;
	}

	public Cursor getAnosDistintos() {
		Cursor c = db
				.rawQuery(
						"SELECT DISTINCT strftime('%Y', datahora) as mes FROM ValorGlicemico ORDER BY datahora DESC",
						null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getValorData(String ano, String mes) {
		Cursor c = db
				.rawQuery(
						"SELECT valorglicemico,datahora FROM ValorGlicemico WHERE strftime('%Y-%m', datahora)='"
								+ ano + "-" + mes + "' ORDER BY datahora DESC",
						null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getMesDesseAno(String ano) {
		Cursor c = db
				.rawQuery(
						"SELECT DISTINCT strftime('%m', datahora)  FROM ValorGlicemico WHERE strftime('%Y-%m', datahora)= strftime('"
								+ ano
								+ "-%m', datahora) ORDER BY datahora DESC",
						null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getAlimentos(String talimento) {
		Cursor c = db.rawQuery("SELECT * FROM Alimento WHERE tipoali='"
				+ talimento + "'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	/**
	 * Inserting new lable into lables table
	 * */
	public void insertLabel(String label) {
		SQLiteDatabase db = myDBHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TIPO, label);

		// Inserting Row
		db.insert(DATABASE_TABLE_TIPOREFEICAO, null, values);
		db.close(); // Closing database connection
	}

	/**
	 * Getting all labels returns list of labels
	 * */
	public List<String> getAllLabels() {
		List<String> labels = new ArrayList<String>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_TIPOREFEICAO;

		SQLiteDatabase db = myDBHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				labels.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return labels;
	}

	/**
	 * Inserting new lable into lables table
	 * */
	public void insertLabel_talimento(String label) {
		SQLiteDatabase db = myDBHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TIPOALIMENTO, label);

		// Inserting Row
		db.insert(DATABASE_TABLE_TIPOALIMENTO, null, values);
		db.close(); // Closing database connection
	}

	/**
	 * Getting all labels returns list of labels
	 * */
	public List<String> getAllLabels_talimento() {
		List<String> labels = new ArrayList<String>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_TIPOALIMENTO;

		SQLiteDatabase db = myDBHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				labels.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return labels;
	}

	public void insertLabel_alimento(String label, Double label2, String label3) {
		SQLiteDatabase db = myDBHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ALIMENTO, label);
		values.put(KEY_CALORIA, label2);
		values.put(KEY_TIPOALI, label3);

		// Inserting Row
		db.insert(DATABASE_TABLE_ALIMENTO, null, values);
		db.close(); // Closing database connection
	}

	/**
	 * Getting all labels returns list of labels
	 * */
	public List<String> getAllLabels_alimento() {
		List<String> labels = new ArrayList<String>();

		// Select All Query
		String selectQuery = "SELECT * FROM " + DATABASE_TABLE_ALIMENTO;

		SQLiteDatabase db = myDBHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				labels.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return labels;
	}

	public long inserirRefeicao(String tiporef, String alimentos, float calorias) {
		/*
		 * CHANGE 3:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:

		TimeZone tz = TimeZone.getTimeZone("GMT+01:00");
		Calendar c = Calendar.getInstance(tz);
		String time = String.format("%02d", c.get(Calendar.YEAR)) + "-"
				+ String.format("%02d", (c.get(Calendar.MONTH) + 1)) + "-"
				+ String.format("%02d", c.get(Calendar.DAY_OF_MONTH)) + " "
				+ String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) + ":"
				+ String.format("%02d", c.get(Calendar.MINUTE)) + ":"
				+ String.format("%02d", c.get(Calendar.SECOND));

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TIPOREF, tiporef);
		initialValues.put(KEY_ALIMENTOS, alimentos);
		initialValues.put(KEY_CALORIAS, calorias);
		initialValues.put(KEY_DATAHORA, time);

		// Insert it into the database.
		return db.insert(DATABASE_TABLE_REFEICAO, null, initialValues);
	}

	public Cursor getCaloria(String alimento) {
		Cursor c = db.rawQuery("SELECT caloria FROM Alimento WHERE (alimento='"
				+ alimento + "'" + "OR alimento='" + alimento + " ')", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getAnosDistintosRef() {
		Cursor c = db
				.rawQuery(
						"SELECT DISTINCT strftime('%Y', datahora) as mes FROM Refeicao ORDER BY datahora DESC",
						null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getMesAnoRef(String ano) {
		Cursor c = db
				.rawQuery(
						"SELECT DISTINCT strftime('%m', datahora)  FROM Refeicao WHERE strftime('%Y-%m', datahora)= strftime('"
								+ ano
								+ "-%m', datahora) ORDER BY datahora DESC",
						null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getDiaRef(String ano, String mes) {
		Cursor c = db
				.rawQuery(
						"SELECT DISTINCT strftime('%d', datahora) FROM Refeicao WHERE strftime('%Y-%m-%d', datahora)= strftime('"
								+ ano
								+ "-"
								+ mes
								+ "-%d', datahora) ORDER BY datahora DESC",
						null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getRefeicaoData(String ano, String mes, String dia) {
		Cursor c = db.rawQuery(
				"SELECT tiporef,calorias FROM Refeicao WHERE strftime('%Y-%m-%d', datahora)='"
						+ ano + "-" + mes + "-" + dia
						+ "' ORDER BY datahora DESC", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public List<String> getAllLabelsMedicos() {
		List<String> labels = new ArrayList<String>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_EQUIPAAPOIO;

		SQLiteDatabase db = myDBHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				labels.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return labels;
	}

	public long insertRowMedico(String especialidade, String nome,
			String contacto) {
		/*
		 * CHANGE 3:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ESPECIALIDADE, especialidade);
		initialValues.put(KEY_NOMEMEDICO, nome);
		initialValues.put(KEY_CONTACTO, contacto);

		// Insert it into the database.
		return db.insert(DATABASE_TABLE_EQUIPAAPOIO, null, initialValues);
	}

	public Cursor deleteRowEspecialidade(String especialidade) {
		Cursor c = db.rawQuery("DELETE FROM equipaapoio WHERE especialidade='"
				+ especialidade + "'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getContactoMedico(String especialidade) {
		Cursor c = db.rawQuery(
				"SELECT contacto FROM equipaapoio WHERE especialidade='"
						+ especialidade + "'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getDadosMedico(String especialidade) {
		Cursor c = db.rawQuery(
				"SELECT * FROM equipaapoio WHERE especialidade='"
						+ especialidade + "'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getTodosValoresGli() {
		Cursor c = db.rawQuery("SELECT valorglicemico FROM valorglicemico",
				null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getValorDia(int ano, String mes, int dia) {
		Cursor c = db
				.rawQuery(
						"SELECT valorglicemico FROM ValorGlicemico WHERE strftime('%Y-%m-%d', datahora)= strftime('"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ "', datahora) ORDER BY datahora DESC", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getValorCaloria(int ano, String mes, int dia) {
		Cursor c = db
				.rawQuery(
						"SELECT calorias FROM Refeicao WHERE strftime('%Y-%m-%d', datahora)= strftime('"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ "', datahora) ORDER BY datahora DESC", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor deleteRowAlimento(String alimento) {
		Cursor c = db.rawQuery("DELETE FROM alimento WHERE alimento='"
				+ alimento + "'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getAlimento(String talimento) {
		Cursor c = db.rawQuery("SELECT alimento FROM Alimento WHERE tipoali='"
				+ talimento + "'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor deleteRowCategoriaAlimento(String tipoalimento) {
		Cursor c = db.rawQuery("DELETE FROM tipoalimento WHERE tipoalimento='"
				+ tipoalimento + "'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor deleteRowTipoRefeicao(String tiporefeicao) {
		Cursor c = db.rawQuery("DELETE FROM tiporefeicao WHERE tipo='"
				+ tiporefeicao + "'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getMedico(String especialidade) {
		Cursor c = db.rawQuery(
				"SELECT especialidade FROM equipaapoio WHERE especialidade='"
						+ especialidade + "'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor calcpeqalmoco(int ano, String mes, int dia) {
		Cursor c = db
				.rawQuery(
						"SELECT AVG(valorglicemico) FROM ValorGlicemico WHERE strftime('%Y-%m-%d', datahora)= strftime('"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ "', datahora) AND datahora BETWEEN '"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ " "
								+ "00:00:00"
								+ "' AND '"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ " "
								+ "12:00:00" + "'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor calcalmoco(int ano, String mes, int dia) {
		Cursor c = db
				.rawQuery(
						"SELECT AVG(valorglicemico) FROM ValorGlicemico WHERE strftime('%Y-%m-%d', datahora)= strftime('"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ "', datahora) AND datahora BETWEEN '"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ " "
								+ "12:00:01"
								+ "' AND '"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ " "
								+ "18:30:00" + "'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor calcjantar(int ano, String mes, int dia) {
		Cursor c = db
				.rawQuery(
						"SELECT AVG(valorglicemico) FROM ValorGlicemico WHERE strftime('%Y-%m-%d', datahora)= strftime('"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ "', datahora) AND datahora BETWEEN '"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ " "
								+ "18:30:01"
								+ "' AND '"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ " "
								+ "23:59:59" + "'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor calcrefjantar(int ano, String mes, int dia) {
		Cursor c = db
				.rawQuery(
						"SELECT AVG(calorias) FROM Refeicao WHERE strftime('%Y-%m-%d', datahora)= strftime('"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ "', datahora) AND tiporef='Jantar'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor calcrefalmoco(int ano, String mes, int dia) {
		Cursor c = db
				.rawQuery(
						"SELECT AVG(calorias) FROM Refeicao WHERE strftime('%Y-%m-%d', datahora)= strftime('"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ "', datahora) AND tiporef='Almoço'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor calcrefpeqalmoco(int ano, String mes, int dia) {
		Cursor c = db
				.rawQuery(
						"SELECT AVG(calorias) FROM Refeicao WHERE strftime('%Y-%m-%d', datahora)= strftime('"
								+ ano
								+ "-"
								+ mes
								+ "-"
								+ dia
								+ "', datahora) AND tiporef='Pequeno-Almoço'",
						null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor pegaEmerg() {
		Cursor c = db.rawQuery("SELECT contemerg FROM Utente", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor apagaHistValor(String valor, String valor_dia,
			String valor_hora) {
		Cursor c = db
				.rawQuery(
						"DELETE FROM ValorGlicemico WHERE valorglicemico='"
								+ valor
								+ "' AND strftime('%Y-%m-%d %H:%M:%S', datahora)= strftime('%Y-%m-"
								+ valor_dia + "" + valor_hora
								+ ":%S', datahora)", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// ///////////////////////////////////////////////////////////////////
	// Private Helper Classes:
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Private class which handles database creation and upgrading. Used to
	 * handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);
			_db.execSQL(DATABASE_CREATE_SQL_1);
			_db.execSQL(DATABASE_CREATE_SQL_TIPOREFEICAO);
			_db.execSQL(DATABASE_CREATE_SQL_TIPOALIMENTO);
			_db.execSQL(DATABASE_CREATE_SQL_ALIMENTO);
			_db.execSQL(DATABASE_CREATE_SQL_REFEICAO);
			_db.execSQL(DATABASE_CREATE_SQL_EQUIPAAPOIO);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version "
					+ oldVersion + " to " + newVersion
					+ ", which will destroy all old data!");

			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_UTENTE);
			_db.execSQL("DROP TABLE IF EXISTS "
					+ DATABASE_CREATE_SQL_TIPOREFEICAO);
			_db.execSQL("DROP TABLE IF EXISTS "
					+ DATABASE_CREATE_SQL_TIPOALIMENTO);
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_SQL_ALIMENTO);
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_SQL_REFEICAO);
			_db.execSQL("DROP TABLE IF EXISTS "
					+ DATABASE_CREATE_SQL_EQUIPAAPOIO);

			// Recreate new database:
			// onCreate(_db);
		}
	}

}
