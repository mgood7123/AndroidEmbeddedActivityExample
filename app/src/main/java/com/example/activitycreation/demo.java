package com.example.activitycreation;

import android.os.Bundle;

import embeddedActivity.EmbeddedActivityClient;
import embeddedActivity.EmbeddedActivityHost;

public class demo extends EmbeddedActivityClient {

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.logMethodName();
        setContentView(R.layout.fragment_container_quad_screen);
        log.log("savedInstanceState is " + savedInstanceState);
        if (savedInstanceState == null) {
            host.addClient(R.id.fragment_container_quad_screen_A, new QuadCube());
            host.addClient(R.id.fragment_container_quad_screen_B, new QuadCube());
            host.addClient(R.id.fragment_container_quad_screen_C, new QuadCube());
            host.addClient(R.id.fragment_container_quad_screen_D, new QuadCube());
            host.buildClients();
        }
    }
}
