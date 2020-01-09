package com.example.activitycreation.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

public class ScreenUtils {

    public Variables variables = new Variables();

    public void onCreate(Activity activity, ImageView imageView) {
        variables.activity = activity;
        variables.imageView = imageView;
        if (variables.mProjectionManager == null) variables.mProjectionManager =
                variables.mediaProjectionHelper.getMediaProjectionManager();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == variables.REQUEST_CODE_CAPTURE) {
            variables.mediaProjectionHelper.startCapture(resultCode, data);
        } else if (requestCode == variables.REQUEST_CODE_ACCESSIBILITY) {
            variables.log.errorNoStackTrace(
                    "result code from REQUEST_CODE_ACCESSIBILITY " + resultCode
            );
            variables.ACCESSIBILITY_RETURNED = true;
        }
    }

    public void takeScreenShot(Activity activity) {
        onCreate(activity, null);
        takeScreenShot();
    }

    public void takeScreenShot(Activity activity, ImageView imageView) {
        onCreate(activity, imageView);
        takeScreenShot();
    }

    public void takeScreenShot() {
        variables.mediaProjectionHelper.takeScreenShot();
    }

    public void startScreenMirror() {
        variables.mediaProjectionHelper.startScreenMirror();
    }

    public void stopScreenMirror() {
        variables.mediaProjectionHelper.stopScreenMirror();
    }

    public void waitForBitmap() {
        while (!variables.bitmapObtained) {
        };
    }
}
