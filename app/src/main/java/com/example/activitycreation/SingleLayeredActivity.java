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

public class SingleLayeredActivity extends Fragment {

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
        root.addView(getLayoutInflater().inflate(R.layout.fragment_container, null, false), 0);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.add(R.id.fragment_container, new LayeredActivity(), "dla1");
            fragmentTransaction1.commitNow();
            ((LayeredActivity)fragmentManager1.findFragmentByTag("dla1")).onCreate_(null, "activity layer 0", "layered activity 1", "dla2");
        }
    }
}