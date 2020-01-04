package cube;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
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
        log.logMethodName();
        if (context == null) context = getContext();
        if (n == null) n = new NativeView(context, this);
        nativeEnableRenderOneFrame(() -> {
            try {
                synchronized (n.surfaceReadyNotification) {
                    log.log("nativeEnableRenderOneFrame n.surfaceReadyNotification.wait() waiting");
                    n.surfaceReadyNotification.wait();
                    log.log("nativeEnableRenderOneFrame n.surfaceReadyNotification.wait() waited");
                    n.nativeEnableRenderOneFrame(n.instance);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        nativeDisableRenderOneFrame(() -> {
            try {
                synchronized (n.surfaceReadyNotification) {
                    log.log("nativeDisableRenderOneFrame n.surfaceReadyNotification.wait() waiting");
                    n.surfaceReadyNotification.wait();
                    log.log("nativeDisableRenderOneFrame n.surfaceReadyNotification.wait() waited");
                    n.nativeDisableRenderOneFrame(n.instance);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        n.nativeOnCreate(n.instance);
        // build layout
        RelativeLayout rel = new RelativeLayout(context);
        setContentView(rel);
        rel.addView(n.surfaceView);
        Log.i(n.TAG, "onCreate()");
        n.surfaceView.setOnClickListener(new MyListener());
    }

    @Override
    public void onStart() {
        super.onStart();
        log.logMethodName();
        n.nativeOnStart(n.instance);
    }

    @Override
    public void onResume() {
        super.onResume();
        log.logMethodName();
        n.nativeOnResume(n.instance);
    }

    @Override
    public void onPause() {
        super.onPause();
        log.logMethodName();
        n.nativeOnPause(n.instance);
    }

    @Override
    public void onStop() {
        super.onStop();
        log.logMethodName();
        n.nativeOnStop(n.instance);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log.logMethodName();
        n.nativeOnDestroy(n.instance);
        n.nativeDeleteInstance(n.instance);
    }
}
