package com.upvictoria.pm2023.iti_271086.p1u1.circulotrianglefan;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by GabrielK on 6/21/2016.
 */
public class MyGLRenderer2 implements GLSurfaceView.Renderer {
    //private Triangle triangle;
    private int zoom;
    private int programHandle;
    private float[] mModelMatrix = new float[16];
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    // floats per trapezoid = 7 floats per vertex * 3 vertices per triangle * 2 triangles per trapezoid
    private final int floatsPerTrap = 42;
    /** How many bytes per float. */
    private final int mBytesPerFloat = 4;
    /** How many elements per vertex. */
    private final int mStrideBytes = 7 * mBytesPerFloat;
    /** Offset of the position data. */
    private final int mPositionOffset = 0;
    /** Size of the position data in elements. */
    private final int mPositionDataSize = 3;
    /** Offset of the color data. */
    private final int mColorOffset = 3;
    /** Size of the color data in elements. */
    private final int mColorDataSize = 4;
    /** This will be used to pass in the transformation matrix. */
    private int mMVPMatrixHandle;
    /** This will be used to pass in model position information. */
    private int mPositionHandle;
    /** This will be used to pass in model color information. */
    private int mColorHandle;
    //private FloatBuffer vertexBuffer;

    public MyGLRenderer2(int zoom) {
        this.zoom = zoom;
    }
    public void zoom(int z) { zoom=z; setUp(); }
    public int zoom_in()    { zoom++; setUp(); return zoom; }
    public int zoom_out()   { zoom--; setUp(); return zoom; }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //triangle = new Triangle();
        GLES20.glClearColor(0.0f, 0.0f, 0.5f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glDepthMask(true);

        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 2f, 0, 0, -5, 0, 1, 0);

