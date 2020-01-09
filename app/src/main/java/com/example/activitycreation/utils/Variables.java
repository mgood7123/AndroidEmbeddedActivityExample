package com.example.activitycreation.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.view.Display;
import android.widget.ImageView;

import embeddedActivity.LogUtils;

public class Variables {

    public boolean ACCESSIBILITY_RETURNED;
    public final String TAG = getClass().getName();

    public ImageView imageView;

    public final int REQUEST_CODE_CAPTURE = 100;
    public final int REQUEST_CODE_ACCESSIBILITY = 101;

    public final String SCREENCAP_NAME = "screencap";

    public final int VIRTUAL_DISPLAY_FLAGS =
            DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY |
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;

    public MediaProjection sMediaProjection;

    public MediaProjectionManager mProjectionManager;

    public MediaProjectionHelper mediaProjectionHelper = new MediaProjectionHelper(this);
    public Looper looperHelper = new Looper(this);

    public ImageReader mImageReader;
    public Handler mHandler;
    public Display mDisplay;
    public VirtualDisplay mVirtualDisplay;

    public int mDensity;

    public OrientationChangeCallback mOrientationChangeCallback;

    public Activity activity;

    public LogUtils log = new LogUtils(
            TAG, "a bug has occurred, this should not happen"
    );

    public Thread looper;
    public boolean screenshot;
    public boolean grantedPermission;
    public int resultCodeSaved;
    public Intent dataSaved;
    public Bitmap bitmap;
    public boolean bitmapObtained;
    public boolean serviceIsRunning;
    public boolean serviceWasNotPreviouslyRunning;
    public boolean serviceWasDisabled = true;
}