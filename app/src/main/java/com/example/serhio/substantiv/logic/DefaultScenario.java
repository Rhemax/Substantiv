package com.example.serhio.substantiv.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.serhio.substantiv.R;
import com.example.serhio.substantiv.entities.ConstantsSQL;
import com.example.serhio.substantiv.entities.Quiz;

/**
 * Created by Serhio on 25.03.2018.
 */

public class DefaultScenario extends Scenario {

    private int quizCount;
    private int maxScore;
    private int maxId;

    public DefaultScenario(Context context) {
        super(context);
        String maxScoreKey = context.getResources().getString(R.string.default_max_score_key);
        String quizCounterKey = context.getResources().getString(R.string.default_quiz_count_key);
        maxScore = Integer.parseInt(preferences.getString(maxScoreKey, "4"));
       // quizCount = Integer.parseInt(preferences.getString(quizCounterKey, "30"));
                    quizCount = 3;
    }

    @Override
    public boolean change(Quiz quiz) {
        boolean change = (quiz.getScore() < maxScore) ? false : true;
        return change;
    }

    @Override
    public String getSubstantivListSQLRequest() {
        return "SELECT * FROM " + ConstantsSQL.SUBSTANTIV_TABLE + WHERE + ConstantsSQL.SCORE
                + " < " + maxScore + ORDER_BY + ConstantsSQL.FREQUENCE_RATE + ASC + LIMIT + quizCount;
    }

    @Override
    public void updateQuiz(Quiz quiz, boolean response) {

        int score = quiz.getScore();
        score = (response == true) ? ++score : --score;
        int falses = quiz.getFalsesCOunt();
       // Log.d("Rhemax", "DefaultScenario, falses before: " + falses);
        if (!response) quiz.setFalsesCount(falses++);
       // Log.d("Rhemax", "DefaultScenario, falses after: " + quiz.getFalsesCOunt());
        score = (score < 0) ? 0 : score;
        quiz.setScore(score);
        int answersCount = quiz.getAnswerCount();
        quiz.setAnswerCount(++answersCount);
    }

    @Override
    //TODO Optimize select - can cause problem when for example 51 is missing
    public String getNext() {
        String sqlRequest = "SELECT * FROM  (SELECT * FROM " + ConstantsSQL.SUBSTANTIV_TABLE +  " WHERE " + ConstantsSQL.SCORE + " < " + maxScore + " ORDER BY " + ConstantsSQL.FREQUENCE_RATE + " ASC " + " LIMIT " + (quizCount + 1) + ") " + " ORDER BY " + ConstantsSQL.FREQUENCE_RATE + " DESC " + " LIMIT " + 1;
        //return "SELECT * FROM " + ConstantsSQL.SUBSTANTIV_TABLE + " WHERE _id=" + ++quizCount;
        //Log.d("Rhemax" , "DefScenario, request: " + sqlRequest);
        return  sqlRequest;
    }

    @Override
    public boolean showScore(){
        return true;
    }


}
