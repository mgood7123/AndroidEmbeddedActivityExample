package com.example.activitycreation;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

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
        setContentView(R.layout.activity_main);
        View x = host.setupHostFragmentContainer(
                R.layout.fragment_container,
                R.id.fragment_container,
                findViewById(R.id.SCREENSHOT),
                findViewById(R.id.SCREENSHOT_BUTTON)
        );
        ((FrameLayout)findViewById(R.id.View)).addView(x);
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
