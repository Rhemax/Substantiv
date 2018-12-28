package com.example.serhio.substantiv;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.serhio.substantiv.entities.GenderConstants;
import com.example.serhio.substantiv.entities.Locales;
import com.example.serhio.substantiv.entities.Quiz;
import com.example.serhio.substantiv.entities.QuizBundleHelper;
import com.example.serhio.substantiv.behavior.DefaultScenario;
import com.example.serhio.substantiv.behavior.HardestScenario;
import com.example.serhio.substantiv.behavior.ShuffleScenario;
/*
 * The main class. The only activity.
 * Navigation in the application is carried out by replacing the current fragment with a new fragment.
 */
public class MainActivity extends AppCompatActivity implements QuizFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, StartFragment.OnFragmentInteractionListener {

    private String TAG = "Rhemax";
    private String CURRENT_QUIZ_ID_KEY = "currentQuizID";
    private String CHANGE_DELAY_KEY = "changeDelay";
    private DrawerLayout mDrawerLayout;
    private QuizManager quizManager;
    private QuizFragment quizFragment;
    private Handler handler;
    private Locales currentLocale;
    private int changeDelay;
    private Runnable changeQuizRunnable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        handler = new Handler();

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null) return;

        StartFragment startFragment = StartFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, startFragment);
        fragmentTransaction.commit();
    }

    public void showNextQuiz() {
        changeQuizRunnable = new Runnable() {
            @Override
            public void run() {
                Quiz quiz = quizManager.getNextQuiz();
                QuizBundleHelper helper = new QuizBundleHelper();
                Bundle bundle = helper.createBundle()
                        .withGender(quiz.getGender())
                        .withName(quiz.getName())
                        .withTranslation(quiz.getTranslation(currentLocale))
                        .withRule(quiz.getRule())
                        .withScore(quiz.getScore())
                        .asBundle();

                try {
                    quizFragment.changeQuiz(handler, this, bundle);

                } catch (NullPointerException exception) {
                    Log.d(TAG, "An error is occured. Can't show new Quiz.");
                }
            }
        };
        handler.postDelayed(changeQuizRunnable, changeDelay);
    }

    // Handling a click on the game option menu (StartFragment)
    public void menuButtonClick(int resourceId) {
        changeDelay = 0;
        currentLocale = getCurrentLocale();

        switch (resourceId) {

            case R.id.learn_button: {

                quizManager = new QuizManager(this, new DefaultScenario(this));
                quizFragment = new ScoreQuizFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, quizFragment).
                        addToBackStack(null).
                        commit();

                showNextQuiz();

                changeDelay = getDelay();

                break;
            }

            case R.id.shuffle_button: {
                quizManager = new QuizManager(this, new ShuffleScenario(this));
                quizFragment = new QuizFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, quizFragment);
                fragmentTransaction.addToBackStack(null).commit();
                showNextQuiz();
                changeDelay = getDelay();
                break;
            }

            //TODO Implement the algorithm for displaying the most difficult words
            case R.id.hardest_button: {
                quizManager = new QuizManager(this, new HardestScenario(this));
                int minCount = 10;
                if (quizManager.getQuizCount() < minCount) {
                    Toast.makeText(getApplicationContext(), "Not enough complicated words to display.  At least " + minCount + " words required", Toast.LENGTH_LONG).show();
                    break;
                } else {
                    quizFragment = new QuizFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, quizFragment);
                    fragmentTransaction.addToBackStack(null).commit();
                    showNextQuiz();
                    changeDelay = getDelay();
                }
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
        if (changeQuizRunnable != null) handler.removeCallbacks(changeQuizRunnable);
        if (quizManager != null) {
            quizManager.closeDatabase();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //clearAllViews();
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

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
                return true;
            }

            case R.id.progress_menu_item: {
                quizManager = new QuizManager(this, new DefaultScenario(this));
                int[] data = quizManager.getStatistics();
                ProgressFragment progressFragment = ProgressFragment.newInstance(data);
                fragmentTransaction.replace(R.id.container, progressFragment)
                        .addToBackStack(null).commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }


        }
        return false;
    }

// Processing and saving user response. Returns true if the answer is correct and false if the answer is incorrect.
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
        showNextQuiz();

        return isRight;
    }

    private void clearAllViews() {
        ViewGroup layout = findViewById(R.id.container);
        layout.removeAllViews();
    }

    private Locales getCurrentLocale() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String localeKey = getResources().getString(R.string.locale_key);
        Locales currentLocale = null;
        String defaultLocaleKey = "en";
        String currentLocales = preferences.getString(localeKey, defaultLocaleKey);
        switch (currentLocales) {
            case "en": {
                currentLocale = Locales.EN;
                break;
            }
            case "ru": {
                currentLocale = Locales.RU;
                break;
            }

            case "ro": {
                currentLocale = Locales.RO;
                break;
            }
        }
        return currentLocale;
    }

    // Return change delay between Quiz's in milliseconds.
    public int getDelay() {
        String changeSpeedKey = getResources().getString(R.string.change_speed);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int quizChangeDelay = Integer.parseInt(preferences.getString(changeSpeedKey, "2"));
        return quizChangeDelay * 1000;
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof QuizFragment)
            quizFragment = (QuizFragment) fragment;
    }


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
