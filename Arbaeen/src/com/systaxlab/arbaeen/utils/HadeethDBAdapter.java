package com.systaxlab.arbaeen.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HadeethDBAdapter {

	public static final String KEY_ID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_HADEETH = "hadeeth";

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "arbaeen.db";
	private static final String SQLITE_TABLE = "ahadeeth1";
	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	public HadeethDBAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	public HadeethDBAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}

	public long createCountry(String id, String hadeeth, String title) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ID, id);
		initialValues.put(KEY_HADEETH, hadeeth);
		initialValues.put(KEY_TITLE, title);

		return mDb.insert(SQLITE_TABLE, null, initialValues);
	}

	public boolean deleteAllCountries() {

		int doneDelete = 0;
		doneDelete = mDb.delete(SQLITE_TABLE, null, null);
		return doneDelete > 0;
	}

	public Cursor fetchCountriesByName(String inputText) throws SQLException {
		Cursor mCursor = null;
		if (inputText == null || inputText.length() == 0) {
			mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ID,
					KEY_HADEETH, KEY_TITLE}, null,
					null, null, null, null);

		} else {
			mCursor = mDb.query(true, SQLITE_TABLE, new String[] { KEY_ID,
					KEY_HADEETH, KEY_TITLE }, KEY_TITLE
					+ " like '%" + inputText + "%'", null, null, null, null,
					null);
		}
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public Cursor fetchAllCountries() {

		Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ID,
				KEY_HADEETH, KEY_TITLE }, null, null,
				null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

}
