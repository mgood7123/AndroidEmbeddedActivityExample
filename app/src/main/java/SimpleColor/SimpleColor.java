package SimpleColor;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import embeddedActivity.EmbeddedActivityClient;

public class SimpleColor extends EmbeddedActivityClient {
    NativeView n;

    public void setVisibility(int visibility) {
        if (n != null) {
            n.surfaceSV.setVisibility(visibility);
        }
    }

    public int getVisibility() {
        if (n != null) {
            return n.surfaceSV.getVisibility();
        }
        return View.GONE;
    }

    public class MyListener implements View.OnClickListener {
        @Override
        public void onClick (View v) {
            Toast toast = Toast.makeText(getContext(),
                    "This demo combines Java UI and native EGL + OpenGL renderer",
                    Toast.LENGTH_SHORT);
            toast.show();
            toast = null;
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.logMethodName();
        Context context = getContext();
        n = new NativeView();
        n.onCreate(context);
        // build layout
        RelativeLayout rel = new RelativeLayout(context);
        setContentView(rel);
        rel.addView(n.surfaceSV);
        rel = null;
        Log.i(n.TAG, "onCreate()");
        n.surfaceSV.setOnClickListener(new MyListener());
    }

    @Override
    public void onDestroy() {
        log.logMethodName();
        n.onDestroy();
        n = null;
        super.onDestroy();
    }
}
