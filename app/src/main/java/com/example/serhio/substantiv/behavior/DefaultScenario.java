package com.example.serhio.substantiv.behavior;

import android.content.Context;

import com.example.serhio.substantiv.R;
import com.example.serhio.substantiv.entities.ConstantsSQL;
import com.example.serhio.substantiv.entities.Quiz;


/*
 *  Default training mode. Words learn alternately and with accrual and taking into account points
 */

public class DefaultScenario extends Scenario {

    private int quizCount;
    private int maxScore;

    public DefaultScenario(Context context) {
        super(context);
        String maxScoreKey = context.getResources().getString(R.string.default_max_score_key);
        maxScore = Integer.parseInt(preferences.getString(maxScoreKey, "4"));
                    quizCount = 10;
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
        score = (response) ? ++score : --score;
        int falsesCount = quiz.getFalsesCount();
        if (!response) quiz.setFalsesCount(++falsesCount);
        score = (score < 0) ? 0 : score;
        quiz.setScore(score);
        int answersCount = quiz.getAnswerCount();
        quiz.setAnswerCount(++answersCount);
    }

    @Override
    //TODO Optimize select - can cause problem when for example no more quizs are present
    public String getNext() {
        String sqlRequest = "SELECT * FROM  (SELECT * FROM " + ConstantsSQL.SUBSTANTIV_TABLE +  " WHERE "
                + ConstantsSQL.SCORE + " < " + maxScore + " ORDER BY " + ConstantsSQL.FREQUENCE_RATE
                + " ASC " + " LIMIT " + (quizCount + 1) + ") " + " ORDER BY "
                + ConstantsSQL.FREQUENCE_RATE + " DESC " + " LIMIT " + 1;
        return  sqlRequest;
    }


}
