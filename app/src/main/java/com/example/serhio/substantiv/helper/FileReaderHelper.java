package com.example.serhio.substantiv.helper;

import android.content.Context;
import android.util.Log;

import com.example.serhio.substantiv.R;
import com.example.serhio.substantiv.entities.Substantiv;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Serhio on 30.04.2018.
 */

//Delete later
public class FileReaderHelper {

    Context context;

    public FileReaderHelper(Context context) {
        this.context = context;
    }

    public void saveToFile(String path, String file) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path));
            Log.d("Scrisul", "Scriu in: " + path + ": \n" + file);
            writer.write(file);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Substantiv[] getSubstantivFromJson() {
        Gson gson = new Gson();
        Substantiv[] substantivs = new Substantiv[0];
        try {
            substantivs = gson.fromJson(getJsonString(), Substantiv[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Substantiv subst : substantivs) {
            // Log.d("rezultat", "Subst: " + subst.toString());
        }
        return substantivs;
    }

    public String getJsonString() throws IOException {
        String str = "";
        StringBuffer buf = new StringBuffer();
        InputStream is = context.getResources().openRawResource(R.raw.original);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String result = "";
        if (is != null) {
            while ((str = reader.readLine()) != null) {
                result = result + str;
            }
        }
        return result;

    }
}
