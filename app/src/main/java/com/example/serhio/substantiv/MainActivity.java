package com.example.serhio.substantiv;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.serhio.substantiv.entities.Gender;
import com.example.serhio.substantiv.entities.Substantiv;
import com.example.serhio.substantiv.logic.SubstantivManager;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements TaskFragment.OnFragmentInteractionListener, SubstantivFragment.OnFragmentInteractionListener, ResponseFragment.OnFragmentInteractionListener{

    private static final String TAG = "Rhemax";
    private Substantiv substantiv;
    private TaskFragment taskFragment;
    private ResponseFragment responseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        substantiv = SubstantivManager.getNextSubstantiv();
        taskFragment = getNewTaskFragment(substantiv);
        responseFragment = getNewResponseFragment(substantiv);

        updateActivity(taskFragment, responseFragment);
    }

    private void updateActivity(TaskFragment taskFragment, ResponseFragment responseFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.task_frame_layout, taskFragment);
        fragmentTransaction.replace(R.id.response_frame_layout, responseFragment);
        fragmentTransaction.commit();
    }

    private TaskFragment getNewTaskFragment(Substantiv substantiv){
        taskFragment = new TaskFragment();
        Bundle taskFragmentBundle = new Bundle();
        taskFragmentBundle.putString("name", substantiv.getSubstantivName());
        taskFragmentBundle.putString("gender", substantiv.getGender().name());
        taskFragment.setArguments(taskFragmentBundle);

        return taskFragment;
    }

    private ResponseFragment getNewResponseFragment(Substantiv substantiv){

        responseFragment = new ResponseFragment();
        Bundle responseFragmentBundle = new Bundle();
        responseFragmentBundle.putString("gender", substantiv.getGender().name());
        responseFragment.setArguments(responseFragmentBundle);
        return responseFragment;
    }
    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    public void buttonPressed(){
        substantiv = SubstantivManager.getNextSubstantiv();
        taskFragment = getNewTaskFragment(substantiv);
        responseFragment = getNewResponseFragment(substantiv);
        updateActivity(taskFragment, responseFragment);

    }

}
