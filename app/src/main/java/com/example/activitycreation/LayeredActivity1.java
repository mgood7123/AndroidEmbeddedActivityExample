package com.example.activitycreation;

import android.os.Bundle;
import android.widget.TextView;

import embeddedActivity.EmbeddedActivityClient;
import embeddedActivity.EmbeddedActivityHost;

public class LayeredActivity1 extends EmbeddedActivityClient {

    CharSequence text;
    LayeredActivity1(CharSequence text) {
        this.text = text;
    }

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);
        if (savedInstanceState == null) {
            ((TextView)root.findViewById(R.id.activity_text)).setText(text);
//            Log.e("TAG", "getFragmentManager().findFragmentById(R.id.fragment_container) = " + getFragmentManager().findFragmentById(R.id.fragment_container));
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.activity, new QuadCube());
//            fragmentTransaction.commitNow();
//            ((QuadCube)fragmentManager.findFragmentById(R.id.activity)).onCreate_(null);

        }
    }
}