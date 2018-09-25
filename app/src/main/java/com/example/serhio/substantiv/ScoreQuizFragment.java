package com.example.serhio.substantiv;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.serhio.substantiv.entities.QuizKeys;


public class ScoreQuizFragment extends QuizFragment {

    // Animation a;
    private LinearLayout scoreContainer;
    private ImageView scoreIconView;
    private boolean showScore;


    public ScoreQuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_score_quiz, null);
        this.initFields(view);
        this.attachClickListeners();

        return view;
    }

    @Override
    protected void initFields(View view) {
        super.initFields(view);
        scoreContainer = view.findViewById(R.id.score_container);
        scoreIconView = view.findViewById(R.id.scoreIcon);
    }


    public void showNextQuiz() {
        super.showNextQuiz();

        int score = getArguments().getInt(QuizKeys.SCORE);
        showScore = getArguments().getBoolean(QuizKeys.SHOW_SCORE);
        updateScoreView(score);
    }

    public void showAnswer() {
        Animation a = AnimationUtils.loadAnimation(this.getContext(), R.anim.fade_in);

        a.reset();
        genderTextView.clearAnimation();
        genderTextView.setVisibility(View.VISIBLE);
        genderTextView.startAnimation(a);
        clearButtonsClickListener();
    }

    @Override
    public void update(Bundle bundle) {
        int score = bundle.getInt(QuizKeys.SCORE);
        getArguments().putInt(QuizKeys.SCORE, score);
        updateScoreView(score);
    }

    //TODO optimize and clean code
    private void updateScoreView(int score) {

        scoreContainer.removeAllViews();
        if (showScore) {
            for (int x = 0; x < score; x++) {
                ImageView scoreImage = new ImageView(getContext());
                scoreImage.setBackgroundResource(R.drawable.star_gold);
                scoreContainer.addView(scoreImage);
            }
            for (int x = 4; x > score; x--) {
                ImageView scoreImage = new ImageView(getContext());
                scoreImage.setBackgroundResource(R.drawable.star_grey);
                scoreContainer.addView(scoreImage);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void changeQuiz(Bundle bundle) {
        updateBundle(bundle);
        showNextQuiz();
    }

    @Override
    protected void updateBundle(Bundle bundle) {
        super.updateBundle(bundle);
        getArguments().putInt(QuizKeys.SCORE, bundle.getInt(QuizKeys.SCORE));
        getArguments().putBoolean(QuizKeys.SHOW_SCORE, bundle.getBoolean(QuizKeys.SHOW_SCORE));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int score = getArguments().getInt(QuizKeys.SCORE);
        boolean showScore = getArguments().getBoolean(QuizKeys.SHOW_SCORE);

        outState.putInt(QuizKeys.SCORE, score);
        outState.putBoolean(QuizKeys.SHOW_SCORE, showScore);

    }

}
