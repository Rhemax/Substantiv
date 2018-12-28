package com.example.serhio.substantiv.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.serhio.substantiv.R;
import com.example.serhio.substantiv.entities.Quiz;

/*
* The class that set the Quiz behavior and get the SQL Requests for forming a quizList
* */


public abstract class Scenario {

    protected static String LIMIT = " LIMIT ";
    protected static String WHERE = " WHERE ";
    protected static String ORDER_BY = " ORDER BY ";
    protected static String ASC = " ASC ";
    protected static String DESC = " DESC ";
    protected String locale;
    //TODO Optimize MaxScore set
    protected Context context;
    protected SharedPreferences preferences;

    public Scenario(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String localeKey = context.getResources().getString(R.string.locale_key);
        String defaultLocaleKey = "en";
        locale = preferences.getString(localeKey, defaultLocaleKey);
    }

    //Return true if  the quiz should be replaced with another quiz
    //i.e. if the quiz had enough points
    public abstract boolean change(Quiz quiz);

    //Get SQL request for populate #quizList in QuizManager class
    public abstract String getSubstantivListSQLRequest();

    //Update Quiz after response
    //i.e. update 'score' of Quiz regarding for false/true response
    public abstract void updateQuiz(Quiz quiz, boolean response);

    //Return a new SQL Request from SQL Database that past to Scenario criteria for getting a new Quiz object
    public abstract String getNext();


    //todo move method to an specialized class. This class is only for sql methods
    //Return delay between next Quiz show in seconds
    public int getDelay() {
        String changeSpeed = context.getResources().getString(R.string.change_speed);
        int quizChangeDelay = Integer.parseInt(preferences.getString(changeSpeed, "2"));
        return quizChangeDelay;
    }

    //TODO delete this method
    public abstract boolean showScore();
}
