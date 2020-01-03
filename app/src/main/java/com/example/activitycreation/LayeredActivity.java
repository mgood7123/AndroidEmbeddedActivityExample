package com.example.activitycreation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import embeddedActivity.EmbeddedActivityClient;
import embeddedActivity.EmbeddedActivityHost;

public class LayeredActivity extends EmbeddedActivityClient {

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        host.restoreBundle(savedInstanceState);
        setContentView(R.layout.sample_activity);
        host.bindId(R.id.activity);
        if (savedInstanceState == null) {
            ((TextView)root.findViewById(R.id.activity_text)).setText((CharSequence) initializationExtras.get("text"));
            LayeredActivity1 x = new LayeredActivity1();
            x.initializationExtras.put("text", initializationExtras.get("text2"));
            host.addAndBuildClient(R.id.activity, x);
        } else {
            ((TextView)root.findViewById(R.id.activity_text)).setText(savedInstanceState.getCharSequence("t"));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        host.saveBundle(outState);
        outState.putCharSequence("t", ((TextView)root.findViewById(R.id.activity_text)).getText());
    }
}