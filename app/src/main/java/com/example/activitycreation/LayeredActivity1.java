package com.example.activitycreation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LayeredActivity1 extends Fragment {

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

    public void onCreate_(Bundle savedInstanceState, CharSequence text) {
        super.onCreate(savedInstanceState);
        root.addView(getLayoutInflater().inflate(R.layout.sample_activity, null, false), 0);
        if (savedInstanceState == null) {
            ((TextView)root.findViewById(R.id.activity_text)).setText(text);
            Log.e("TAG", "getFragmentManager().findFragmentById(R.id.fragment_container) = " + getFragmentManager().findFragmentById(R.id.fragment_container));
//            FragmentManager fragmentManager1 = getFragmentManager();
//            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
//            fragmentTransaction1.add(R.id.activity, new QuadCube());
//            fragmentTransaction1.commitNow();
//            ((QuadCube)fragmentManager1.findFragmentById(R.id.activity)).onCreate_(null);

        }
    }
}