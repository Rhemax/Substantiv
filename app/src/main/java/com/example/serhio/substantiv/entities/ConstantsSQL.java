package com.example.serhio.substantiv.entities;

/**
 * Created by Serhio on 20.03.2018.
 */

public class ConstantsSQL {


    public static final String DATABASE_NAME = "substantiv";
    public static final int DATABASE_VERSION = 1;


    //table names

    public static final String SUBSTANTIV_TABLE = "substantivTable";
    public static final String RULE_TABLE = "ruleTable";

    //column names
    // substantiv table
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String PLURAL_FORM = "pluralForm";
    public static final String EXPLANATION = "explanation";
    public static final String RULE_ID = "ruleId";
    public static final String IS_FAVORITE = "isFavorite";
    public static final String FREQUENCE_RATE = "frequenceRate";
    public static final String ANSWERS_COUNT = "answersCount";
    public static final String SCORE = "score";
    public static final String FALSES_COUNT = "falsesCount";

    // rules table
    public static final String RULE = "rule";
    public static final String ENDING = "ending";
    public static final String IS_ENDING_BASED = "isEndingBased";
    // create tables
    public static final String CREATE_SUBSTANTIV_TABLE = "CREATE TABLE " + ConstantsSQL.SUBSTANTIV_TABLE + "(" + ConstantsSQL.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ConstantsSQL.NAME + " TEXT," + ConstantsSQL.GENDER + " TEXT, " + ConstantsSQL.PLURAL_FORM + " TEXT, "
            + ConstantsSQL.EXPLANATION + " TEXT, " + ConstantsSQL.RULE_ID + " INTEGER, " + ConstantsSQL.IS_FAVORITE + " INTEGER, "
            + ConstantsSQL.FREQUENCE_RATE + " INTEGER, " + ConstantsSQL.ANSWERS_COUNT + " INTEGER, " + ConstantsSQL.SCORE + " INTEGER" + ")";


    public static final String CREATE_RULE_TABLE = "CREATE TABLE " + ConstantsSQL.RULE_TABLE + "(" + ConstantsSQL.RULE_ID + " INTEGER PRIMARY KEY, "
            + ConstantsSQL.RULE + " TEXT, " + ConstantsSQL.ENDING + "TEXT, " + ConstantsSQL.IS_ENDING_BASED + " INTEGER" + ")";

}
