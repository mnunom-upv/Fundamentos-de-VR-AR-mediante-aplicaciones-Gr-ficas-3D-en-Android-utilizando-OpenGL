package com.example.lesson07;

//import android.support.v7.app.AppCompatActivity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/*
Instrucciones adicionales:

1) Crear en la carpera de res una carpeta llamada raw y copiar todos los archivos con extension glsl
2) Copiar las imagenes .png y .jpg en la carmera res/drawable

 */


public class MainActivity extends AppCompatActivity {

    //private LessonSevenGLSurfaceView mGLSurfaceView;
    private LessonSevenRenderer mRenderer;
    private GLSurfaceView mGLView;

    private float mPreviousX;
    private float mPreviousY;
    private float mDensity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);

        mGLView = findViewById(R.id.gl_surface_view);

        // Check if the system supports OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2) {
            // Request an OpenGL ES 2.0 compatible context.
            //mGLSurfaceView.setEGLContextClientVersion(2);
            mGLView.setEGLContextClientVersion(2);

            final DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            // Set the renderer to our demo renderer, defined below.
            mRenderer = new LessonSevenRenderer(this, mGLView);
            mGLView.setRenderer(mRenderer);
            mGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
            //mGLSurfaceView.setRenderer(mRenderer, displayMetrics.density);
            mDensity=displayMetrics.density;
        } else {
            // This is where you could create an OpenGL ES 1.x compatible
            // renderer if you wanted to support both ES 1 and ES 2.
            return;
        }

        mGLView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent != null)
                {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();

                    if (motionEvent.getAction() == MotionEvent.ACTION_MOVE)
                    {
                        if (mRenderer != null)
                        {
                            float deltaX = (x - mPreviousX) / mDensity / 2f;
                            float deltaY = (y - mPreviousY) / mDensity / 2f;

                            mRenderer.mDeltaX += deltaX;
                            mRenderer.mDeltaY += deltaY;
                        }
                    }

                    mPreviousX = x;
                    mPreviousY = y;

                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        findViewById(R.id.button_decrease_num_cubes).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseCubeCount();
            }
        });

        findViewById(R.id.button_increase_num_cubes).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCubeCount();
            }
        });

        findViewById(R.id.button_switch_VBOs).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVBOs();
            }
        });

        findViewById(R.id.button_switch_stride).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleStride();
            }
        });
    }

    @Override
    protected void onResume() {
        // The activity must call the GL surface view's onResume() on activity
        // onResume().
        super.onResume();
        //mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        // The activity must call the GL surface view's onPause() on activity
        // onPause().
        super.onPause();
        //mGLSurfaceView.onPause();
    }

    private void decreaseCubeCount() {
        mGLView.queueEvent(new Runnable() {
            @Override
            public void run() {
                mRenderer.decreaseCubeCount();
            }
        });
    }

    private void increaseCubeCount() {
        mGLView.queueEvent(new Runnable() {
            @Override
            public void run() {
                mRenderer.increaseCubeCount();
            }
        });
    }

    private void toggleVBOs() {
        mGLView.queueEvent(new Runnable() {
            @Override
            public void run() {
                mRenderer.toggleVBOs();
            }
        });
    }

    protected void toggleStride() {
        mGLView.queueEvent(new Runnable() {
            @Override
            public void run() {
                mRenderer.toggleStride();
            }
        });
    }

    public void updateVboStatus(final boolean usingVbos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (usingVbos) {
                    ((Button) findViewById(R.id.button_switch_VBOs)).setText("VBOs");
                } else {
                    ((Button) findViewById(R.id.button_switch_VBOs)).setText("No VBOs");
                }
            }
        });
    }

    public void updateStrideStatus(final boolean useStride) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (useStride) {
                    ((Button) findViewById(R.id.button_switch_stride)).setText("Stride");
                } else {
                    ((Button) findViewById(R.id.button_switch_stride)).setText("No Stride");
                }
            }
        });
    }
}
