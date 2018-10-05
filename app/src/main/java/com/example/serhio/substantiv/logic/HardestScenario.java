package com.example.serhio.substantiv.logic;

import android.content.Context;
import android.util.Log;

import com.example.serhio.substantiv.entities.ConstantsSQL;
import com.example.serhio.substantiv.entities.Quiz;

public class HardestScenario extends Scenario {
    private int answersCountDecrease;
    private int scoreDifference;
    private int falsesCount;
    private float hardRatio;

    public HardestScenario(Context context) {
        super(context);
        answersCountDecrease = 2;
        scoreDifference = 1;
        falsesCount = 1;
        hardRatio = 2f;

    }

    @Override
    public boolean change(Quiz quiz) {
        return false;
    }

    @Override
    public String getSubstantivListSQLRequest() {
       // String sqlRequest = "SELECT * FROM " + ConstantsSQL.SUBSTANTIV_TABLE + WHERE + ConstantsSQL.ANSWERS_COUNT + " - " + ConstantsSQL.SCORE + " > " + scoreDifference;
        //String sqlRequest = "SELECT * FROM " + ConstantsSQL.SUBSTANTIV_TABLE + WHERE + ConstantsSQL.FALSES_COUNT + " > " + falsesCount;
        String sqlRequest = "SELECT * FROM " + ConstantsSQL.SUBSTANTIV_TABLE + WHERE + ConstantsSQL.FALSES_COUNT + " > " + falsesCount + " AND " + ConstantsSQL.SCORE + " < " + 4;
        return sqlRequest;
    }

    @Override
    public void updateQuiz(Quiz quiz, boolean response) {
/*        int answersCount = quiz.getAnswerCount();
        answersCount = response ? (answersCount - answersCountDecrease) : answersCount++;
        if (answersCount < 0) answersCount = 0;
        quiz.setAnswerCount(answersCount);
        int falses = quiz.getFalsesCOunt();
        if (!response) quiz.setFalsesCount(++falses);
        else quiz.setFalsesCount(--falses);*/
        int falses = quiz.getFalsesCOunt();
        Log.d("Rhemax", "Hard.Scen., hard. before: " + falses);
        falses = (falses <= 0) ? 0 : falses;
        quiz.setFalsesCount(falses);
        Log.d("Rhemax", "Hard.Scen., hard. after: " + falses);
    }

    @Override
    public String getNext() {
        return null;
    }

    @Override
    public boolean showScore() {
        return false;
    }
}
