package com.example.serhio.substantiv.entities;

import android.os.Bundle;
import android.support.v7.preference.PreferenceDialogFragmentCompat;

public class DialogPrefFragCompat extends PreferenceDialogFragmentCompat {
    public static DialogPrefFragCompat newInstance(String key) {
        final DialogPrefFragCompat fragment = new DialogPrefFragCompat();
        final Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            // do things
        }
    }
}