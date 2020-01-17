package SimpleColor;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import embeddedActivity.EmbeddedActivityClient;

public class SimpleColor extends EmbeddedActivityClient {
    NativeView n = null;
    Context context = null;

    public void setVisibility(int visibility) {
        if (n != null) {
            n.surfaceView.setVisibility(visibility);
        }
    }

    public int getVisibility() {
        if (n != null) {
            return n.surfaceView.getVisibility();
        }
        return View.GONE;
    }

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
        log.logMethodName();
        if (context == null) context = getContext();
        if (n == null) n = new NativeView(context);
        n.nativeOnCreate(n.instance);
        // build layout
        RelativeLayout rel = new RelativeLayout(context);
        setContentView(rel);
        rel.addView(n.surfaceView);
        Log.i(n.TAG, "onCreate()");
        n.surfaceView.setOnClickListener(new MyListener());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log.logMethodName();
        n.nativeOnDestroy(n.instance);
        n.nativeDeleteInstance(n.instance);
    }
}
