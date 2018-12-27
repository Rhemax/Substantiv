package com.example.serhio.substantiv;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.serhio.substantiv.entities.QuizKeys;

/*
 * Fragment responsible for showing tests. The test is shown along without the score.
 */
public class ScoreQuizFragment extends QuizFragment {

    private LinearLayout scoreContainer;

    public ScoreQuizFragment() {
        // Required empty public constructor
    }


    @Override
    protected View getNextCard() {
        LayoutInflater inflater = getLayoutInflater();
        View newCardView = inflater.inflate(R.layout.quiz_score_card_view, null, false);
        populateNewCardView(newCardView);

        return newCardView;
    }

    @Override
    protected void populateNewCardView(View toPopulateView) {
        super.populateNewCardView(toPopulateView);
        scoreContainer = toPopulateView.findViewById(R.id.score_container);
        int score = getArguments().getInt(QuizKeys.SCORE);
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

    //TODO optimize and clean code
    private void updateScoreView(int score) {
        scoreContainer.removeAllViews();
        if (true) {
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
