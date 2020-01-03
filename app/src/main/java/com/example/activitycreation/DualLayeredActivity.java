package com.example.activitycreation;

import android.os.Bundle;

import embeddedActivity.EmbeddedActivityClient;
import embeddedActivity.EmbeddedActivityHost;

public class DualLayeredActivity extends EmbeddedActivityClient {
    EmbeddedActivityClient A;
    EmbeddedActivityClient B;

    public DualLayeredActivity() {}

    DualLayeredActivity(EmbeddedActivityClient A, EmbeddedActivityClient B) {
        this.A = A;
        this.B = B;
    }

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container_split_screen);
        if (savedInstanceState == null) {
            EmbeddedActivityClient AA = A;
            if (A == null)
                AA = new LayeredActivity("activity layer 0-A", "layered activity 1-A");
            EmbeddedActivityClient BB = B;
            if (B == null)
                BB = new LayeredActivity("activity layer 0-B", "layered activity 1-B");
            host.addClient(R.id.fragment_container_split_screen_A, AA);
            host.addClient(R.id.fragment_container_split_screen_B, BB);
            host.buildClients();
        }
    }
}