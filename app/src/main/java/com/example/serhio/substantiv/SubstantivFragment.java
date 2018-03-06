package com.example.serhio.substantiv;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubstantivFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubstantivFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubstantivFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Handler handler = new Handler();
    private Runnable runnable;

    private OnFragmentInteractionListener mListener;

    public SubstantivFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubstantivFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubstantivFragment newInstance(String param1, String param2) {
        SubstantivFragment fragment = new SubstantivFragment();
        Bundle args = new Bundle();
        String substantiv = Long.toString(System.currentTimeMillis());
        args.putString(ARG_PARAM1, substantiv);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_substantiv, container, false);
        inflateButtons(view);

        return view;
    }

    private void inflateButtons(View view) {
        final TextView textView = view.findViewById(R.id.substantiv);
        textView.setText(Long.toString(System.currentTimeMillis()));
        final TextView ruleTextView = view.findViewById(R.id.rule);
        final Button button = view.findViewById(R.id.der);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setBackgroundColor(Color.GREEN);
                textView.setText(Html.fromHtml("Frag<strong>e</strong>"));


/*
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        //Second fragment after 5 seconds appears
                       FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                       fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                                fragmentTransaction.replace(R.id.frame_layout, new SubstantivFragment()).addToBackStack("Haha")
                                .commit();
                    }
                };*/

                handler.postDelayed(runnable, 3000);

            }
        });
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
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


    @Override
    public void onDestroy () {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
