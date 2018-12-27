package com.example.serhio.substantiv.entities;

public class Quiz {
    private Substantiv substantiv;

    public Quiz(Substantiv substantiv) {
        this.substantiv = substantiv;
    }

    public String getGender() {
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

    public String getTranslation(Locales locale) {
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

    public void setAnswerCount(int count) {
        substantiv.setAnswersCount(count);
    }

    public void setFalsesCount(int count) {
        substantiv.setFalseResponsesCount(count);
    }

    public int getFalsesCount() {
        return substantiv.getFalseResponsesCount();
    }

    public int getId() {
        return substantiv.getId();
    }

}

