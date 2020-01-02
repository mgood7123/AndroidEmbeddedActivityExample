package com.example.activitycreation;

//public class QuadCube extends EmbeddedActivityClient {
//
//    EmbeddedActivityHost host = new EmbeddedActivityHost(this);
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        log.logMethodName();
//        setContentView(R.layout.fragment_container_quad_screen);
//        log.log("savedInstanceState is " + savedInstanceState);
//        if (savedInstanceState == null) {
//            host.addClient(R.id.fragment_container_quad_screen_A, new Cube());
//            host.addClient(R.id.fragment_container_quad_screen_B, new Cube());
//            host.addClient(R.id.fragment_container_quad_screen_C, new Cube());
//            host.addClient(R.id.fragment_container_quad_screen_D, new Cube());
//            host.buildClients();
//        }
//    }
//}

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cube.Cube;

public class QuadCube extends Fragment {

    FrameLayout root;

    // since onCreate is called after onAttach and BEFORE onCreateView, the root view must exist
    // before onCreate is called since it is the entry point of an Activity

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
        root = new FrameLayout(getContext());
    }

    @Nullable
    @Override
    public final View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState
    ) {
        super.onCreateView(inflater, container, savedInstanceState);
        return root;
    }

    public void onCreate_(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root.addView(getLayoutInflater().inflate(R.layout.fragment_container_quad_screen, null, false), 0);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // the order here does not affect the actual order displayed on screen
            // since Cube does not spawn any Fragment's itself
            fragmentTransaction.add(R.id.A, new Cube());
            fragmentTransaction.add(R.id.B, new Cube());
            fragmentTransaction.add(R.id.C, new Cube());
            fragmentTransaction.add(R.id.D, new Cube());
            fragmentTransaction.commitNow();
        }
    }
}