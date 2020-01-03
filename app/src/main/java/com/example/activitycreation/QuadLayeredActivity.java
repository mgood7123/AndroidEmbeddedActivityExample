package com.example.activitycreation;

import android.os.Bundle;

import androidx.annotation.NonNull;

import embeddedActivity.EmbeddedActivityClient;
import embeddedActivity.EmbeddedActivityHost;

public class QuadLayeredActivity extends EmbeddedActivityClient {

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        host.restoreBundle(savedInstanceState);
        setContentView(R.layout.fragment_container_quad_screen);
        host.bindId(R.id.fragment_container_quad_screen_A);
        host.bindId(R.id.fragment_container_quad_screen_B);
        host.bindId(R.id.fragment_container_quad_screen_C);
        host.bindId(R.id.fragment_container_quad_screen_D);
        if (savedInstanceState == null) {
            EmbeddedActivityClient A = (EmbeddedActivityClient) initializationExtras.get("A");
            if (A == null) {
                A = new LayeredActivity();
                A.initializationExtras.put("text", "activity layer 0-A");
                A.initializationExtras.put("text2", "layered activity 1-A");
            }
            EmbeddedActivityClient B = (EmbeddedActivityClient) initializationExtras.get("B");;
            if (B == null) {
                B = new LayeredActivity();
                B.initializationExtras.put("text", "activity layer 0-B");
                B.initializationExtras.put("text2", "layered activity 1-B");
            }
            EmbeddedActivityClient C = (EmbeddedActivityClient) initializationExtras.get("C");
            if (C == null) {
                C = new LayeredActivity();
                C.initializationExtras.put("text", "activity layer 0-C");
                C.initializationExtras.put("text2", "layered activity 1-C");
            }
            EmbeddedActivityClient D = (EmbeddedActivityClient) initializationExtras.get("D");
            if (D == null) {
                D = new LayeredActivity();
                D.initializationExtras.put("text", "activity layer 0-D");
                D.initializationExtras.put("text2", "layered activity 1-D");
            }
            host.addClient(R.id.fragment_container_quad_screen_A, A);
            host.addClient(R.id.fragment_container_quad_screen_B, B);
            host.addClient(R.id.fragment_container_quad_screen_C, C);
            host.addClient(R.id.fragment_container_quad_screen_D, D);
            host.buildClients();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        host.saveBundle(outState);
    }
}