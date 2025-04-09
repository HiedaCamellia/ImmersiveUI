#version 330 core

in vec4 vertexColor;
in vec2 texCoord0;

uniform vec4 ColorModulator;
uniform float Radius;
uniform vec4 BorderColor;

out vec4 fragColor;

void main() {






    fragColor = color * ColorModulator;
}
