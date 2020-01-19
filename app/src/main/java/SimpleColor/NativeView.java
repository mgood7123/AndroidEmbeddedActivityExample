package SimpleColor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class NativeView {
    String TAG = "EglSample";

    public long instance;
    public final native long nativeNewInstance();
    public final native void nativeOnCreate(long instance);
    public final native void nativeOnDestroy(long instance);
    public final native void nativeDeleteInstance(long instance);
    public final native void nativeSetSurface(long instance, Surface surface);

    SV surfaceSV;
    SurfaceHolderCallback surfaceHolderCallback;

    public final void onCreate(Context context) {
        System.loadLibrary("SimpleColor");
        instance = nativeNewInstance();
        surfaceHolderCallback = new SurfaceHolderCallback(instance);
        surfaceSV = new SV(surfaceHolderCallback, context);
        nativeOnCreate(instance);
    }

    public final void onDestroy() {
        nativeOnDestroy(instance);
        nativeDeleteInstance(instance);
        surfaceHolderCallback.mInstance = 0;
        surfaceHolderCallback = null;
        surfaceSV = null;
        instance = 0;
    }

    class SV extends SurfaceView {
        public SV(SurfaceHolder.Callback callback, Context context) {
            super(context);
            getHolder().addCallback(callback);
        }

        public SV(SurfaceHolder.Callback callback, Context context, AttributeSet attrs) {
            super(context, attrs);
            getHolder().addCallback(callback);
        }

        public SV(SurfaceHolder.Callback callback, Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            getHolder().addCallback(callback);
        }

        public SV(SurfaceHolder.Callback callback, Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
            super(context, attrs, defStyle, defStyleRes);
            getHolder().addCallback(callback);
        }
    }

    class SurfaceHolderCallback implements SurfaceHolder.Callback {
        long mInstance;
        public SurfaceHolderCallback(long instance) {
            mInstance = instance;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            nativeSetSurface(mInstance, holder.getSurface());
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            nativeSetSurface(mInstance, null);
        }
    }
}
