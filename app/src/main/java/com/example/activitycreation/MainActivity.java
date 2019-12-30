package com.example.activitycreation;

import android.os.Bundle;

import cube.Cube;
import embeddedActivity.EmbeddedActivityHost;

public class MainActivity extends EmbeddedActivityHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.logMethodName();
        setContentView(R.layout.fragment_container_split_screen);
        log.log("savedInstanceState is " + savedInstanceState);
        if (savedInstanceState == null) {
            embeddedActivity_addClient(R.id.fragment_containerA, new Cube());
            embeddedActivity_addClient(R.id.fragment_containerB, new Cube());
            embeddedActivity_buildClients();
        }
    }
}
