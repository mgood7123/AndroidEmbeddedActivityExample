package com.example.activitycreation.utils;

import android.graphics.Bitmap;
import android.media.Image;
import android.media.ImageReader;
import android.util.Log;

import java.nio.ByteBuffer;

public class ImageAvailableListener implements ImageReader.OnImageAvailableListener {
    private final Variables variables;
    String TAG;
    long IMAGES_PRODUCED;
    int mWidth;
    int mHeight;

    boolean shouldContinueCapturing = true;

    public ImageAvailableListener(Variables variables, int mWidth, int mHeight) {
        this.variables = variables;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
    }

    @Override
    public void onImageAvailable(ImageReader reader) {
        if (shouldContinueCapturing) {
            Image image = null;
            Bitmap bitmap = null;

            try {
                image = reader.acquireLatestImage();
                if (image != null) {
                    Image.Plane[] planes = image.getPlanes();
                    ByteBuffer buffer = planes[0].getBuffer();
                    int pixelStride = planes[0].getPixelStride();
                    int rowStride = planes[0].getRowStride();
                    int rowPadding = rowStride - pixelStride * mWidth;

                    // create bitmap
                    bitmap = Bitmap.createBitmap(mWidth + rowPadding / pixelStride, mHeight, Bitmap.Config.ARGB_8888);
                    bitmap.copyPixelsFromBuffer(buffer);

                    // render bitmap
                    variables.bitmap = bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
                    variables.bitmapObtained = true;
                    IMAGES_PRODUCED++;
                    Log.e(TAG, "captured image: " + IMAGES_PRODUCED);
                    if (variables.screenshot) {
                        // min is 5
                        int min;
                        variables.log.errorNoStackTrace(
                                "variables.grantedPermission = " + variables.grantedPermission
                        );
                        variables.log.errorNoStackTrace(
                                "variables.serviceIsRunning = " + variables.serviceIsRunning
                        );
                        variables.log.errorNoStackTrace(
                                "variables.serviceWasNotPreviouslyRunning = " +
                                        variables.serviceWasNotPreviouslyRunning
                        );
                        variables.log.errorNoStackTrace(
                                "variables.serviceWasDisabled = " + variables.serviceWasDisabled
                        );
                        if (
                                variables.grantedPermission &&
                                        variables.serviceIsRunning &&
                                        !variables.serviceWasNotPreviouslyRunning
                        ) {
                            // if permission has already been granted,
                            // and the service is running, and was previously running

                            // in this case, the user has just already enabled the service
                            // from the accessibility activity at a previous point in time
                            // and has already granted permission at a previous point in time

                            // set min to 1
                            // as the Media Projection popup will not appear
                            min = 1;
                        } else {
                            if (variables.serviceWasDisabled) {
                                // if the permission has not been granted,
                                // and the service is running, and was disabled

                                // in this case, the user has just returned from the
                                // accessibility activity

                                // set the min to 25
                                // to allow for both the accessibility activity and
                                // Media Projection popup to disappear

                                // then add 15 to min to be safe against
                                // slow cpu's/power saving mode

                                // 25 + 15 = 40

                                min = 40;
                            } else {
                                // if the permission has not been granted,
                                // and the service is running, and was enabled

                                // in this case, the user has just already enabled the service
                                // from the accessibility activity at a previous point in time
                                // and has not yet granted permission

                                // set the min to 5
                                // to allow for the Media Projection popup to disappear

                                // then triple the min to be safe against
                                // slow cpu's/power saving mode

                                // 5 * 3 = 15

                                min = 15;
                            }
                        }
                        variables.log.errorNoStackTrace("min = " + min);
                        if (IMAGES_PRODUCED == min) {
                            if (variables.imageView != null) {
                                variables.activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        variables.imageView.setImageBitmap(variables.bitmap);
                                    }
                                });
                            }
                            new Thread() {
                                @Override
                                public void run() {
                                    Log.e("TAG", "took screenshot");
                                    variables.mediaProjectionHelper.stopScreenMirror();
                                    variables.screenshot = false;
                                    shouldContinueCapturing = true;
                                }
                            }.start();
                            shouldContinueCapturing = false;
                        }
                    } else {
                        if (variables.imageView != null) {
                            variables.activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    variables.imageView.setImageBitmap(variables.bitmap);
                                }
                            });
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bitmap != null) {
                    bitmap.recycle();
                }

                if (image != null) {
                    image.close();
                }
            }
        }
    }
}
