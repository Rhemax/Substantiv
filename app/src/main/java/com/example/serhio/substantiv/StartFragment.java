package com.example.serhio.substantiv;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/*
 * Fragment with a choice of game mode
 */
public class StartFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public StartFragment() {
        // Required empty public constructor
    }

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
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        Button learnButton = view.findViewById(R.id.learn_button);
        Button shuffleButton = view.findViewById(R.id.shuffle_button);
        Button hardestButton = view.findViewById(R.id.hardest_button);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.menuButtonClick(v.getId());
                }
            }
        };

        learnButton.setOnClickListener(onClickListener);
        shuffleButton.setOnClickListener(onClickListener);
        hardestButton.setOnClickListener(onClickListener);

        return view;
    }

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

    public interface OnFragmentInteractionListener {

        void menuButtonClick(int resourceId);
    }
}
