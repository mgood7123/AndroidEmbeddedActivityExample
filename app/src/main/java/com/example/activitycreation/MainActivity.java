package com.example.activitycreation;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import embeddedActivity.EmbeddedActivityHost;

public class MainActivity extends FragmentActivity {

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        host.log.logMethodName();
        setContentView(R.layout.fragment_container);
        host.log.log("savedInstanceState is " + savedInstanceState);
        if (savedInstanceState == null) {
//            host.addAndBuildClient(R.id.fragment_container, new Cube());
            host.addAndBuildClient(R.id.fragment_container, new DualLayeredActivity(new demo(), new DualLayeredActivity()));
        }
    }
}
