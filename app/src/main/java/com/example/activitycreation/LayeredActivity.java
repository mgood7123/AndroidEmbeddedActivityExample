package com.example.activitycreation;

import android.os.Bundle;
import android.widget.TextView;

import embeddedActivity.EmbeddedActivityClient;
import embeddedActivity.EmbeddedActivityHost;

public class LayeredActivity extends EmbeddedActivityClient {
    CharSequence text;
    CharSequence text2;
    String tag;
    LayeredActivity(CharSequence text, CharSequence text2) {
        this.text = text;
        this.text2 = text2;
    }

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);
        if (savedInstanceState == null) {
            ((TextView)root.findViewById(R.id.activity_text)).setText(text);
            host.addAndBuildClient(R.id.activity, new LayeredActivity1(text2));
        }
    }
}