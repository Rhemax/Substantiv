package com.example.serhio.substantiv.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.serhio.substantiv.R;
import com.example.serhio.substantiv.ScoreQuizFragment;
import com.example.serhio.substantiv.entities.GenderConstants;
import com.example.serhio.substantiv.entities.Locales;
import com.example.serhio.substantiv.entities.Quiz;

import java.security.acl.LastOwnerException;
import java.util.List;
import java.util.Random;

/**
 * Created by Serhio on 27.03.2018.
 */

//TODO optimize Scenario setting
public class QuizManager {
    private final String TAG = "Rhemax";
    private Context context;
    private String LANGUAGE = "language";
    private Locales locale;
    public Scenario scenario;
    private Quiz currentQuiz;
    private DBManager dbManager;
    private List<Quiz> quizList;
    private ScoreQuizFragment.OnFragmentInteractionListener mListener;


    //TODO Optimize currentQuiz initialisation
    public QuizManager(Context context, Scenario scenario) {
        this.context = context;
        this.scenario = scenario;
        setLocale();
        dbManager = new DBManager(context);
        quizList = dbManager.getQuizList(scenario);
        currentQuiz = getNext();
        Log.d(TAG, "QuizManager: quizList.size: " + quizList.size());
        for (Quiz quizL: quizList){
            Log.d(TAG, "START!!!!!! Name: " + quizL.getName() + ", Score: " + quizL.getScore());

        }
        for (Quiz quiz: quizList){
            //Log.d(TAG, "QuizManager: name: " + quiz.getName() + ", score: " + quiz.getScore() + ", answers: " + quiz.getAnswerCount());
        }
        // Log.d("Rhemax", "QuizManager: quizLIst size: " + quizList.size());
    }

    private void setLocale() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String localeKey = context.getResources().getString(R.string.locale_key);
        String defaultLocaleKey = "en";
        String currentLocales = preferences.getString(localeKey, defaultLocaleKey);
        switch (currentLocales) {
            case "en": {
                locale = Locales.EN;
                break;
            }
            case "ru": {
                locale = Locales.RU;
                break;
            }

            case "ro": {
                locale = Locales.RO;
                break;
            }
        }

    }

    public String getName() {
        return currentQuiz.getName();
    }

    public String getRule() {
        return currentQuiz.getRule();
    }

   /* public String getResponse() {
        return currentQuiz.getResponse();
    }*/

    public String getGender() {
        return currentQuiz.getGender();
    }

    public String getTranslation() {
        return currentQuiz.getTranslation(locale);
    }

    public boolean showScore() {
        return scenario.showScore();
    }

    public void setResponse(boolean response) {
        scenario.updateQuiz(currentQuiz, response);
        updateQuizList();
        dbManager.save(currentQuiz);
    }

    public int getScore() {
        return currentQuiz.getScore();
    }

    private void updateQuizList() {
        boolean changeQuiz = scenario.change(currentQuiz);
       // Log.d(TAG, "QuizMan., name: " + currentQuiz.getName() + "changeQuiz: " + changeQuiz + ", Score: " + currentQuiz.getScore());
        if (changeQuiz) {
            Quiz quiz = dbManager.getQuiz(scenario.getNext());
         //   Log.d(TAG, "QuizManag., changeQuiz true, new quiz: " + quiz.getName()+ ", new score: " + quiz.getScore() + ", listSize: " + quizList.size());
            quizList.add(quiz);
            quizList.remove(currentQuiz);

/*            Log.d(TAG, "QuizMan, new Quiz: name" + quiz.getName() + ", rule: " + quiz.getRule() + ", list.size: " + quizList.size());
            for (Quiz quizL: quizList){
                Log.d(TAG, "Name: " + quizL.getName() + ", Score: " + quizL.getScore());

            }*/
        }
    }

    //todo resolve double get of this method by first call
    public Quiz getNext() {
        Random random = new Random();
        int quizListSize = quizList.size();
        if (quizListSize == 0) return null;
        int index = random.nextInt(quizListSize);
        currentQuiz = quizList.get(index);
      //  Log.d(TAG, "QuizManager, getNext. Name: " + currentQuiz.getName() + ", Score: " + currentQuiz.getScore() + "AnswersCount: " + currentQuiz.getAnswerCount() + ", Translation: " + currentQuiz.getTranslation(Locales.RU));
        return currentQuiz;
    }

    public int[] getStatistics(){
        return dbManager.getStatistic();
    }

    public void resetAll() {
        dbManager.resetAll();
    }

    public int getDelay() {
        return scenario.getDelay() * 1000;
    }

    public boolean setResponse(String gender) {
        boolean response = false;
        switch (gender) {
            case GenderConstants.DER: {
                response = gender.equalsIgnoreCase(currentQuiz.getGender());
                break;
            }
            case GenderConstants.DIE: {
                response = gender.equalsIgnoreCase(currentQuiz.getGender());
                break;
            }
            case GenderConstants.DAS: {
                response = gender.equalsIgnoreCase(currentQuiz.getGender());
                break;
            }
        }
        scenario.updateQuiz(currentQuiz, response);
        updateQuizList();
        dbManager.save(currentQuiz);
        return response;
    }

    public int getCurrentQuizID() {
        return currentQuiz.getId();
    }

    public void setCurrentQuiz(int currentQuizID) {
        for (Quiz quiz : quizList) {
            if (quiz.getId() == currentQuizID) {
                currentQuiz = quiz;
                break;
            }

        }
    }

    public void closeDatabase() {
        if (dbManager != null) {
            dbManager.closeDatabase();
        }
    }


    public int getFalsesCount() {
        return currentQuiz.getFalsesCount();
    }

    public int getQuizCount() {
       // Log.d(TAG, "QuizManager, list.size: " + quizList.size());
        if (quizList != null) return quizList.size();
        return 0;
    }
/*
    public void nextQuiz() {
        currentQuiz = getNext();
        quizFragment.showNextQuiz(currentQuiz.getName(), currentQuiz.getGender(), currentQuiz.getRule());
        quizFragment.updateScoreView(currentQuiz.getScore());
    }*/

}


