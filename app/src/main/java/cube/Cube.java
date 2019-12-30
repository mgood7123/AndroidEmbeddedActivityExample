package cube;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import embeddedActivity.EmbeddedActivityClient;

public class Cube extends EmbeddedActivityClient {
    NativeView n = null;
    Context context = null;

    public class MyListener implements View.OnClickListener {
        @Override
        public void onClick (View v) {
            Toast toast = Toast.makeText(context,
                    "This demo combines Java UI and native EGL + OpenGL renderer",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (context == null) context = getContext();
        if (n == null) n = new NativeView(context);
        n.nativeOnCreate(n.instance);
        // build layout
        RelativeLayout rel = new RelativeLayout(context);
        setContentView(rel);
        rel.addView(n.surfaceView);
        // set text
        TextView text = new TextView(context);
        text.setText("Hello World! Try clicking the screen");
        text.setTextSize(60f);
        text.setTextColor(Color.WHITE);
        rel.addView(text);
        Log.i(n.TAG, "onCreate()");
        n.surfaceView.setOnClickListener(new MyListener());
    }

    @Override
    public void onStart() {
        super.onStart();
        n.nativeOnStart(n.instance);
    }

    @Override
    public void onResume() {
        super.onResume();
        n.nativeOnResume(n.instance);
    }

    @Override
    public void onPause() {
        super.onPause();
        n.nativeOnPause(n.instance);
    }

    @Override
    public void onStop() {
        super.onStop();
        n.nativeOnStop(n.instance);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        n.nativeOnDestroy(n.instance);
        n.nativeDeleteInstance(n.instance);
    }
}
