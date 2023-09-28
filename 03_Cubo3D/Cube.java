package com.upvictoria.pm2023.iti_271086.p1u1.cubo3d;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Cube {

    // Experimento 1: CUBO
    // Cube vertices
    private static final float VERTICES[] = {
            // Cara posterior
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            // Cara frontal
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f,  1.0f, 1.0f,
            -1.0f,  1.0f, 1.0f,

            // Cara Izquierdo
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f,  1.0f,
            -1.0f, 1.0f,  1.0f,
            -1.0f, 1.0f, -1.0f,

            // Cara Derecho
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f,  1.0f,
            1.0f, 1.0f,  1.0f,
            1.0f, 1.0f, -1.0f,
            // Cara Inferior
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f,  1.0f,
            1.0f,  -1.0f,  1.0f,
            1.0f,  -1.0f, -1.0f,
            // Cara Superior
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f,  1.0f,
            1.0f,  1.0f,  1.0f,
            1.0f,  1.0f, -1.0f,

            //-1.0f, -1.0f, 1.0f,
            //1.0f, -1.0f, 1.0f,
            //1.0f, 1.0f, 1.0f,
            //-1.0f, 1.0f, 1.0f
    };

    // Vertex colors.
    private static final float COLORS[] = {
            // RGBA
            // Colro de Cara Posterior
            0.5f, 0.5f, 0.5f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f,

            // Colro de Cara Frontal
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,

            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

    };

    // Order to draw vertices as triangles.
    private static final byte INDICES[] = {
            0, 1,  3,  3, 1,  2,   // Front face.
            4, 5,  7,  7, 5,  6,   // Back face.
            8, 9, 11, 11, 9, 10, // Left face.
            12, 13, 15, 15, 13, 14, // Right face.
            16, 17, 19, 19, 17, 18, // Bottom face.
            20, 21, 23, 23, 21, 22, // Top face.
    };
    // En el comentario "draw cube", descomentar las dos siguientes lineas
    // GLES20.glDrawElements(GLES20.GL_POINTS, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);
    // GLES20.glDrawElements(GLES20.GL_TRIANGLES, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);

/*
    // Experimento 2: CUBO ALAMBRICO
    private static final float VERTICES[] = {
            // Cara posterior
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            // Cara frontal
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f,  1.0f, 1.0f,
            -1.0f,  1.0f, 1.0f,

            // Cara Izquierdo
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f,  1.0f,
            -1.0f, 1.0f,  1.0f,
            -1.0f, 1.0f, -1.0f,

            // Cara Derecho
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f,  1.0f,
            1.0f, 1.0f,  1.0f,
            1.0f, 1.0f, -1.0f,
            // Cara Inferior
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f,  1.0f,
            1.0f,  -1.0f,  1.0f,
            1.0f,  -1.0f, -1.0f,
            // Cara Superior
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f,  1.0f,
            1.0f,  1.0f,  1.0f,
            1.0f,  1.0f, -1.0f,

            //-1.0f, -1.0f, 1.0f,
            //1.0f, -1.0f, 1.0f,
            //1.0f, 1.0f, 1.0f,
            //-1.0f, 1.0f, 1.0f
    };
    // Vertex colors.
    private static final float COLORS[] = {
            // RGBA
            // Colro de Cara Posterior
            0.5f, 0.5f, 0.5f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f,

            // Colro de Cara Frontal
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,

            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

    };

    // Para cada linea es necesario un par de puntos
    private static final byte INDICES[] = {
            0, 1,  1,  2, 2,  3, 3,  0,   // Front face.
            4, 5,  5, 6,  6, 7, 7,  4,   // Back face.
            8, 9, 9, 10, 10, 11, 11, 8, // Left face.
            12, 13, 13, 14, 14, 15, 15, 12 // Right face.
            // Pirque no son necesarias las lineas de Bottom Face y TopFace?
    };
    // GLES20.glDrawElements(GLES20.GL_POINTS, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);
    // GLES20.glDrawElements(GLES20.GL_LINES, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);
*/
/*
    // EXPERIMENTO XXX
    // Hexagono
    private static final float VERTICES[] = {
            // Origen
            0.0f, 0.0f, 0.0f,
            // Primer Vertice
            1.0f, 0.0f,0.0f,
            // Segundo
            0.5f, 0.86f,0.0f,
            // Tercer
            -0.5f,0.86f,0.0f,
            // Cuarto Vertice
            -1.0f,0.0f,0.0f,
            // Quinto Vertice
            -0.5f,-0.86f,0.0f,
            // Sexto Vertice
            0.5f,-0.86f,0.0f,
            // Septimo Vertice
            1.0f,0.0f,0.0f

    };
    // Vertex colors. (Hexagono)
    private static final float COLORS[] = {
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
    };
    // Indices para el HEXAGONO
    private static final byte INDICES[] = {
            0,1,2,3,4,5,6,7
    };
    // En el comentario "draw cube", descomentar las dos siguientes lineas
    //GLES20.glDrawElements(GLES20.GL_POINTS, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);
    //GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);
    */

    // Number of coordinates per vertex in {@link VERTICES}.
    private static final int COORDS_PER_VERTEX = 3;

    // Number of values per colors in {@link COLORS}.
    private static final int VALUES_PER_COLOR = 4;

    // Vertex size in bytes.
    private final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4;

    // Color size in bytes.
    private final int COLOR_STRIDE = VALUES_PER_COLOR * 4;

    /** Shader code for the vertex. */
    private static final String VERTEX_SHADER_CODE =
                    "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec4 vColor;" +
                    "varying vec4 _vColor;" +
                    "void main() {" +
                    "  _vColor = vColor;" +
                    "  gl_PointSize = 30.0; "+
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    /** Shader code for the fragment. */
    private static final String FRAGMENT_SHADER_CODE =
                    "precision mediump float;" +
                    "varying vec4 _vColor;" +
                    "void main() {" +
                    "  gl_FragColor = _vColor;" +
                    "}";


    private final FloatBuffer mVertexBuffer;
    private final FloatBuffer mColorBuffer;
    private final ByteBuffer mIndexBuffer;
    private final int mProgram;
    private final int mPositionHandle;
    private final int mColorHandle;
    private final int mMVPMatrixHandle;

    public Cube() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(VERTICES.length * 4);

        byteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = byteBuffer.asFloatBuffer();
        mVertexBuffer.put(VERTICES);
        mVertexBuffer.position(0);

        byteBuffer = ByteBuffer.allocateDirect(COLORS.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        mColorBuffer = byteBuffer.asFloatBuffer();
        mColorBuffer.put(COLORS);
        mColorBuffer.position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(INDICES.length);
        mIndexBuffer.put(INDICES);
        mIndexBuffer.position(0);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER_CODE));
        GLES20.glAttachShader(mProgram, loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE));
        GLES20.glLinkProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "vColor");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix The Model View Project matrix in which to draw this shape
     */
    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment.
        GLES20.glUseProgram(mProgram);

        // Prepare the cube coordinate data.
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(
                mPositionHandle, 3, GLES20.GL_FLOAT, false, VERTEX_STRIDE, mVertexBuffer);

        // Prepare the cube color data.
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(
                mColorHandle, 4, GLES20.GL_FLOAT, false, COLOR_STRIDE, mColorBuffer);

        // Apply the projection and view transformation.
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the cube.
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);
        GLES20.glDrawElements(GLES20.GL_POINTS, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);

        //GLES20.glDrawElements(GLES20.GL_LINES, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);
        //GLES20.glDrawElements(GLES20.GL_POINTS, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);

        //GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);
        //GLES20.glDrawElements(GLES20.GL_POINTS, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);

        // Disable vertex arrays.
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
    }

    /** Loads the provided shader in the program. */
    private static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }


}
