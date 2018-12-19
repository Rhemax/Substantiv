package com.example.serhio.substantiv.entities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import com.example.serhio.substantiv.DatabaseAssetHelper;
import com.example.serhio.substantiv.R;


public class MyDialogPreference extends DialogPreference implements DialogInterface.OnClickListener {

    public MyDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPersistent(false);


    }

    //TODO optimize reset toast message
    //TODO Optimize UI updating from Thread
    @Override
    protected void onClick() {
        Resources resources = getContext().getResources();
        String alertTitle = resources.getString(R.string.reset_dialog_title);
        String alertMessage = resources.getString(R.string.reset_message);
        String positiveButtonText = resources.getString(R.string.positive_button_text);
        String negativeButtonText = resources.getString(R.string.negative_button_text);
        final String toastMessage = resources.getString(R.string.reset_toast_message);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        dialog.setTitle(alertTitle);
        dialog.setMessage(alertMessage);
        dialog.setCancelable(true);
        dialog.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //reset database

                Log.d("Rhemax", "MyDialogPreference onclick - clicked YES");


                Handler handler = new Handler();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       /* DatabaseHelper dbHelper = DatabaseHelper.getInstance(getContext());
                        dbHelper.resetAll();
                        Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();*/
                        DatabaseAssetHelper dbAssetHelper = new DatabaseAssetHelper(getContext());
                        dbAssetHelper.resetAll();
                        Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        dialog.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dlg, int which) {
                Log.d("Rhemax", "MyDialogPreference onclick - clicked NO");

                dlg.cancel();
            }
        });

        AlertDialog al = dialog.create();
        al.show();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
/*        Log.d("Rhemax", "MyDialogPreference onclick - whitout if-else");

        if (which == DialogInterface.BUTTON_POSITIVE) {
            Log.d("Rhemax", "MyDialogPreference onclick - clicked YES");
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            Log.d("Rhemax", "MyDialogPreference onclick - clicked NO");
        }*/
    }


}

