package com.example.serhio.substantiv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.serhio.substantiv.entities.ConstantsSQL;
import com.example.serhio.substantiv.entities.Locales;
import com.example.serhio.substantiv.entities.Quiz;
import com.example.serhio.substantiv.entities.Rule;
import com.example.serhio.substantiv.entities.Substantiv;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;
/*
* The class that accesses the database.
* */
public class DBAssetHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "substantiv.db";
    private static final int DATABASE_VERSION = 1;
    private static String TAG = "Rhemax";
    private SQLiteDatabase mDataBase;

    public DBAssetHelper(Context context) {
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
                substantiv.setFrequencyRate(cursor.getInt(7));
                substantiv.setAnswersCount(cursor.getInt(8));
                substantiv.setFalseResponsesCount(cursor.getInt(9));
                substantiv.setScore(cursor.getInt(10));
                substantiv.addTranslation(Locales.EN, cursor.getString(11));
                substantiv.addTranslation(Locales.RU, cursor.getString(12));
                substantiv.addTranslation(Locales.RO, cursor.getString(13));
                substantivs.add(substantiv);
            } while (cursor.moveToNext());

        }
        cursor.close();
        return substantivs;
    }

    public List<Rule> getRuleList() {
        List<Rule> ruleList = new ArrayList<>();

        Cursor cursor = mDataBase.rawQuery("SELECT * FROM ruleTable", null);

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

            List<Rule> rules = getRuleList();

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
            substantiv.setFrequencyRate(cursor.getInt(7));
            substantiv.setAnswersCount(cursor.getInt(8));
            substantiv.setFalseResponsesCount(cursor.getInt(9));
            substantiv.setScore(cursor.getInt(10));
            substantiv.addTranslation(Locales.EN, cursor.getString(11));
            substantiv.addTranslation(Locales.RU, cursor.getString(12));
            substantiv.addTranslation(Locales.RO, cursor.getString(13));
        } else Log.d(TAG, "DBAssetHelper, getQuiz: cursor is empty");
        cursor.close();
        return substantiv;
    }

    public void update(Quiz quiz) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantsSQL.SCORE, quiz.getScore());
        contentValues.put(ConstantsSQL.ANSWERS_COUNT, quiz.getAnswerCount());
        contentValues.put(ConstantsSQL.FALSES_COUNT, quiz.getFalsesCount());
    }

    // Reset all current progress.
    public boolean resetAll() {
        //Random negativ counter for checking of update execution (!= (-1...+maxInt))
        int updatedCount = -2;
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantsSQL.SCORE, 0);
        contentValues.put(ConstantsSQL.ANSWERS_COUNT, 0);
        contentValues.put(ConstantsSQL.IS_FAVORITE, 0);
        contentValues.put(ConstantsSQL.FALSES_COUNT, 0);
        String WHERE = ConstantsSQL.ANSWERS_COUNT + ">" + 0;

        updatedCount = mDataBase.update(ConstantsSQL.SUBSTANTIV_TABLE, contentValues, WHERE, null);
        boolean isUpdated = (updatedCount != -2) ? true : false;
        return isUpdated;
    }
}
