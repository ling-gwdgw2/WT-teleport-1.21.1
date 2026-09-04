#version 150

in vec4 Position;

uniform mat4 ProjMat;

out vec2 texCoord;

void main() {
    vec4 outPos = ProjMat * Position;
    gl_Position = vec4(outPos.xy, 0.2, 1.0);
    texCoord = outPos.xy * 0.5 + 0.5;
}
