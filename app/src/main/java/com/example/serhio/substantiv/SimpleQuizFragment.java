package com.example.serhio.substantiv;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.serhio.substantiv.entities.QuizKeys;

public class SimpleQuizFragment extends QuizFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String SUBSTANTIV_NAME = "name";
    public static final String GENDER = "gender";
    public static final String RULE = "rule";
    public static final String TRANSLATION = "translation";
    private static final String TAG = "Rhemax";
    View.OnClickListener clickListener;
    Animation a;
    // TODO: Rename and change types of parameters
/*    private String substantivName;
    private String substantivGender;
    private String substantivRule;
    private String substantivTranslation;*/
    private TextView substantivTextView;
    private TextView genderTextView;
    private TextView ruleTextView;
    private TextView translationTextView;
    private Button derButton;
    private Button dieButton;
    private Button dasButton;
    private LinearLayout scoreContainer;
    private ImageView scoreIconView;
    private boolean showScore;
    private ScoreQuizFragment.OnFragmentInteractionListener mListener;
    private Color defaultButtonColor;


    public SimpleQuizFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.quiz_fragment, null);

        substantivTextView = view.findViewById(R.id.substantiv_text_view);
        genderTextView = view.findViewById(R.id.gender_text_view);
        ruleTextView = view.findViewById(R.id.rule_text_view);
        translationTextView = view.findViewById(R.id.translation_text_view);
        derButton = view.findViewById(R.id.der);
        dieButton = view.findViewById(R.id.die);
        dasButton = view.findViewById(R.id.das);
        scoreContainer = view.findViewById(R.id.score_container);
        scoreIconView = view.findViewById(R.id.scoreIcon);


        //TODO Optimize and clean this code
        // showNextQuiz(substantivName, substantivGender, substantivRule, substantivTranslation);
        //  a = AnimationUtils.loadAnimation(this.getContext(), R.anim.fade_in);

        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.answered(view.getId());

                showAnswer();
            }
        };

        derButton.setOnClickListener(clickListener);
        dieButton.setOnClickListener(clickListener);
        dasButton.setOnClickListener(clickListener);

        return view;
    }


    public void showNextQuiz() {
        String name = getArguments().getString(QuizKeys.SUBSTANTIV_NAME);
        String gender = getArguments().getString(QuizKeys.GENDER);
        String rule = getArguments().getString(QuizKeys.RULE);
        String translation = getArguments().getString(QuizKeys.TRANSLATION);
        int score = getArguments().getInt(QuizKeys.SCORE);
        showScore = getArguments().getBoolean(QuizKeys.SHOW_SCORE);

        updateScoreView(score);

        Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(200);

        Animation out = new AlphaAnimation(1.0f, 0.0f);
        in.setDuration(200);

        genderTextView.setVisibility(View.INVISIBLE);
        genderTextView.setText(gender);
        substantivTextView.startAnimation(in);
        substantivTextView.setText(name);
        ruleTextView.startAnimation(in);
        ruleTextView.setText(rule);
        translationTextView.setText(translation);
        setButtonsClickListener();

        derButton.setBackgroundResource(R.drawable.default_antwort_button);
        dieButton.setBackgroundResource(R.drawable.default_antwort_button);
        dasButton.setBackgroundResource(R.drawable.default_antwort_button);
        // derButton.setBackgroundColor(Color.BLUE);
        // dieButton.setBackgroundColor(Color.BLUE);
        // dasButton.setBackgroundColor(Color.BLUE);
    }

    private void hideScore() {
        // scoreIconView.setImageResource(android.R.color.transparent);
        scoreIconView.setImageDrawable(null);
    }

    public void showAnswer() {
        Animation a = AnimationUtils.loadAnimation(this.getContext(), R.anim.fade_in);

        //Animation a = AnimationUtils.loadAnimation(this.getContext(), R.anim.fade_in);
        a.reset();
        genderTextView.clearAnimation();
        // genderTextView.setText(gender);
        genderTextView.setVisibility(View.VISIBLE);
        genderTextView.startAnimation(a);
        clearButtonsClickListener();
    }
/*
    private void setButtonsClickListener() {
        derButton.setOnClickListener(clickListener);
        dieButton.setOnClickListener(clickListener);
        dasButton.setOnClickListener(clickListener);
    }

    private void clearButtonsClickListener() {
        derButton.setOnClickListener(null);
        dieButton.setOnClickListener(null);
        dasButton.setOnClickListener(null);
    }*/

    //TODO optimize and clean code
    public void updateScoreView(int score) {

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
        if (context instanceof ScoreQuizFragment.OnFragmentInteractionListener) {
            mListener = (ScoreQuizFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void showAnswer(int buttonId, boolean answer) {
        Button button = getView().findViewById(buttonId);
        //  if (answer) button.setBackgroundColor(Color.GREEN);
        //  else button.setBackgroundColor(Color.RED);
        if (answer) button.setBackgroundResource(R.drawable.right_antwort_button);
        else button.setBackgroundResource(R.drawable.wrong_antwort_button);

    }

    public void changeQuiz(Bundle bundle) {
        updateBundle(bundle);
        showNextQuiz();
    }

    protected void updateBundle(Bundle bundle) {
        super.updateBundle(bundle);
        getArguments().putInt(QuizKeys.SCORE, bundle.getInt(QuizKeys.SCORE));
        getArguments().putBoolean(QuizKeys.SHOW_SCORE, bundle.getBoolean(QuizKeys.SHOW_SCORE));
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        boolean answered(int id);

    }
}
