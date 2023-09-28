package com.upvictoria.pm2023.iti_271086.p1u1.cubotexturizado;

/*
Demo original:
https://github.com/huntto/android-opengles-2.0

Actualización de Librerias, reestructurarion de proyecto y modificaciones menores:
Dr. Marco Aurelio Nuño Maganda (Noviembre 2021)

 */

import android.opengl.GLSurfaceView;

import androidx.annotation.NonNull;


public class MainActivity extends BaseActivity {

    @NonNull
    @Override
    protected GLSurfaceView.Renderer newRenderer() {
        ShapeRenderer shapeRenderer = new ShapeRenderer(new ShapeRenderer.InitGLCallback() {
            @Override
            public void onInitGL() {
                TexturedCube.init(MainActivity.this);
            }
        });

        shapeRenderer.addShape(new TexturedCube());
        return shapeRenderer;
    }
}
