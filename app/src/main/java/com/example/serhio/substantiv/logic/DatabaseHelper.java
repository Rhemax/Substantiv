package com.example.serhio.substantiv.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.serhio.substantiv.entities.ConstantsSQL;
import com.example.serhio.substantiv.entities.Locales;
import com.example.serhio.substantiv.entities.Rule;
import com.example.serhio.substantiv.entities.Substantiv;

import junit.framework.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static DatabaseHelper dbHelper;
    private static String DB_NAME = "substantiv";
    private static String DB_PATH = "";
    private static String RULE_TABLE_REQUEST = "SELECT * FROM " + ConstantsSQL.RULE_TABLE;
    private static String TAG = "Rhemax";
    private  Context mContext;
    private SQLiteDatabase mDataBase;
    private boolean mNeedUpdate = false;
    private List<Rule> rules;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
      //  this.DB_PATH = context.getDatabasePath(DB_NAME).getAbsolutePath();


        Log.d(TAG, "DatabaseHelper, constructor, dbExist(): " + checkDataBase() + ", dbName: " + getDatabaseName() + ", PATH: " + DB_PATH);
        openDataBase();

    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (dbHelper == null)
            dbHelper = new DatabaseHelper(context);
        return dbHelper;
    }

    public void





    updateDataBase() throws IOException {
        Log.d(TAG, "DatabaseHelper, updateDataBase()");
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        try {
            mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            Log.d(TAG,"DatabaseHelper, mDataGase.isOpen: " + mDataBase.isOpen());
            SQLiteDatabase database = getWritableDatabase();
            Cursor c = database.rawQuery(
                    "SELECT name FROM sqlite_master WHERE type='table'", null);
            Assert.assertNotNull(c);

            String actual = "";
            if (c.moveToFirst()) {
                while (!c.isAfterLast()) {
                    actual += c.getString(0) + ",";
                    c.moveToNext();
                }
            }
            c.close();
            Log.d(TAG,"DatabaseHelper, tableNames: " + actual);


        } catch (SQLiteException e) {
            if (mDataBase != null && mDataBase.isOpen())
                mDataBase.close();
        }/* finally {
            if (mDataBase != null && mDataBase.isOpen())
                mDataBase.close();
        }*/
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
        {
            try {
                updateDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean insertSubstantiv(Substantiv substantiv) {
        ContentValues values = new ContentValues();
        values.put(ConstantsSQL.NAME, substantiv.getName());
        values.put(ConstantsSQL.GENDER, substantiv.getGender());
        values.put(ConstantsSQL.PLURAL_FORM, substantiv.getPluralForm());
        values.put(ConstantsSQL.EXPLANATION, substantiv.getExplanation());
        values.put(ConstantsSQL.RULE_ID, substantiv.getRuleId());
        values.put(ConstantsSQL.IS_FAVORITE, substantiv.isFavorite());
        values.put(ConstantsSQL.FREQUENCE_RATE, substantiv.getFrequencyRate());
        values.put(ConstantsSQL.ANSWERS_COUNT, substantiv.getAnswersCount());
        values.put(ConstantsSQL.SCORE, substantiv.getScore());
        long inserted = mDataBase.insert(ConstantsSQL.SUBSTANTIV_TABLE, null, values);
        return !(inserted == -1);
    }

    //TODO Optimize
    public List<Substantiv> getSubstantivList(String sqlRequest) {
        List<Substantiv> substantivs = new ArrayList<>();
       // rules = getRuleList();
        mDataBase = getReadableDatabase();
        Cursor c = mDataBase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        String[] columnNames = c.getColumnNames();
        Log.d("Rhemax", "DatabaseHelper, getsubstantivList, columns: ");
        for(int i = 0; i < columnNames.length; i++)
        {
            Log.d("Rhemax", i + ": " + columnNames[i]);
        }
        Cursor cursor = mDataBase.rawQuery(sqlRequest, null);

        if (cursor.moveToFirst()) {
            do {
                Substantiv substantiv = new Substantiv();
                substantiv.setId(cursor.getInt(0));
                substantiv.setName(cursor.getString(1));
                substantiv.setGender(cursor.getString(2));
                substantiv.setPluralForm(cursor.getString(3));
                substantiv.setExplanation(cursor.getString(4));
                substantiv.setRuleId(cursor.getInt(5));
                int ruleId = cursor.getInt(5);
                //TODO Optimize!!!
/*                for (Rule rule : rules) {
                    if (rule.getId() == ruleId) {
                        substantiv.setRule(rule);
                        //   Log.d("Rhemax", "Rule.getiId = "+ rule.getId() + ", ruleId = " + ruleId + ": Substantivul: " + substantiv.getName() + ", Adaug regula: " + rule.getRule());
                    }
                }*/
                if (substantiv.getRule() == null) {
                    Rule rule = new Rule();
                    rule.setRule("No sugestions");
                    substantiv.setRule(rule);
                }
                substantiv.setFavorite(cursor.getInt(6) == 1 ? true : false);
                substantiv.setFrequencyRate(cursor.getInt(7));
                substantiv.setAnswersCount(cursor.getInt(8));
                substantiv.setScore(cursor.getInt(9));
                substantiv.addTranslation(Locales.EN, cursor.getString(10));
                substantiv.addTranslation(Locales.RU, cursor.getString(11));
                substantiv.addTranslation(Locales.RO, cursor.getString(12));
                substantivs.add(substantiv);
            } while (cursor.moveToNext());

        } else Log.d(TAG, "DatabaseHelper, getSubstantivLIst cursor is empty: true");

        return substantivs;
    }

    public List<Rule> getRuleList() {
        List<Rule> ruleList = new ArrayList<>();
        mDataBase = getReadableDatabase();


        Cursor c = mDataBase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                Log.d("Rhemax", "DatabaseHelper, getrulelist, Table Name=> " + c.getString(0));
                c.moveToNext();
            }
        }
        Cursor dbCursor = mDataBase.query(ConstantsSQL.SUBSTANTIV_TABLE, null, null, null, null, null, null);

        String[] columnNames = dbCursor.getColumnNames();
        for (int i = 0; i < columnNames.length; i++) {
            Log.d("Rhemax", "DatabaseHelper, getRuleList, substTable columns: " + columnNames[i]);
        }

        Cursor cursor = mDataBase.rawQuery("select * from ruleTable", null);

        if (cursor.moveToFirst()) {
            do {
                Rule rule = new Rule();
                rule.setId(cursor.getInt(0));
                rule.setGender(cursor.getInt(1));
                rule.setRule(cursor.getString(2));
                rule.setExplanation(cursor.getString(3));
                rule.setException(cursor.getString(4));
                ruleList.add(rule);
            } while (cursor.moveToNext());
        }
        return ruleList;
    }


    public void update(int id, int score, int answerCount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantsSQL.SCORE, score);
        contentValues.put(ConstantsSQL.ANSWERS_COUNT, answerCount);
        String WHERE = ConstantsSQL.ID + "=" + id;
        int updatedRow = mDataBase.update(ConstantsSQL.SUBSTANTIV_TABLE, contentValues, WHERE, null);
    }


    public Substantiv getSubstantiv(String sqlRequest) {
        Cursor cursor = mDataBase.rawQuery(sqlRequest, null);
        Substantiv substantiv = new Substantiv();
        if (cursor.moveToFirst()) {
            substantiv.setId(cursor.getInt(0));
            substantiv.setName(cursor.getString(1));
            substantiv.setGender(cursor.getString(2));
            substantiv.setPluralForm(cursor.getString(3));
            substantiv.setExplanation(cursor.getString(4));
            substantiv.setRuleId(cursor.getInt(5));
            int ruleId = cursor.getInt(5);
            //TODO Optimize!!!
            for (Rule rule : rules) {
                if (rule.getId() == ruleId) {
                    substantiv.setRule(rule);
                    //   Log.d("Rhemax", "Rule.getiId = "+ rule.getId() + ", ruleId = " + ruleId + ": Substantivul: " + substantiv.getName() + ", Adaug regula: " + rule.getRule());
                }
            }
            if (substantiv.getRule() == null) {
                Rule rule = new Rule();
                rule.setRule("No sugestions");
                substantiv.setRule(rule);
            }
            substantiv.setFavorite(cursor.getInt(6) == 1 ? true : false);
            substantiv.setFrequencyRate(cursor.getInt(7));
            substantiv.setAnswersCount(cursor.getInt(8));
            substantiv.setScore(cursor.getInt(9));
        } else Log.d(TAG, "DatabaseHelper, getQuiz: cursor is empty");
        return substantiv;
    }

    public boolean resetAll() {
        //Random negativ counter for checking of update execution (!= (-1...+maxInt))
        int updatedCount = -2;
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantsSQL.SCORE, 0);
        contentValues.put(ConstantsSQL.ANSWERS_COUNT, 0);
        contentValues.put(ConstantsSQL.IS_FAVORITE, 0);
        String WHERE = ConstantsSQL.ANSWERS_COUNT + ">" + 0;
        Log.d(TAG, "DatabaseHelper, mDataBase isNull: " + (mDataBase == null) + ", mDataBase.isReadOnly(): " + mDataBase.isReadOnly() + ", isOpen(): " + mDataBase.isOpen());

        updatedCount = mDataBase.update(ConstantsSQL.SUBSTANTIV_TABLE, contentValues, WHERE, null);
     /*   mDataBase.rawQuery("UPDATE " + ConstantsSQL.SUBSTANTIV_TABLE + " SET "
                + ConstantsSQL.SCORE + "=0, " + ConstantsSQL.ANSWERS_COUNT + "=0"
                + " WHERE " + ConstantsSQL.ANSWERS_COUNT + ">0", null);*/
        boolean isUpdated = (updatedCount != -2) ? true : false;
        return isUpdated;
    }

}