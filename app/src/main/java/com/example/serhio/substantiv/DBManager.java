package com.example.serhio.substantiv;

import android.content.Context;

import com.example.serhio.substantiv.entities.Quiz;
import com.example.serhio.substantiv.entities.Substantiv;
import com.example.serhio.substantiv.behavior.Scenario;
import com.example.serhio.substantiv.behavior.ShuffleScenario;

import java.util.ArrayList;
import java.util.List;


public class DBManager {
    private Context context;
    private DBAssetHelper dbAssetHelper;

    public DBManager(Context context) {
        this.context = context;
        dbAssetHelper = new DBAssetHelper(context);
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
        List<Substantiv> substList = dbAssetHelper.getSubstantivList(request);

        return substList;
    }

    public Quiz getQuiz(String sqlRequest) {
        Substantiv substantiv = dbAssetHelper.getSubstantiv(sqlRequest);

        return new Quiz(substantiv);
    }

    //Returns the statistics of learned words in the form of an array in the following form:
    // [learnded words, three stars words, two stars words, one star words, to learn words]
    public int[] getStatistic() {
        List<Quiz> substantivs = getQuizList(new ShuffleScenario(context));
        int[] data = new int[5];
        for (Quiz quiz : substantivs) {
            int score = quiz.getScore();

            switch (score) {
                case 0: {
                    data[4]++;
                    break;
                }
                case 1: {
                    data[3]++;
                    break;
                }
                case 2: {
                    data[2]++;
                    break;
                }
                case 3: {
                    data[1]++;
                    break;
                }
                case 4: {
                    data[0]++;
                    break;
                }
            }
        }
        return data;
    }

    public void save(Quiz currentQuiz) {
        dbAssetHelper.update(currentQuiz);
    }

    //TODO Implement reset function
    public void resetAll() {
        dbAssetHelper.resetAll();
    }


    public void closeDatabase() {
        if (dbAssetHelper != null) dbAssetHelper.close();
    }
}
