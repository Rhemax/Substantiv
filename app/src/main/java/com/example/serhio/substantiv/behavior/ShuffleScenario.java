package com.example.serhio.substantiv.behavior;

import android.content.Context;

import com.example.serhio.substantiv.entities.ConstantsSQL;
import com.example.serhio.substantiv.entities.Quiz;

    /*
    Study of all available words in random order. Points are not awarded.
    */

public class ShuffleScenario extends Scenario {


    public ShuffleScenario(Context context) {
        super(context);
    }

    @Override
    public boolean change(Quiz quiz) {
        return false;
    }


    @Override
    public String getSubstantivListSQLRequest() {
        return "SELECT * FROM " + ConstantsSQL.SUBSTANTIV_TABLE + " LIMIT 1000";
    }

    @Override
    public void updateQuiz(Quiz quiz, boolean response) {
    }

    @Override
    public String getNext() {
        return null;
    }

}
