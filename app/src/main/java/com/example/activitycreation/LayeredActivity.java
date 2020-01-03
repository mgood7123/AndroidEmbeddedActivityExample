package com.example.activitycreation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LayeredActivity extends Fragment {

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

    public void onCreate_(Bundle savedInstanceState, CharSequence text, CharSequence text2, String tag) {
        super.onCreate(savedInstanceState);
        root.addView(getLayoutInflater().inflate(R.layout.sample_activity, null, false), 0);
        if (savedInstanceState == null) {
            ((TextView)root.findViewById(R.id.activity_text)).setText(text);
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.add(R.id.activity, new LayeredActivity1(), tag);
            fragmentTransaction1.commitNow();
            ((LayeredActivity1)fragmentManager1.findFragmentByTag(tag)).onCreate_(null, text2);
        }
    }
}