precision mediump float;

uniform sampler2D uTextureUnit0;
uniform sampler2D uTextureUnit1;
uniform sampler2D uTextureUnit2;
uniform sampler2D uTextureUnit3;
uniform sampler2D uTextureUnit4;
uniform sampler2D uTextureUnit5;

varying vec3 vTextureCoordinates;

void main() {
    if (vTextureCoordinates.z < 1.0) {
        gl_FragColor = texture2D(uTextureUnit0, vTextureCoordinates.xy);
    } else if (vTextureCoordinates.z < 2.0) {
        gl_FragColor = texture2D(uTextureUnit1, vTextureCoordinates.xy);
    } else if (vTextureCoordinates.z < 3.0) {
        gl_FragColor = texture2D(uTextureUnit2, vTextureCoordinates.xy);
    } else if (vTextureCoordinates.z < 4.0) {
        gl_FragColor = texture2D(uTextureUnit3, vTextureCoordinates.xy);
    } else if (vTextureCoordinates.z < 5.0) {
        gl_FragColor = texture2D(uTextureUnit4, vTextureCoordinates.xy);
    } else if (vTextureCoordinates.z < 6.0) {
        gl_FragColor = texture2D(uTextureUnit5, vTextureCoordinates.xy);
    }
}
