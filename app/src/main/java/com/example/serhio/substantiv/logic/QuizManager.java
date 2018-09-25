package com.example.serhio.substantiv.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.serhio.substantiv.R;
import com.example.serhio.substantiv.ScoreQuizFragment;
import com.example.serhio.substantiv.entities.GenderConstants;
import com.example.serhio.substantiv.entities.Locales;
import com.example.serhio.substantiv.entities.Quiz;

import java.util.List;
import java.util.Random;

/**
 * Created by Serhio on 27.03.2018.
 */

//TODO optimize Scenario setting
public class QuizManager {
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

    public String getResponse() {
        return currentQuiz.getResponse();
    }

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
        if (changeQuiz) {
            Quiz quiz = dbManager.getQuiz(scenario.getNext());
            quizList.add(quiz);
        }
    }

    public Quiz getNext() {
        Random random = new Random();
        int index = random.nextInt(quizList.size());
        currentQuiz = quizList.get(index);
        return currentQuiz;
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
/*
    public void nextQuiz() {
        currentQuiz = getNext();
        quizFragment.showNextQuiz(currentQuiz.getName(), currentQuiz.getGender(), currentQuiz.getRule());
        quizFragment.updateScoreView(currentQuiz.getScore());
    }*/

}


