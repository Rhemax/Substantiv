package com.example.serhio.substantiv;

import android.content.Context;

import com.example.serhio.substantiv.entities.GenderConstants;
import com.example.serhio.substantiv.entities.Quiz;
import com.example.serhio.substantiv.behavior.Scenario;

import java.util.List;
import java.util.Random;

/*
 * Quiz Manager. Use the Scenario.class to request a list of quizzes from the database.
 */
//TODO optimize Scenario setting
public class QuizManager {
    private Scenario scenario;
    private Quiz currentQuiz;
    private DBManager dbManager;
    private List<Quiz> quizList;

    public QuizManager(Context context, Scenario scenario) {
        this.scenario = scenario;
        dbManager = new DBManager(context);
        quizList = dbManager.getQuizList(scenario);
    }

    public int getScore() {
        return currentQuiz.getScore();
    }

    //Replaces the current quiz in quizList with the next matching quiz.
    private void updateQuizList() {
        boolean changeQuiz = scenario.change(currentQuiz);
        if (changeQuiz) {
            Quiz quiz = dbManager.getQuiz(scenario.getNext());
            quizList.add(quiz);
            quizList.remove(currentQuiz);

        }
    }

    // Return random Quiz from quizList
    public Quiz getNextQuiz() {
        Random random = new Random();
        int quizListSize = quizList.size();
        if (quizListSize == 0) return null;
        int index = random.nextInt(quizListSize);
        currentQuiz = quizList.get(index);
        return currentQuiz;
    }

    //Returns the statistics of learned words in the form of an array in the following form:
    // [learnded words, three stars words, two stars words, one star words, zero star words]
    public int[] getStatistics() {
        return dbManager.getStatistic();
    }

    // Processing and saving user response. Returns true if the answer is correct and false if the answer is incorrect.
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

    // The method is called when activity is restored. Returns the quiz identifier at the time when the activity was stopped or destroyed.
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

    public int getQuizCount() {
        if (quizList != null) return quizList.size();
        return 0;
    }

}


