package com.example.serhio.substantiv;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ResponseFragment extends Fragment {

    private static final String GENDER = "gender";

    // TODO: Rename and change types of parameters
    private String gender;
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
        Log.e("Rhemaxus", gender);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Testare", "Sunt in Response Fragment, onCreate");
        if (getArguments() != null) {
            for (String key : savedInstanceState.keySet()) {
                Log.e("Rhemaxus", "Keya: " + key);
                Object obj = savedInstanceState.get(key);   //later parse it as per your required type
            }
            gender = savedInstanceState.getString("name");
            Log.d("Rhemax", "Keya este: " + gender);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_response, container, false);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed(null);
                Log.d("Rhemax", "Buton apasat!");
            }
        };
        derButton = view.findViewById(R.id.der);
        dieButton = view.findViewById(R.id.die);
        dasButton = view.findViewById(R.id.das);

        derButton.setOnClickListener(clickListener);
        dieButton.setOnClickListener(clickListener);
        dasButton.setOnClickListener(clickListener);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
            mListener.buttonPressed();
            Log.d("Rhemax", "mListener nu este Null");
        } else Log.d("Rhemax", "mListener este Null");
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
