package com.example.serhio.substantiv;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public StartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartFragment newInstance() {
        StartFragment fragment = new StartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        CardView learnCardView = view.findViewById(R.id.learn_card_view);
        CardView shuffleCardView = view.findViewById(R.id.shuffle_card_view);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.menuButtonClick(v.getId());
                }
            }
        };

        learnCardView.setOnClickListener(onClickListener);
        shuffleCardView.setOnClickListener(onClickListener);

/*       /Button startButton = view.findViewById(R.id.start_game_button);
        Button shuffleButton = view.findViewById(R.id.shuffle_game_button);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   onButtonPressed(v.getId());
                if (mListener != null) {
                    mListener.menuButtonClick(v.getId());

                }
            }
        };
        startButton.setOnClickListener(onClickListener);
        shuffleButton.setOnClickListener(onClickListener);*/
        return view;
    }

/*    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int resourceId) {

        switch (resourceId) {
            case R.id.start_game_button: {


                ScoreQuizFragment quizFragment = new ScoreQuizFragment();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, quizFragment);
                fragmentTransaction.commit();


               final QuizManager quizManager = new QuizManager(getContext(), quizFragment, new DefaultScenario(getContext()));

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        quizManager.nextQuiz();
                    }
                });
                break;
            }
        }
        if (mListener != null) {
            mListener.menuButtonClick(resourceId);

        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

        void menuButtonClick(int resourceId);
    }
}
