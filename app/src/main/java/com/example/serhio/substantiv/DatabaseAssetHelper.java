package com.example.serhio.substantiv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.util.Log;

import com.example.serhio.substantiv.entities.ConstantsSQL;
import com.example.serhio.substantiv.entities.Locales;
import com.example.serhio.substantiv.entities.Rule;
import com.example.serhio.substantiv.entities.Substantiv;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAssetHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "substantiv.db";
    private static final int DATABASE_VERSION = 1;
    private static String TAG = "Rhemax";
    private SQLiteDatabase mDataBase;

    public DatabaseAssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mDataBase = getWritableDatabase();
    }

    //TODO Optimize
    public List<Substantiv> getSubstantivList(String sqlRequest) {

        List<Substantiv> substantivs = new ArrayList<>();
         List<Rule> rules = getRuleList();

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
                for (Rule rule : rules) {
                    if (rule.getId() == ruleId) {
                        substantiv.setRule(rule);
                    }
                }
                if (substantiv.getRule() == null) {
                    Rule rule = new Rule();
                    rule.setRule("No sugestions");
                    substantiv.setRule(rule);
                }
                substantiv.setFavorite(cursor.getInt(6) == 1 ? true : false);
                substantiv.setFrequenceRate(cursor.getInt(7));
                substantiv.setAnswersCount(cursor.getInt(8));
                substantiv.setScore(cursor.getInt(9));
                substantiv.addTranslation(Locales.EN, cursor.getString(10));
                substantiv.addTranslation(Locales.RU, cursor.getString(11));
                substantiv.addTranslation(Locales.RO, cursor.getString(12));
                substantivs.add(substantiv);
            } while (cursor.moveToNext());

        };
        cursor.close();
        return substantivs;
    }

    public List<Rule> getRuleList() {
        List<Rule> ruleList = new ArrayList<>();

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
        cursor.close();
        return ruleList;
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
            ArrayList<Rule> rules = new ArrayList<>();
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
            substantiv.setFrequenceRate(cursor.getInt(7));
            substantiv.setAnswersCount(cursor.getInt(8));
            substantiv.setScore(cursor.getInt(9));
        } else Log.d(TAG, "DatabaseHelper, getQuiz: cursor is empty");
        return substantiv;
    }

    public void update(int id, int score, int answerCount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantsSQL.SCORE, score);
        contentValues.put(ConstantsSQL.ANSWERS_COUNT, answerCount);
        String WHERE = ConstantsSQL.ID + "=" + id;
        int updatedRow = mDataBase.update(ConstantsSQL.SUBSTANTIV_TABLE, contentValues, WHERE, null);
    }

    //todo optimize updatedCount - should be -1 and not -2
    public boolean resetAll() {
        //Random negativ counter for checking of update execution (!= (-1...+maxInt))
        int updatedCount = -2;
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantsSQL.SCORE, 0);
        contentValues.put(ConstantsSQL.ANSWERS_COUNT, 0);
        contentValues.put(ConstantsSQL.IS_FAVORITE, 0);
        String WHERE = ConstantsSQL.ANSWERS_COUNT + ">" + 0;

        updatedCount = mDataBase.update(ConstantsSQL.SUBSTANTIV_TABLE, contentValues, WHERE, null);
     /*   mDataBase.rawQuery("UPDATE " + ConstantsSQL.SUBSTANTIV_TABLE + " SET "
                + ConstantsSQL.SCORE + "=0, " + ConstantsSQL.ANSWERS_COUNT + "=0"
                + " WHERE " + ConstantsSQL.ANSWERS_COUNT + ">0", null);*/
        boolean isUpdated = (updatedCount != -2) ? true : false;
        return isUpdated;
    }
}
