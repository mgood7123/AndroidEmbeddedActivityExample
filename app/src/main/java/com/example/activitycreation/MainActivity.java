package com.example.activitycreation;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//public class MainActivity extends FragmentActivity {
//
//    EmbeddedActivityHost host = new EmbeddedActivityHost(this);
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        host.log.logMethodName();
//        setContentView(R.layout.fragment_container);
//        host.log.log("savedInstanceState is " + savedInstanceState);
//        if (savedInstanceState == null) {
//            host.addAndBuildClient(R.id.fragment_container, new demo());
//        }
//    }
//}

/*
    res/layouts/fragment_container.xml

    <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/fragment_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </FrameLayout>
 */

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, new DualLayeredActivity());
            fragmentTransaction.commitNow();
            ((DualLayeredActivity)fragmentManager.findFragmentById(R.id.fragment_container)).onCreate_(null);
        }
    }
}
