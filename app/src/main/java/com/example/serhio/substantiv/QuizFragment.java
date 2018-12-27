package com.example.serhio.substantiv;


import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.serhio.substantiv.entities.QuizKeys;

/*
 * Fragment responsible for showing tests. The test is shown along with the score.
 */
public class QuizFragment extends Fragment {

    private final static String CURRENT_ROW_SCORE = "currentRowScoreKey";
    private int currentRowScore;
    private int recordRowScore;
    protected TextView recordView;
    protected TextView currentView;
    protected View.OnClickListener clickListener;
    protected TextView genderTextView;
    protected Button derButton;
    protected Button dieButton;
    protected Button dasButton;
    protected QuizFragment.OnFragmentInteractionListener mListener;
    protected String recordKey;
    protected Handler handler;
    protected Runnable changeQuizRunnable;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setArguments(new Bundle());
        if(savedInstanceState != null) currentRowScore = savedInstanceState.getInt(CURRENT_ROW_SCORE);
        else currentRowScore = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz, null);
        initFields(view);
        attachClickListeners();

        return view;
    }

    protected void attachClickListeners() {
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isRight = mListener.answered(view.getId());
                LinearLayout backgroudLayout = getView().findViewById(R.id.substantiv_container);
                if (isRight) {
                    backgroudLayout.setBackgroundColor(getResources().getColor(R.color.greenSoft));
                    view.setBackgroundResource(R.drawable.right_antwort_button);
                } else {
                    view.setBackgroundResource(R.drawable.false_antwort_button);
                    backgroudLayout.setBackgroundColor(getResources().getColor(R.color.red));
                }
                showAnswer();
                updateRowScore(isRight);
            }
        };

        derButton.setOnClickListener(clickListener);
        dieButton.setOnClickListener(clickListener);
        dasButton.setOnClickListener(clickListener);
    }

    private void updateRowScore(boolean isRight) {
        currentRowScore = isRight ? (++currentRowScore) : 0;
        updateCurrentRowScore(currentRowScore);

        if (currentRowScore > recordRowScore) updateRecordRowScore(currentRowScore);
    }

    private void updateCurrentRowScore(int score) {
        currentView.setText("Current: " + score);
    }

    private void updateRecordRowScore(int score) {
        recordView.setText("Record: " + score);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(recordKey, String.valueOf(score));
        editor.commit();
    }

    protected void initFields(View view) {

        recordKey = getResources().getString(R.string.score_quiz_record_key);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        recordRowScore = Integer.parseInt(preferences.getString(recordKey, "0"));
        recordView = view.findViewById(R.id.record);
        updateRecordRowScore(recordRowScore);
        currentView = view.findViewById(R.id.current);
        updateCurrentRowScore(currentRowScore);
        derButton = view.findViewById(R.id.der);
        dieButton = view.findViewById(R.id.die);
        dasButton = view.findViewById(R.id.das);
    }

    protected void showNextQuiz() {
        View currentView = getView().findViewById(R.id.cardViewContainer);
        View newCardView = getNextCard();
        changeCards(currentView, newCardView);

        resetAntwortButtons();
    }

    // Get new card with new quiz
    protected View getNextCard() {
        LayoutInflater inflater = getLayoutInflater();
        View newCardView = inflater.inflate(R.layout.quiz_card_view, null, false);
        populateNewCardView(newCardView);

        return newCardView;
    }

    protected void resetAntwortButtons() {
        setButtonsClickListener();


        derButton.setBackgroundResource(R.drawable.default_antwort_button);
        dieButton.setBackgroundResource(R.drawable.default_antwort_button);
        dasButton.setBackgroundResource(R.drawable.default_antwort_button);
    }


    protected void populateNewCardView(View toPopulateView) {
        String name = getArguments().getString(QuizKeys.SUBSTANTIV_NAME);
        String gender = getArguments().getString(QuizKeys.GENDER);
        String rule = getArguments().getString(QuizKeys.RULE);
        String translation = getArguments().getString(QuizKeys.TRANSLATION);

        genderTextView = toPopulateView.findViewById(R.id.gender_text_view);
        TextView substantivView = toPopulateView.findViewById(R.id.substantiv_text_view);
        TextView translationView = toPopulateView.findViewById(R.id.translation_text_view);
        TextView ruleView = toPopulateView.findViewById(R.id.rule_text_view);

        genderTextView.setText(gender);
        genderTextView.setVisibility(View.INVISIBLE);
        substantivView.setText(name);
        translationView.setText(translation);
        ruleView.setText(rule);
    }

    protected void changeCards(View currentCardView, View newCardView) {
        ViewGroup.LayoutParams layoutParams = currentCardView.getLayoutParams();
        ViewGroup parent = (ViewGroup) currentCardView.getParent();
        int index = parent.indexOfChild(currentCardView);
        int currentViewIndexID = currentCardView.getId();

        Animation outToLeftAnimation = outToLeftAnimation();
        Interpolator interpolator = android.support.v4.view.animation.PathInterpolatorCompat.create(1.000f, 0.025f, 0.285f, 1.160f);
        outToLeftAnimation.setInterpolator(interpolator);
        currentCardView.setAnimation(outToLeftAnimation);
        parent.removeView(currentCardView);


        newCardView.setLayoutParams(layoutParams);
        newCardView.setId(currentViewIndexID);
        Animation inFromRightAnimation = inFromRightAnimation();
        inFromRightAnimation.setInterpolator(interpolator);
        newCardView.setAnimation(inFromRightAnimation);

        parent.addView(newCardView, index);
    }

    private Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(400);
        inFromRight.setFillAfter(true);
        inFromRight.setStartOffset(120);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    private Animation outToLeftAnimation() {
        Animation outToLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -2.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outToLeft.setDuration(300);
        outToLeft.setInterpolator(new AccelerateInterpolator());
        return outToLeft;
    }

    protected void showAnswer() {
        Animation a = AnimationUtils.loadAnimation(this.getContext(), R.anim.fade_in);
        a.reset();
        genderTextView.clearAnimation();
        genderTextView.setVisibility(View.VISIBLE);
        genderTextView.startAnimation(a);
        clearButtonsClickListener();
    }

    protected void setButtonsClickListener() {
        derButton.setOnClickListener(clickListener);
        dieButton.setOnClickListener(clickListener);
        dasButton.setOnClickListener(clickListener);
    }

    protected void clearButtonsClickListener() {
        derButton.setOnClickListener(null);
        dieButton.setOnClickListener(null);
        dasButton.setOnClickListener(null);
    }

    public void changeQuiz(Handler handler, Runnable changeQuizRunnable, Bundle bundle) {
        this.handler = handler;
        this.changeQuizRunnable = changeQuizRunnable;
        updateBundle(bundle);
        showNextQuiz();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QuizFragment.OnFragmentInteractionListener) {
            mListener = (QuizFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if (handler != null) handler.removeCallbacks(changeQuizRunnable);
    }

    // Save current quiz data in a fragment bundle
    protected void updateBundle(Bundle bundle) {
        getArguments().clear();
        getArguments().putString(QuizKeys.GENDER, bundle.getString(QuizKeys.GENDER));
        getArguments().putString(QuizKeys.SUBSTANTIV_NAME, bundle.getString(QuizKeys.SUBSTANTIV_NAME));
        getArguments().putString(QuizKeys.TRANSLATION, bundle.getString(QuizKeys.TRANSLATION));
        getArguments().putString(QuizKeys.RULE, bundle.getString(QuizKeys.RULE));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String gender = getArguments().getString(QuizKeys.GENDER);
        String name = getArguments().getString(QuizKeys.SUBSTANTIV_NAME);
        String translation = getArguments().getString(QuizKeys.TRANSLATION);
        String rule = getArguments().getString(QuizKeys.RULE);

        outState.putString(QuizKeys.GENDER, gender);
        outState.putString(QuizKeys.SUBSTANTIV_NAME, name);
        outState.putString(QuizKeys.TRANSLATION, translation);
        outState.putString(QuizKeys.RULE, rule);
        outState.putInt(CURRENT_ROW_SCORE, currentRowScore);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (savedInstanceState != null) {

            updateBundle(savedInstanceState);
            showNextQuiz();
        }
    }

    public interface OnFragmentInteractionListener {

        boolean answered(int id);

    }
}
