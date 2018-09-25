package com.example.serhio.substantiv.entities;

/**
 * Created by Serhio on 27.03.2018.
 */

public class Quiz {
    private Substantiv substantiv;

    public Quiz(Substantiv substantiv) {
        this.substantiv = substantiv;
    }

    public String getGender() {
        return substantiv.getGender();
    }

    public String getResponse() {
        return substantiv.getGender();
    }

    public String getName() {
        return substantiv.getName();
    }

    public Substantiv getSubstantiv() {
        return substantiv;
    }

    public String getRule() {
        return substantiv.getRule().getRule();
    }

    public String getTranslation(Locales locale){
        return substantiv.getTranslation(locale);
    }

    public int getScore() {
        return substantiv.getScore();
    }

    public void setScore(int score) {
        substantiv.setScore(score);
    }

    public int getAnswerCount() {
        return substantiv.getAnswersCount();
    }

    public int getId() {
        return substantiv.getId();
    }

    public boolean isFavorite() {
        return substantiv.isFavorite();
    }

    public void setFavorite(boolean isFavorite) {
        substantiv.setFavorite(isFavorite);
    }

    public void setAnswer(boolean answer) {
        int score = substantiv.getScore();
        if (answer) substantiv.setScore(score++);
        else substantiv.setScore(score--);

        int answerCount = substantiv.getAnswersCount();
        substantiv.setAnswersCount(answerCount++);
    }
}
