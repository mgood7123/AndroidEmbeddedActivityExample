package com.example.activitycreation;

import android.os.Bundle;
import android.widget.TextView;

import embeddedActivity.EmbeddedActivityClient;
import embeddedActivity.LogUtils;

public class MainActivity2 extends EmbeddedActivityClient {

    final LogUtils log =
            new LogUtils(
                    "MainActivity2",
                    "a bug has occurred, this should not happen"
            );

    @Override
    public void onCreate(
            final Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);
        log.logMethodName();
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.TEXTVIEW)).setText("embedded activity host demo");
    }
}
