package com.example.activitycreation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import embeddedActivity.EmbeddedActivityClient;

public class LayeredActivity1 extends EmbeddedActivityClient {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);
        if (savedInstanceState == null) {
            ((TextView)root.findViewById(R.id.activity_text)).setText((CharSequence) initializationExtras.get("text"));
        } else {
            ((TextView)root.findViewById(R.id.activity_text)).setText(savedInstanceState.getCharSequence("t"));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("t", ((TextView)root.findViewById(R.id.activity_text)).getText());
    }
}