package com.example.serhio.substantiv.logic;

import com.example.serhio.substantiv.entities.Substantiv;

import java.util.List;

/**
 * Created by Serhio on 25.03.2018.
 */

public class QuizViewModel {
    private List<Substantiv> substantivList;
    private Substantiv currentSubstantiv;
    private DefaultScenario scenario;
    private DBHELPER dbHelper;

    public QuizViewModel() {
    }

    //
    public Substantiv getNext() {

        return null;
    }

    public void update(Substantiv substantiv) {

    }
}
