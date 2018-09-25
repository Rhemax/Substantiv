package com.example.serhio.substantiv;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class QuizFragment extends Fragment {

    protected View.OnClickListener clickListener;
    protected TextView substantivTextView;
    protected TextView genderTextView;
    protected TextView ruleTextView;
    protected TextView translationTextView;
    protected Button derButton;
    protected Button dieButton;
    protected Button dasButton;
    protected QuizFragment.OnFragmentInteractionListener mListener;


    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz, null);
        initFields(view);
        attachClickListeners();

        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    protected void attachClickListeners() {
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isRight = mListener.answered(view.getId());
                LinearLayout backgroudLayout = getView().findViewById(R.id.substantiv_container);
                if (isRight) {
                    view.setBackgroundColor(Color.GREEN);
                    backgroudLayout.setBackgroundColor(getResources().getColor(R.color.greenSoft));
                } else {
                    view.setBackgroundColor(Color.RED);
                    backgroudLayout.setBackgroundColor(getResources().getColor(R.color.red));
                }
                showAnswer();
            }
        };

        derButton.setOnClickListener(clickListener);
        dieButton.setOnClickListener(clickListener);
        dasButton.setOnClickListener(clickListener);
    }

    protected void initFields(View view) {

        substantivTextView = view.findViewById(R.id.substantiv_text_view);
        genderTextView = view.findViewById(R.id.gender_text_view);
        ruleTextView = view.findViewById(R.id.rule_text_view);
        translationTextView = view.findViewById(R.id.translation_text_view);
        derButton = view.findViewById(R.id.der);
        dieButton = view.findViewById(R.id.die);
        dasButton = view.findViewById(R.id.das);
    }


    //TODO Update clickListener adding
    protected void showNextQuiz() {
        String name = getArguments().getString(QuizKeys.SUBSTANTIV_NAME);
        String gender = getArguments().getString(QuizKeys.GENDER);
        String rule = getArguments().getString(QuizKeys.RULE);
        String translation = getArguments().getString(QuizKeys.TRANSLATION);

        LayoutInflater inflater = getLayoutInflater();

        View currentView = getView().findViewById(R.id.cardViewContainer);


/*        ViewGroup.LayoutParams layoutParams = currentView.getLayoutParams();
        ViewGroup parent = (ViewGroup) currentView.getParent();
        int index = parent.indexOfChild(currentView);
        int currentViewIndexID = currentView.getId();

        Animation outToLeftAnimation = outToLeftAnimation();
        Interpolator interpolator = android.support.v4.view.animation.PathInterpolatorCompat.create(1.000f, 0.025f, 0.285f, 1.160f);
        outToLeftAnimation.setInterpolator(interpolator);
        currentView.setAnimation(outToLeftAnimation);

        parent.removeView(currentView);*/

        View cardView = inflater.inflate(R.layout.quiz_card_view, null, false);
        /*
        cardView.setLayoutParams(layoutParams);
        cardView.setId(currentViewIndexID);*/

        genderTextView = cardView.findViewById(R.id.gender_text_view);
        TextView substantivView = cardView.findViewById(R.id.substantiv_text_view);
        TextView translationView = cardView.findViewById(R.id.translation_text_view);
        TextView ruleView = cardView.findViewById(R.id.rule_text_view);
        genderTextView.setText(gender);
        genderTextView.setVisibility(View.INVISIBLE);
        substantivView.setText(name);
        translationView.setText(translation);
        ruleView.setText(rule);

/*        Animation inFromRightAnimation = inFromRightAnimation();
        inFromRightAnimation.setInterpolator(interpolator);
        cardView.setAnimation(inFromRightAnimation);

        parent.addView(cardView, index);*/

        changeCards(currentView, cardView);

        setButtonsClickListener();

        derButton.setBackgroundResource(R.drawable.default_antwort_button);
        dieButton.setBackgroundResource(R.drawable.default_antwort_button);
        dasButton.setBackgroundResource(R.drawable.default_antwort_button);
    }

    private void changeCards(View currentCardView, View newCardView){
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
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -2.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(300);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    /*1)inFromRightAnimation

    private Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(500);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
        }

 2)outToLeftAnimation
    private Animation outToLeftAnimation() {
    Animation outtoLeft = new TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, -1.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f);
    outtoLeft.setDuration(500);
    outtoLeft.setInterpolator(new AccelerateInterpolator());
    return outtoLeft;
    }

3)inFromLeftAnimation

    private Animation inFromLeftAnimation() {
    Animation inFromLeft = new TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, -1.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f);
    inFromLeft.setDuration(500);
    inFromLeft.setInterpolator(new AccelerateInterpolator());
    return inFromLeft;
    }

4)outToRightAnimation

    private Animation outToRightAnimation() {
    Animation outtoRight = new TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, +1.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f);
    outtoRight.setDuration(500);
    outtoRight.setInterpolator(new AccelerateInterpolator());
    return outtoRight;
    }*/
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

    public void changeQuiz(Bundle bundle) {
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
    }

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

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (savedInstanceState != null) {

            updateBundle(savedInstanceState);
            showNextQuiz();
        }
    }

    public void update(Bundle bundle) {

    }

    public interface OnFragmentInteractionListener {

        boolean answered(int id);

    }
}
