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
            QuadLayeredActivity x = new QuadLayeredActivity();
            x.initializationExtras.put("A", new demo()); // 16
            x.initializationExtras.put("B", new demo()); // 32
            QuadLayeredActivity x2 = new QuadLayeredActivity();
            x2.initializationExtras.put("A", new Cube());
            x2.initializationExtras.put("B", new Cube());
            x2.initializationExtras.put("C", new Cube());
//            x2.initializationExtras.put("D", new Cube());
            QuadLayeredActivity x5 = new QuadLayeredActivity();
            x5.initializationExtras.put("A", new QuadCube()); // 36
            x5.initializationExtras.put("B", new QuadCube()); // 40
            x5.initializationExtras.put("C", new QuadCube()); // 44
            x5.initializationExtras.put("D", x2); // 47
            x.initializationExtras.put("C", x5);
            host.addAndBuildClient(R.id.fragment_container, x);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        host.saveBundle(outState);
    }
}
