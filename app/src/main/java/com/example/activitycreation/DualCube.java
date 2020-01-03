package com.example.activitycreation;

import android.os.Bundle;

import cube.Cube;
import embeddedActivity.EmbeddedActivityClient;
import embeddedActivity.EmbeddedActivityHost;

public class DualCube extends EmbeddedActivityClient {

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.logMethodName();
        setContentView(R.layout.ss);
        log.log("savedInstanceState is " + savedInstanceState);
        if (savedInstanceState == null) {
            host.addClient(R.id.ssa, new Cube());
            host.addClient(R.id.ssb, new Cube());
            host.buildClients();
        }
    }
}