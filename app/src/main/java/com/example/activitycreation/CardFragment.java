package com.example.activitycreation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment {
    static String TAG = "CardFragment";
    private static final String ARG_COUNT = "param1";
    private Integer counter;
    private int[] COLOR_MAP = {
            R.color.red_100, R.color.red_300, R.color.red_500, R.color.red_700, R.color.blue_100,
            R.color.blue_300, R.color.blue_500, R.color.blue_700, R.color.green_100, R.color.green_300,
            R.color.green_500, R.color.green_700
    };
    public CardFragment() {
        // Required empty public constructor
        Log.e(TAG, "public CardFragment()");
    }
    public static CardFragment newInstance(Integer counter) {
        Log.e(TAG, "public static CardFragment newInstance(Integer counter)");
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COUNT, counter);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "@Override public void onCreate(Bundle savedInstanceState)");
        if (getArguments() != null) {
            counter = getArguments().getInt(ARG_COUNT);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e(TAG, "@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");
        return inflater.inflate(R.layout.fragment_card, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "@Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)");
        view.setBackgroundColor(ContextCompat.getColor(getContext(), COLOR_MAP[counter]));
        TextView textViewCounter = view.findViewById(R.id.tv_counter);
        textViewCounter.setText("Fragment No " + counter);
    }

    @Override
    protected void finalize() throws Throwable {
        Log.e(TAG, "@Override protected void finalize() throws Throwable");
        super.finalize();
    }
}