package com.example.activitycreation;

import android.os.Bundle;

import androidx.annotation.NonNull;

import SimpleColor.SimpleColor;
import embeddedActivity.EmbeddedActivityClient;
import embeddedActivity.EmbeddedActivityHost;

public class DualCube extends EmbeddedActivityClient {

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.logMethodName();
        host.restoreBundle(savedInstanceState);
        setContentView(R.layout.ss);
        host.bindId(R.id.ssa);
        host.bindId(R.id.ssb);
        log.log("savedInstanceState is " + savedInstanceState);
        if (savedInstanceState == null) {
            host.addClient(R.id.ssa, new SimpleColor());
            host.addClient(R.id.ssb, new SimpleColor());
            host.buildClients();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        host.saveBundle(outState);
    }

    public void setVisibility(int visibility) {
        ((SimpleColor)host.findClientById(R.id.ssa)).setVisibility(visibility);
        ((SimpleColor)host.findClientById(R.id.ssb)).setVisibility(visibility);
    }

    public int getVisibility() {
        return ((SimpleColor)host.findClientById(R.id.ssa)).getVisibility();
    }
}