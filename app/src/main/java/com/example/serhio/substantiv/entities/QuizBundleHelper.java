package com.example.serhio.substantiv.entities;

import android.os.Bundle;

public class QuizBundleHelper {

    private Bundle bundle;

    //Return simple empty Bundle that must be seted.
    public QuizBundleHelper createBundle() {
        bundle = new Bundle();
        return this;
    }

    public QuizBundleHelper withGender(String gender) {
        bundle.putString(QuizKeys.GENDER, gender);
        return this;
    }

    public QuizBundleHelper withName(String name) {
        bundle.putString(QuizKeys.SUBSTANTIV_NAME, name);
        return this;
    }

    public QuizBundleHelper withTranslation(String translation) {
        bundle.putString(QuizKeys.TRANSLATION, translation);
        return this;
    }

    public QuizBundleHelper withRule(String rule) {
        bundle.putString(QuizKeys.RULE, rule);
        return this;
    }

    public QuizBundleHelper withScore(int score) {
        bundle.putInt(QuizKeys.SCORE, score);
        return this;
    }

    public QuizBundleHelper withShowAnswerState(boolean showScore) {
        bundle.putBoolean(QuizKeys.SHOW_SCORE, showScore);
        return this;
    }


    public Bundle asBundle() {
        return bundle;
    }
}
