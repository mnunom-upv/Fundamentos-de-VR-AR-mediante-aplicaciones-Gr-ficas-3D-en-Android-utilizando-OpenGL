package com.upvictoria.pm2023.iti_271086.p1u1.cilindro_poligonos;

/*
https://github.com/GabeK0/Android-OpenGL-Sphere
Modificaciones Menores, Noviembre 2021
 */

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private MyGLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);


        mGLSurfaceView = new MyGLSurfaceView(this, size.x, size.y);

        setContentView(mGLSurfaceView);
    }

    @Override
    protected void onResume() {
        mGLSurfaceView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mGLSurfaceView.onPause();
        super.onPause();
    }
}