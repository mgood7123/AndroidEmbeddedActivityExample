package com.example.activitycreation;

import android.os.Bundle;

import androidx.annotation.NonNull;

import embeddedActivity.EmbeddedActivityClient;
import embeddedActivity.EmbeddedActivityHost;

public class demo extends EmbeddedActivityClient {

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.logMethodName();
        host.restoreBundle(savedInstanceState);
        setContentView(R.layout.fragment_container_quad_screen);
        host.bindId(R.id.fragment_container_quad_screen_A);
        host.bindId(R.id.fragment_container_quad_screen_B);
        host.bindId(R.id.fragment_container_quad_screen_C);
        host.bindId(R.id.fragment_container_quad_screen_D);
        log.log("savedInstanceState is " + savedInstanceState);
        if (savedInstanceState == null) {
            host.addClient(R.id.fragment_container_quad_screen_A, new QuadCube());
            host.addClient(R.id.fragment_container_quad_screen_B, new QuadCube());
            host.addClient(R.id.fragment_container_quad_screen_C, new QuadCube());
            host.addClient(R.id.fragment_container_quad_screen_D, new QuadCube());
            host.buildClients();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        host.saveBundle(outState);
    }
}
