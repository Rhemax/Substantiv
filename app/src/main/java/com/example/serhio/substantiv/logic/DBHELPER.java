package com.example.serhio.substantiv.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.serhio.substantiv.entities.ConstantsSQL;
import com.example.serhio.substantiv.entities.Substantiv;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhio on 20.03.2018.
 */

public class DBHELPER extends SQLiteOpenHelper {


    private static String DB_NAME = "ordersDB";
    private final SQLiteDatabase db;

    public DBHELPER(Context context) {
        super(context, ConstantsSQL.DATABASE_NAME, null, ConstantsSQL.DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("Rhemax", "Sunt in OnCreate");
        sqLiteDatabase.execSQL(ConstantsSQL.CREATE_SUBSTANTIV_TABLE);
        sqLiteDatabase.execSQL(ConstantsSQL.CREATE_RULE_TABLE);
        Log.d("Rhemax", "Acus fac PRAGMA table");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ConstantsSQL.SUBSTANTIV_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ConstantsSQL.RULE_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
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
        values.put(ConstantsSQL.FREQUENCE_RATE, substantiv.getFrequenceRate());
        values.put(ConstantsSQL.ANSWERS_COUNT, substantiv.getAnswersCount());
        values.put(ConstantsSQL.SCORE, substantiv.getScore());
        long inserted = db.insert(ConstantsSQL.SUBSTANTIV_TABLE, null, values);
        //  Log.d("Rhemaxus", "Inserted : " + inserted);

        return !(inserted == -1);
    }

    public List<Substantiv> getSubstantivList(String sqlRequest) {
        ArrayList<Substantiv> substantivs = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlRequest, null);
        if (cursor.moveToFirst()) {
            do {
                Substantiv substantiv = new Substantiv();
                substantiv.setId(cursor.getInt(0));
                substantiv.setName(cursor.getString(1));
                substantiv.setGender(cursor.getString(2));
                substantiv.setPluralForm(cursor.getString(3));
                substantiv.setExplanation(cursor.getString(4));
                substantiv.setRuleId(cursor.getInt(5));
                substantiv.setFavorite(cursor.getInt(6) == -1 ? true : false);
                substantiv.setFrequenceRate(cursor.getInt(7));
                substantiv.setAnswersCount(cursor.getInt(8));
                substantiv.setScore(cursor.getInt(9));
                substantivs.add(substantiv);
            } while (cursor.moveToNext());
        }
        return substantivs;
    }

    public void update(Substantiv substantiv) {

    }

}
