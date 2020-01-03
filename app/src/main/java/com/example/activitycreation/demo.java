package com.example.activitycreation;

import android.os.Bundle;

import androidx.annotation.NonNull;

import cube.Cube;
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
            QuadLayeredActivity x1 = new QuadLayeredActivity();
            x1.initializationExtras.put("A", new Cube());
            x1.initializationExtras.put("B", new Cube());
            x1.initializationExtras.put("C", new Cube());
            x1.initializationExtras.put("D", new Cube());
            QuadLayeredActivity x2 = new QuadLayeredActivity();
            x2.initializationExtras.put("A", new Cube());
            x2.initializationExtras.put("B", new Cube());
            x2.initializationExtras.put("C", new Cube());
            x2.initializationExtras.put("D", new Cube());
            QuadLayeredActivity x3 = new QuadLayeredActivity();
            x3.initializationExtras.put("A", new Cube());
            x3.initializationExtras.put("B", new Cube());
            x3.initializationExtras.put("C", new Cube());
            x3.initializationExtras.put("D", new Cube());
            QuadLayeredActivity x4 = new QuadLayeredActivity();
            x4.initializationExtras.put("A", new Cube());
            x4.initializationExtras.put("B", new Cube());
            x4.initializationExtras.put("C", new Cube());
            x4.initializationExtras.put("D", new Cube());
            host.addClient(R.id.fragment_container_quad_screen_A, x1);
            host.addClient(R.id.fragment_container_quad_screen_B, x2);
            host.addClient(R.id.fragment_container_quad_screen_C, x3);
            host.addClient(R.id.fragment_container_quad_screen_D, x4);
            host.buildClients();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        host.saveBundle(outState);
    }
}
