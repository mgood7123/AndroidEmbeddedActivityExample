package com.example.activitycreation;

import android.os.Bundle;

import cube.Cube;
import embeddedActivity.EmbeddedActivityClient;
import embeddedActivity.EmbeddedActivityHost;

public class QuadCube extends EmbeddedActivityClient {

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.logMethodName();
        setContentView(R.layout.fragment_container_quad_screen);
        log.log("savedInstanceState is " + savedInstanceState);
        if (savedInstanceState == null) {
            host.addClient(R.id.fragment_container_quad_screen_A, new Cube());
            host.addClient(R.id.fragment_container_quad_screen_B, new Cube());
            host.addClient(R.id.fragment_container_quad_screen_C, new Cube());
            host.addClient(R.id.fragment_container_quad_screen_D, new Cube());
            host.buildClients();
        }
    }
}
