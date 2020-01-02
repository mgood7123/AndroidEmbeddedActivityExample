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

public class DualLayeredActivity extends Fragment {

    FrameLayout root;

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
        root.addView(getLayoutInflater().inflate(R.layout.fragment_container_split_screen, null, false), 0);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.add(R.id.fragment_container_split_screen_A, new LayeredActivity());
            fragmentTransaction1.add(R.id.fragment_container_split_screen_B, new LayeredActivity());
            fragmentTransaction1.commitNow();
            ((LayeredActivity)fragmentManager1.findFragmentById(R.id.fragment_container_split_screen_A)).onCreate_(null, "activity layer 0-A");
            ((LayeredActivity)fragmentManager1.findFragmentById(R.id.fragment_container_split_screen_B)).onCreate_(null, "activity layer 0-B");
        }
    }
}