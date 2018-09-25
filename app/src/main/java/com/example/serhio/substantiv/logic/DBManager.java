package com.example.serhio.substantiv.logic;

import android.content.Context;

import com.example.serhio.substantiv.DatabaseAssetHelper;
import com.example.serhio.substantiv.entities.Quiz;
import com.example.serhio.substantiv.entities.Substantiv;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhio on 27.03.2018.
 */

public class DBManager {
    private Context context;
    private DatabaseHelper dbhelper;
    private DatabaseAssetHelper dbAssetHelper;

    public DBManager(Context context) {
        this.context = context;/*
        dbhelper = DatabaseHelper.getInstance(context);
        dbhelper.openDataBase();*/
        dbAssetHelper = new DatabaseAssetHelper(context);
    }

    public List<Quiz> getQuizList(Scenario scenario) {
        ArrayList quizList = new ArrayList<>();

        List<Substantiv> substantivsList = getSubstantivs(scenario);
        for (int i = 0; i < substantivsList.size(); i++) {
            Quiz quiz = new Quiz(substantivsList.get(i));
            quizList.add(quiz);
        }
        return quizList;
    }

    private List<Substantiv> getSubstantivs(Scenario scenario) {
        String request = scenario.getSubstantivListSQLRequest();
        //  List<Substantiv> substList = dbhelper.getSubstantivList(request);
        List<Substantiv> substList = dbAssetHelper.getSubstantivList(request);

        return substList;
    }

    public Quiz getQuiz(String sqlRequest) {
        Substantiv substantiv = dbhelper.getSubstantiv(sqlRequest);
        return new Quiz(substantiv);
    }

    public void save(Quiz currentQuiz) {
        int id = currentQuiz.getId();
        int score = currentQuiz.getScore();
        int answerCount = currentQuiz.getAnswerCount();
        //dbhelper.update(id, score, ++answerCount);
        dbAssetHelper.update(id, score, ++answerCount);
    }

    public void resetAll() {
        // dbhelper.resetAll();
        dbAssetHelper.resetAll();
    }


    public void closeDatabase() {
        // if (dbhelper != null) dbhelper.close();
        if (dbAssetHelper != null) dbAssetHelper.close();
    }
}
