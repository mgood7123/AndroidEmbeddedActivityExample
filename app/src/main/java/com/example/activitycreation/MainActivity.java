package com.example.activitycreation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import SimpleColor.SimpleColor;
import embeddedActivity.EmbeddedActivityHost;

public class MainActivity extends FragmentActivity {

    EmbeddedActivityHost host = new EmbeddedActivityHost(this);
    String keyA = "a";
//    String keyB = "b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        host.log.logMethodName();
        host.restoreBundle(savedInstanceState);
        setContentView(R.layout.view_switcher);
        View A = host.generateContainer();
        host.bindId(A, keyA);
//        View B = host.generateContainer();
//        host.bindId(B, keyB);

        FrameLayout frameLayout = findViewById(R.id.viewFlipper);
        frameLayout.addView(A);
//        frameLayout.addView(B);

        Button show_view_A = findViewById(R.id.show_view_A);
//        Button show_view_B = findViewById(R.id.show_view_B);

        show_view_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleColor a = ((SimpleColor)host.findClientByKey(keyA));
                int targetVisibility;
                int currentVisibility = a.getVisibility();
                if (currentVisibility == View.VISIBLE)
                    a.setVisibility(View.INVISIBLE);
                if (currentVisibility == View.INVISIBLE)
                    a.setVisibility(View.VISIBLE);
//                ((DualCube)host.findClientByKey(keyB)).setVisibility(View.INVISIBLE);
            }
        });

//        show_view_B.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((SimpleColor)host.findClientByKey(keyA)).setVisibility(View.INVISIBLE);
//                ((DualCube)host.findClientByKey(keyB)).setVisibility(View.VISIBLE);
//            }
//        });

        if (savedInstanceState == null) {
            host.addAndBuildClient(keyA, new SimpleColor());
//            host.addAndBuildClient(keyB, new DualCube());
            ((SimpleColor)host.findClientByKey(keyA)).setVisibility(View.INVISIBLE);
//            ((DualCube)host.findClientByKey(keyB)).setVisibility(View.INVISIBLE);
        } else {
            ((SimpleColor)host.findClientByKey(keyA)).setVisibility(savedInstanceState.getInt(keyA));
//            ((DualCube)host.findClientByKey(keyA)).setVisibility(savedInstanceState.getInt(keyB));
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View rv = this.getWindow().getDecorView();
//        final FrameLayout frameLayout = (FrameLayout) this.getWindow().getDecorView()
//                .findViewById(android.R.id.content);
//        final LinearLayout ll = new LinearLayout(this);
//
//        SurfaceView mSurfaceView = new SurfaceView(this);
//        SurfaceView mSurfaceView1 = new SurfaceView(this);
//        SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            }
//
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                Canvas c = null;
//                try {
//                    c = holder.lockCanvas();
//                    c.drawColor(Color.GREEN);
//                    Paint p = new Paint();
//                    p.setColor(Color.WHITE);
//                    Rect r = new Rect(100, 50, 300, 250);
//                    c.drawRect(r, p);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (c != null) {
//                        holder.unlockCanvasAndPost(c);
//                    }
//                }
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//            }
//        };
//        mSurfaceView.getHolder().addCallback(mSurfaceCallback);
//        mSurfaceView1.getHolder().addCallback(mSurfaceCallback);
//
//        Button b = new Button(this);b.setText("view1");
//        Button c = new Button(this);c.setText("view2");
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                try{
////                    ll.addView(mSurfaceView);
////                    ll.removeView(mSurfaceView1);
//                    mSurfaceView.setVisibility(View.VISIBLE);
//                    mSurfaceView1.setVisibility(View.INVISIBLE);
//                }catch(Exception e) {}
//            }
//        });
//
//        c.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                try{
////                    ll.addView(mSurfaceView1);
////                    ll.removeView(mSurfaceView);
//                    mSurfaceView1.setVisibility(View.VISIBLE);
//                    mSurfaceView.setVisibility(View.INVISIBLE);
//                }catch(Exception e) {}
//            }
//        });
//
//        ll.setOrientation(LinearLayout.VERTICAL);
//
//        ll.addView(b, new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        ll.addView(c, new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//
//        // add mSurfaceView & mSurfaceView1
//        ll.addView(mSurfaceView);
//        ll.addView(mSurfaceView1);
//        mSurfaceView.setVisibility(View.INVISIBLE);
//        mSurfaceView1.setVisibility(View.INVISIBLE);
//
//        frameLayout.addView(ll);
//    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        host.saveBundle(outState);
        outState.putInt(keyA, ((SimpleColor)host.findClientByKey(keyA)).getVisibility());
//        outState.putInt(keyB, ((DualCube)host.findClientByKey(keyB)).getVisibility());
    }
}