        programHandle = getProgram();
        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");
        GLES20.glUseProgram(programHandle);
        setUp();
    }

    public void setUp() {
        //Random r = new Random();
        //Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 2f, 0, 0, -5, 0, 1, 0);

        float [] verticesData = new float [floatsPerTrap * (int) Math.pow(4, zoom)];

        for (int i = 0; i < Math.pow(2, zoom); i++)
            System.arraycopy(getVertexData(i, 1), 0, verticesData, 0, floatsPerTrap);
            /*for (int j = 0; j < Math.pow(2, zoom); j++)
                //System.arraycopy(getVertexData(i, j), 0, verticesData, (int) (i * Math.pow(2, zoom) + j) * floatsPerTrap, floatsPerTrap);
                System.arraycopy(getVertexData(i, j), 0, verticesData, (int) (i) * floatsPerTrap, floatsPerTrap);*/


        /*float [] verticesData = {
                (float) (0),(float) (1f), (float) (-0.5f),
                0.8f, 0.1f, 0.2f, 1.0f,

                (float) (0),(float) (1f), (float) (-0.5f),
                0.8f, 0.1f, 0.2f, 1.0f,

                (float) (0),(float) (1f), (float) (-0.5f),
                0.8f, 0.1f, 0.2f, 1.0f,

                (float) (0),(float) (1f), (float) (-0.5f),
                0.8f, 0.1f, 0.2f, 1.0f,

                (float) (0),(float) (1f), (float) (-0.5f),
                0.8f, 0.1f, 0.2f, 1.0f,

                (float) (0),(float) (1f), (float) (-0.5f),
                0.8f, 0.1f, 0.2f, 1.0f,
        };*/
        //for(int i = 0; i < 4;i++) {

        //}
        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(verticesData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertexBuffer.put(verticesData).position(0);

        final int vertexBufferIdx;
        final int buffers[] = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * mBytesPerFloat, vertexBuffer, GLES20.GL_STATIC_DRAW);

        vertexBufferIdx = buffers[0];

        // a lo mejor posicion
            //GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferIdx);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false, mStrideBytes, 0);

        // Aqui se agrega los colores a la figura
            //GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferIdx);
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
                mStrideBytes, mPositionDataSize * mBytesPerFloat);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;
        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        long time = SystemClock.uptimeMillis() % 10000L;

        // Se cambia la esfera
        /*for( int i = 0; i < (6 * (int) Math.pow(4, zoom))-2; i++) {
            GLES20.aniopnaipsc(mColorHandle, r, g, b, 1);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,i,  i+2);
        }*/
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0,  6 * (int) Math.pow(4, zoom));

        //GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0,  6 * (int) Math.pow(4, zoom));
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0,  6 * (int) Math.pow(4, zoom));
        //GLES20.glDrawArrays(GLES20.GL_LINES, 0,  6 * (int) Math.pow(4, zoom));

        /*for (int i = 0; i < traps.size(); i++) {
            drawTriangle(traps.get(i).getTri1());
            drawTriangle(traps.get(i).getTri2());
        }*/
        //triangle.draw(gl);
    }

    public float[] getVertexData(int row, int col) {
        double inc = 5;
        double theta = row * inc - 5;
        //double phi = col * inc - Math.PI*2;
        double phi = col * inc / 2;

        //float color = (float) ((theta + Math.PI) / (2 * Math.PI));
        float color = (float) ((Math.abs(theta/Math.PI))/2 + (Math.abs((phi - Math.PI/2)/(Math.PI/2)))/2);

        double[][] spherePoints = new double[][] {  {theta,       phi},
                                                    {theta + inc, phi},
                                                    {theta + inc, phi + inc / 2},
                                                    {theta,       phi + inc / 2}
                                                 };
 /*
        double[][] spherePoints = new double[][] {  {0,       0.5},
                {0, 0.5},
                {0, 0.5},
                {0,       0.5}
        };*/
        double[][] vertexPoints = new double[4][3];
        for (int i = 0; i < spherePoints.length; i ++)
            //vertexPoints[i] = new double[]{5,8,4};
            vertexPoints[i] = sphereToXYZ(spherePoints[i]);

        // poner en un for
        return new float[] {
                //Tri1
                (float) (vertexPoints[0][0]),(float) (vertexPoints[0][1]), (float) (vertexPoints[0][2]),
                0.8f, 0.1f, 0.2f, 1.0f,

                (float) (vertexPoints[1][0]),(float) (vertexPoints[1][1]), (float) (vertexPoints[1][2]),
                0.8f, 0.2f, 0.2f, 1.0f,

                (float) (vertexPoints[3][0]),(float) (vertexPoints[3][1]), (float) (vertexPoints[3][2]),
                0.8f, 0.4f, 0.2f, 1.0f,

                //tri2
                (float) (vertexPoints[3][0]),(float) (vertexPoints[3][1]), (float) (vertexPoints[3][2]),
                0.8f, 0.5f, 0.2f, 1.0f,

                (float) (vertexPoints[1][0]),(float) (vertexPoints[1][1]), (float) (vertexPoints[1][2]),
                0.8f, 0.6f, 0.2f, 1.0f,

                (float) (vertexPoints[2][0]),(float) (vertexPoints[2][1]), (float) (vertexPoints[2][2]),
                0.8f, 0.7f, 0.2f, 1.0f,};
    }

    private double[] sphereToXYZ(double[] p) {
        if (p.length != 2)
            Log.e("DEBUG", "you dun goofed");
        return new double[] { Math.cos(p[0]) * Math.sin(p[1]), Math.sin(p[0]) * Math.sin(p[1]),
                Math.cos(p[1]) };
    }

    public int getProgram() {
        final String vertexShader =
                "uniform mat4 u_MVPMatrix;      \n"     // A constant representing the combined model/view/projection matrix.
                        + "attribute vec4 a_Position;     \n"     // Per-vertex position information we will pass in.
                        + "attribute vec4 a_Color;        \n"     // Per-vertex color information we will pass in.
                        + "varying vec4 v_Color;          \n"     // This will be passed into the fragment shader.
                        + "void main()                    \n"     // The entry point for our vertex shader.
                        + "{                              \n"
                        + "   v_Color = a_Color;          \n"     // Pass the color through to the fragment shader.
                        // It will be interpolated across the triangle.
                        + "   gl_Position = a_Position;   \n"     // gl_Position is a special variable used to store the final position.
                        //+ "               * a_Position;   \n"     // Multiply the vertex by the matrix to get the final point in
                        + "}                              \n";    // normalized screen coordinates.

        final String fragmentShader =
                "precision mediump float;       \n"     // Set the default precision to medium. We don't need as high of a
                        // precision in the fragment shader.
                        + "varying vec4 v_Color;          \n"     // This is the color from the vertex shader interpolated across the
                        // triangle per fragment.
                        + "void main()                    \n"     // The entry point for our fragment shader.
                        + "{                              \n"
                        + "   gl_FragColor = v_Color;     \n"     // Pass the color directly through the pipeline.
                        + "}                              \n";

        int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

        if (vertexShaderHandle != 0) {
            // Pass in the shader source.
            GLES20.glShaderSource(vertexShaderHandle, vertexShader);

            // Compile the shader.
            GLES20.glCompileShader(vertexShaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0) {
                GLES20.glDeleteShader(vertexShaderHandle);
                vertexShaderHandle = 0;
            }
        }

        if (vertexShaderHandle == 0) {
            throw new RuntimeException("Error creating vertex shader.");
        }


        // Load in the fragment shader shader.
        int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        if (fragmentShaderHandle != 0) {
            // Pass in the shader source.
            GLES20.glShaderSource(fragmentShaderHandle, fragmentShader);

            // Compile the shader.
            GLES20.glCompileShader(fragmentShaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0) {
                GLES20.glDeleteShader(fragmentShaderHandle);
                fragmentShaderHandle = 0;
            }
        }

        if (fragmentShaderHandle == 0)
            throw new RuntimeException("Error creating fragment shader.");

        // Create a program object and store the handle to it.
        int programHandle = GLES20.glCreateProgram();

        if (programHandle != 0) {
            // Bind the vertex shader to the program.
            GLES20.glAttachShader(programHandle, vertexShaderHandle);

            // Bind the fragment shader to the program.
            GLES20.glAttachShader(programHandle, fragmentShaderHandle);

            // Bind attributes
            GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
            GLES20.glBindAttribLocation(programHandle, 1, "a_Color");

            // Link the two shaders together into a program.
            GLES20.glLinkProgram(programHandle);

            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            if (linkStatus[0] == 0) { // If the link failed, delete the program.
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }
        }
        if (programHandle == 0)
            throw new RuntimeException("Error creating program.");
        return programHandle;
    }

 /*   public void drawCircle(float x, float y, float z, float r, int sides) {
        int nVertices = sides + 2;

        float[] verticesX = new float[nVertices];
        float[] verticesY = new float[nVertices];
        float[] verticesZ = new float[nVertices];

        verticesX[0] = x;
        verticesY[0] = y;
        verticesZ[0] = z;

        for(int i = 1; i < nVertices; i++) {
            verticesX[i] = x + r * (float) Math.cos(i * 2 * Math.PI / sides);
            verticesX[i] = y + r * (float) Math.sin(i * 2 * Math.PI / sides);
            verticesZ[i] = z;
        }

        float[] allVertices = new float[3 * nVertices];

        for(int i = 0; i < nVertices; i++) {
            allVertices[i * 3] = verticesX[i];
            allVertices[i * 3 + 1] = verticesY[i];
            allVertices[i * 3 + 2] = verticesZ[i];
        }



        // Color de la figura, Parametros 2, 3, 4 son RGB, cambiarlos aleatoriamente
        GLES20.glUniform4f(0, 0.0f, 0.0f, 1.0f, 1.0f);
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, nVertices);
    } */
}