package com.upvictoria.pm2023.iti_271086.p1u1.texturamultiplescubos;

/*
Demo original:
https://github.com/huntto/android-opengles-2.0

Actualización de Librerias, reestructurarion de proyecto y modificaciones menores:
Dr. Marco Aurelio Nuño Maganda (Noviembre 2021)

 */

import android.annotation.SuppressLint;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

//import me.huntto.gl.common.BaseActivity;
//import me.huntto.gl.multitexturedcube.shape.MultiTexturedCube;

public class MainActivity extends BaseActivity {
    private MultiTexturedCube mCube;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView.setOnTouchListener(mTouchListener);
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @NonNull
    @Override
    protected GLSurfaceView.Renderer newRenderer() {
        ShapeRenderer shapeRenderer = new ShapeRenderer(new ShapeRenderer.InitGLCallback() {
            @Override
            public void onInitGL() {
                MultiTexturedCube.init(MainActivity.this);
            }
        });

        mCube = new MultiTexturedCube();
        shapeRenderer.addShape(mCube);
        return shapeRenderer;
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        private float mPrevX;
        private float mPrevY;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int width = mGLSurfaceView.getWidth();
            int height = mGLSurfaceView.getHeight();
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    mPrevX = event.getX() / width * 2 - 1;
                    mPrevY = -event.getY() / height * 2 + 1;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = event.getX() / width * 2 - 1;
                    float y = -event.getY() / height * 2 + 1;
                    float vx = x - mPrevX;
                    float vy = y - mPrevY;

                    float dist = (float) Math.sqrt(vx * vx + vy * vy);
                    mCube.rotate((float) Math.toDegrees(dist), vy, vx, 0);

                    mPrevX = x;
                    mPrevY = y;

                    mGLSurfaceView.requestRender();
                    break;
            }
            return true;
        }
    };
}
