package com.example.serhio.substantiv.behavior;

import android.content.Context;

import com.example.serhio.substantiv.entities.ConstantsSQL;
import com.example.serhio.substantiv.entities.Quiz;

    /*
    Learning the most difficult words.
    */
public class HardestScenario extends Scenario {
    private int falsesCount;
    private int limit;

    //TODO change test parameters!!
    public HardestScenario(Context context) {
        super(context);
        falsesCount = 1;
        limit = 2;
    }

    @Override
    public boolean change(Quiz quiz) {
        return false;
    }

    @Override
    public String getSubstantivListSQLRequest() {
        String sqlRequest = "SELECT * FROM " + ConstantsSQL.SUBSTANTIV_TABLE + WHERE + ConstantsSQL.FALSES_COUNT + " > " + falsesCount + " AND " + ConstantsSQL.SCORE + " < " + 4 + " ORDER BY " + ConstantsSQL.FALSES_COUNT + " DESC " + " LIMIT " + limit;
        return sqlRequest;
    }

    @Override
    public void updateQuiz(Quiz quiz, boolean response) {
        int falsesCount = quiz.getFalsesCount();
        falsesCount = (response) ? --falsesCount : ++falsesCount;
        falsesCount = (falsesCount <= 0) ? 0 : falsesCount;
        quiz.setFalsesCount(falsesCount);
    }

    @Override
    public String getNext() {
        return null;
    }

}
