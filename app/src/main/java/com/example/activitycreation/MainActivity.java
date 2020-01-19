package com.example.activitycreation;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import SimpleColor.SimpleColor;
import embeddedActivity.EmbeddedActivityHost;

public class MainActivity extends FragmentActivity {

    EmbeddedActivityHost host;
    String keyA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        host = new EmbeddedActivityHost(this);

        keyA = "a";

        host.log.logMethodName();
        host.restoreBundle(savedInstanceState);
        setContentView(R.layout.view_switcher);

        View A = host.generateContainer();

        host.bindId(A, keyA);

        ((FrameLayout)findViewById(R.id.viewFlipper)).addView(A);
        A = null;

        findViewById(R.id.show_view_A)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentVisibility = ((SimpleColor) host.findClientByKey(keyA))
                                .getVisibility();
                        if (currentVisibility == View.VISIBLE)
                            ((SimpleColor) host.findClientByKey(keyA))
                                    .setVisibility(View.INVISIBLE);
                        if (currentVisibility == View.INVISIBLE)
                            ((SimpleColor) host.findClientByKey(keyA))
                                    .setVisibility(View.VISIBLE);
                    }
                });

        if (savedInstanceState == null) {
            host.addAndBuildClient(keyA, new SimpleColor());
            ((SimpleColor) host.findClientByKey(keyA)).setVisibility(View.INVISIBLE);
        } else {
            ((SimpleColor) host.findClientByKey(keyA)).setVisibility(savedInstanceState.getInt(keyA));
        }
    }

    @Override
    protected void onDestroy() {
        host.setFragmentActivity(null);
        host = null;
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        host.saveBundle(outState);
        outState.putInt(keyA, ((SimpleColor)host.findClientByKey(keyA)).getVisibility());
    }
}
