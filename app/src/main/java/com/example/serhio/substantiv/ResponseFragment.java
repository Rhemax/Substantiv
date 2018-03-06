package com.example.serhio.substantiv;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.serhio.substantiv.entities.Gender;
import com.example.serhio.substantiv.entities.Substantiv;
import com.example.serhio.substantiv.logic.SubstantivManager;


public class ResponseFragment extends Fragment {

    private static final String GENDER = "gender";

    // TODO: Rename and change types of parameters
    private Gender gender;
    private Button derButton;
    private Button dieButton;
    private Button dasButton;

    private OnFragmentInteractionListener mListener;

    public ResponseFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static ResponseFragment newInstance(String gender) {
        ResponseFragment fragment = new ResponseFragment();
        Bundle args = new Bundle();
        args.putString(GENDER, gender);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            gender = Gender.valueOf(getArguments().getString(GENDER));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_response, container, false);
        derButton = view.findViewById(R.id.der);
        dieButton = view.findViewById(R.id.die);
        dasButton = view.findViewById(R.id.das);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Rhemax", "A fost apasat butonul: " + view.getId());
                switch (gender){
                    case DER:
                        derButton.setBackgroundColor(Color.GREEN);
                        if (view.getId()!= derButton.getId()) {
                            view.setBackgroundColor(Color.RED);
                        };
                        mListener.buttonPressed();
                        break;
                    case DIE:
                        dieButton.setBackgroundColor(Color.GREEN);
                        if(view.getId() != dieButton.getId()) {
                            view.setBackgroundColor(Color.RED);
                        };

                        mListener.buttonPressed();
                        break;
                    case DAS:
                        dasButton.setBackgroundColor(Color.GREEN);
                        if (view.getId() != dasButton.getId()){
                            view.setBackgroundColor(Color.RED);
                        };
                        mListener.buttonPressed();
                        break;
                }
            }
        };
        derButton.setOnClickListener(onClickListener);
        dieButton.setOnClickListener(onClickListener);
        dasButton.setOnClickListener(onClickListener);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * ""
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void buttonPressed();
    }
}
