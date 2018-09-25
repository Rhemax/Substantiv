package com.example.serhio.substantiv;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.serhio.substantiv.entities.GenderConstants;
import com.example.serhio.substantiv.entities.Locales;
import com.example.serhio.substantiv.entities.Quiz;
import com.example.serhio.substantiv.entities.QuizBundleHelper;
import com.example.serhio.substantiv.entities.QuizKeys;
import com.example.serhio.substantiv.logic.DefaultScenario;
import com.example.serhio.substantiv.logic.QuizManager;
import com.example.serhio.substantiv.logic.ShuffleScenario;

public class MainActivity extends AppCompatActivity implements QuizFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, StartFragment.OnFragmentInteractionListener {

    private String TAG = "Rhemax";
    private String CURRENT_QUIZ_ID_KEY = "currentQuizID";
    private String CHANGE_DELAY_KEY = "changeDelay";
    DrawerLayout mDrawerLayout;
    Runnable runnable;
    private QuizManager quizManager;
    private QuizFragment quizFragment;
    private Handler handler;
    private int changeDelay;
    private Locales locale = Locales.RU;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // if (savedInstanceState != null) return;

        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        handler = new Handler();
/*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionbar.setDisplayHomeAsUpEnabled(true);

        setNavigationListener();

        if (savedInstanceState != null) {
            return;
        }

        StartFragment startFragment = StartFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, startFragment);
        ft.commit();
    }


    //TODO optimize!!!
    public void next() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Quiz quiz = quizManager.getNext();
                QuizBundleHelper helper = new QuizBundleHelper();
                Bundle bundle = helper.createBundle()
                        .withGender(quizManager.getGender())
                        .withName(quizManager.getName())
                        .withTranslation(quizManager.getTranslation())
                        .withRule(quizManager.getRule())
                        .withScore(quizManager.getScore())
                        .withShowAnswerState(quizManager.showScore())
                        .asBundle();

                quizFragment.changeQuiz(bundle);

            }
        }, changeDelay);
    }

    public void menuButtonClick(int resourceId) {
        clearAllViews();
        changeDelay = 0;

        switch (resourceId) {

            case R.id.learn_card_view: {

                quizManager = new QuizManager(this, new DefaultScenario(this));
                quizFragment = new ScoreQuizFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, quizFragment);
                fragmentTransaction.addToBackStack(null).commit();
                // fragmentTransaction.commit();

                next();

                changeDelay = quizManager.getDelay();

                break;
            }

            case R.id.shuffle_card_view: {
                quizManager = new QuizManager(this, new ShuffleScenario(this));
                quizFragment = new QuizFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, quizFragment);
                fragmentTransaction.addToBackStack(null).commit();
                next();
                changeDelay = quizManager.getDelay();
                break;
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (runnable != null) handler.removeCallbacks(runnable);
        if (quizManager != null) {
            quizManager.closeDatabase();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        clearAllViews();
        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        switch (id) {
            case R.id.game_menu_item: {

                StartFragment startFragment = StartFragment.newInstance();
                fragmentTransaction.replace(R.id.container, startFragment);
                fragmentTransaction.addToBackStack(null).commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                quizFragment = null;
                return true;
            }

            case R.id.settings_menu_item: {


                PrefsFragment preferencesFragment = new PrefsFragment();
                fragmentTransaction.replace(R.id.container, preferencesFragment)
                        .addToBackStack(null).commit();

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

            case R.id.rules_menu_item: {
                RulesFragment rulesFragment = new RulesFragment();
                fragmentTransaction.replace(R.id.container, rulesFragment)
                        .addToBackStack(null).commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        }
        return false;
    }

    public boolean answered(int id) {

        boolean isRight = false;

        switch (id) {
            case R.id.der: {
                String gender = GenderConstants.DER;
                isRight = quizManager.setResponse(gender);
                break;
            }

            case R.id.die: {
                String gender = GenderConstants.DIE;
                isRight = quizManager.setResponse(gender);
                break;
            }

            case R.id.das: {
                String gender = GenderConstants.DAS;
                isRight = quizManager.setResponse(gender);
                break;
            }

        }

        int score = quizManager.getScore();
        Bundle arguments = quizFragment.getArguments();
        arguments.putInt(QuizKeys.SCORE, score);
        quizFragment.update(arguments);
        next();

        return isRight;
    }

    private void clearAllViews() {
        ViewGroup layout = findViewById(R.id.container);
        layout.removeAllViews();
    }

    private void setNavigationListener() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (quizManager != null)
            savedInstanceState.putInt(CURRENT_QUIZ_ID_KEY, quizManager.getCurrentQuizID());
        savedInstanceState.putInt(CHANGE_DELAY_KEY, changeDelay);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        quizManager = new QuizManager(this, new DefaultScenario(this));
        int currentQuizID = savedInstanceState.getInt(CURRENT_QUIZ_ID_KEY);
        changeDelay = savedInstanceState.getInt(CHANGE_DELAY_KEY);
        quizManager.setCurrentQuiz(currentQuizID);
        //QuizFragment originalQuizFragment = (QuizFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        //  Log.d(TAG, "Main, onRestoreState, quizFragment is null: " + (originalQuizFragment == null));
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof QuizFragment)
            quizFragment = (QuizFragment) fragment;
    }
/*
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }*/

/*    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }*/
}
