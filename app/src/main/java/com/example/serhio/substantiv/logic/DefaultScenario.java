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

    public DefaultScenario(Context context) {
        super(context);
        String maxScoreKey = context.getResources().getString(R.string.default_max_score_key);
        String quizCounterKey = context.getResources().getString(R.string.default_quiz_count_key);
        maxScore = Integer.parseInt(preferences.getString(maxScoreKey, "4"));
       // quizCount = Integer.parseInt(preferences.getString(quizCounterKey, "30"));
                    quizCount = 26;
    }

    @Override
    public boolean change(Quiz quiz) {
        boolean change = (quiz.getScore() <= maxScore) ? false : true;
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
        score = (score < 0) ? 0 : score;
        quiz.setScore(score);
    }

    @Override
    //TODO Optimize select - can cause problem when for example 51 is missing
    public String getNext() {
        return "SELECT * FROM " + ConstantsSQL.SUBSTANTIV_TABLE + " WHERE _id=" + ++quizCount;
    }

    @Override
    public boolean showScore(){
        return true;
    }


}
