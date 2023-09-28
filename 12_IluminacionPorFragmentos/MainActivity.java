package com.upvictoria.pm2023.iti_271086.p1u1.iluminacionporfragmentos;

import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView;
    private LessonThreeRenderer mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mGLSurfaceView = new GLSurfaceView(this);
        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(new LessonThreeRenderer());
        setContentView(mGLSurfaceView);

        /*setContentView(R.layout.activity_main);
        mGLSurfaceView = findViewById(R.id.OpenGL1_surfaceView);
        mGLSurfaceView.setEGLContextClientVersion(2);
        mRenderer = new LessonThreeRenderer();
        mGLSurfaceView.setRenderer(mRenderer);*/


    }
}
