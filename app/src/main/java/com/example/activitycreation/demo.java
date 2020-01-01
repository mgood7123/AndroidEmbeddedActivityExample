package com.example.activitycreation;

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

//public class demo extends EmbeddedActivityClient {
//
//    EmbeddedActivityHost host = new EmbeddedActivityHost(this);
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        log.logMethodName();
//        setContentView(R.layout.ss);
//        log.log("savedInstanceState is " + savedInstanceState);
//        if (savedInstanceState == null) {
//            host.addClient(R.id.ssa, new DualCube());
//            host.addClient(R.id.ssb, new DualCube());
//            host.buildClients();
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

    res/layouts/ss.xml

    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <FrameLayout
            android:id="@+id/ssa"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toStartOf="@+id/ssb"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"></FrameLayout>

        <FrameLayout
            android:id="@+id/ssb"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toEndOf="@+id/ssa"
            app:layout_constraintTop_toTopOf="parent"></FrameLayout>
    </androidx.constraintlayout.widget.ConstraintLayout>

    */

public class demo extends Fragment {

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
        root.addView(getLayoutInflater().inflate(R.layout.ss, null, false), 0);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.ssa, new DualCube());
            fragmentTransaction.add(R.id.ssb, new DualCube());
            fragmentTransaction.commitNow();
            ((DualCube)fragmentManager.findFragmentById(R.id.ssa)).onCreate_(null);
            ((DualCube)fragmentManager.findFragmentById(R.id.ssb)).onCreate_(null);
        }
    }
}