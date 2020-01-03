package com.example.activitycreation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import cube.Cube;
import embeddedActivity.EmbeddedActivityHost;

public class MainActivity extends FragmentActivity {

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        host.log.logMethodName();
        host.restoreBundle(savedInstanceState);
        setContentView(R.layout.fragment_container);
        host.bindId(R.id.fragment_container);
        host.log.log("savedInstanceState is " + savedInstanceState);
        if (savedInstanceState == null) {
            host.addAndBuildClient(R.id.fragment_container, new Cube());
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        host.saveBundle(outState);
    }
}
