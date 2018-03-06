package com.example.serhio.substantiv;

import android.os.AsyncTask;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Serhio on 26.02.2018.
 */

public class TimeoutOperation extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... params) {

        try {
            Log.i(TAG, "Going to sleep");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.i(TAG, "This is executed after 10 seconds and runs on the main thread");
        //Update your layout here
        super.onPostExecute(result);
    }
}