package com.example.serhio.substantiv;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;
import android.widget.Toast;


public class MyDialogPreference extends DialogPreference {

    public MyDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPersistent(false);


    }

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
                Handler handler = new Handler();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        DBAssetHelper dbAssetHelper = new DBAssetHelper(getContext());
                        dbAssetHelper.resetAll();
                        Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        dialog.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dlg, int which) {
                dlg.cancel();
            }
        });

        AlertDialog al = dialog.create();
        al.show();
    }

}

