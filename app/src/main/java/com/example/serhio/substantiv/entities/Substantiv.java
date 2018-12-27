package com.example.serhio.substantiv.entities;

/**
 * Created by Serhio on 01.03.2018.
 */

public class Substantiv {
    private int id;
    private String name;
    private String gender;
    private String pluralForm;
    private String explanation;
    private Rule rule;
    private int ruleId;
    private boolean isFavorite;
    private int frequencyRate;
    private int answersCount;
    private int score;
    private Translation translations;
    private int falseResponsesCount;

    public Substantiv() {
        translations = new Translation();
    }

    public Substantiv(String name, String gender) {
        this.name = name;
        this.gender = gender;
        translations = new Translation();
    }

    public void addTranslation(Locales locale, String translation) {
        translations.addTranslation(locale, translation);
    }

    public String getTranslation(Locales locale){
        return translations.getTranslation(locale);
    }

    public String getPluralForm() {
        return pluralForm;
    }

    public void setPluralForm(String pluralForm) {
        this.pluralForm = pluralForm;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getFrequencyRate() {
        return frequencyRate;
    }

    public void setFrequencyRate(int frequencyRate) {
        this.frequencyRate = frequencyRate;
    }

    public int getAnswersCount() {
        return answersCount;
    }

    public void setAnswersCount(int answersCount) {
        this.answersCount = answersCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Frequency: " + frequencyRate
                + ", Name: " + name
                + ", Gender: " + gender
                + ", Plural form: " + pluralForm;
    }

    public int getFalseResponsesCount() {
        return falseResponsesCount;
    }

    public void setFalseResponsesCount(int falseResponsesCount) {
        this.falseResponsesCount = falseResponsesCount;
    }


    private class Translation {
        private String english;
        private String russian;
        private String romanian;

        public void addTranslation(Locales locale, String translation){
            switch (locale){

                case EN: english = translation;
                    break;

                case RU: russian = translation;
                    break;

                case RO: romanian = translation;
                    break;
            }
        }

        public String getTranslation(Locales locale) {

            switch (locale){
                case EN: return english;
                case RU: return russian;
                case RO: return romanian;
            }

            return null;
        }
    }

}
