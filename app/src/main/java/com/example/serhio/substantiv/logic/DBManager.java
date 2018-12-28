package com.example.serhio.substantiv.logic;

import android.content.Context;
import android.util.Log;

import com.example.serhio.substantiv.DatabaseAssetHelper;
import com.example.serhio.substantiv.entities.Quiz;
import com.example.serhio.substantiv.entities.Substantiv;
import com.example.serhio.substantiv.behavior.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private Context context;
    private DatabaseHelper dbhelper;
    private DatabaseAssetHelper dbAssetHelper;

    public DBManager(Context context) {
        this.context = context;
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
       // Substantiv substantiv = dbhelper.getSubstantiv(sqlRequest);
        Substantiv substantiv = dbAssetHelper.getSubstantiv(sqlRequest);

        return new Quiz(substantiv);
    }

    public int[] getStatistic(){
      List<Quiz> substantivs =  getQuizList(new ShuffleScenario(context));
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
        Log.d("Rhemax", "DBManager, statistic: list.size: " + substantivs.size() + "learned-" + data[0] + "; three-" + data[1] + "; two-" + data[2] + "; one-" + data[3] + "; to learn-" + data[4]);
        return data;
    }

    public void save(Quiz currentQuiz) {
        int id = currentQuiz.getId();
        int score = currentQuiz.getScore();
        int answerCount = currentQuiz.getAnswerCount();
        //dbhelper.update(id, score, ++answerCount);
       // dbAssetHelper.update(id, score, ++answerCount);
        dbAssetHelper.update(currentQuiz);
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
