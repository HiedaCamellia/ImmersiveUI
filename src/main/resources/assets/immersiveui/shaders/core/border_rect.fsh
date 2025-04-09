#version 330 core

in vec4 vertexColor;
in vec2 texCoord0;

uniform vec4 ColorModulator;
uniform vec2 Radius;

out vec4 fragColor;

void main() {

    vec4 color = vertexColor;

    if(!(texCoord0.x<Radius.x||texCoord0.x>(1.0f-Radius.x)||texCoord0.y<Radius.y||texCoord0.y>(1.0f-Radius.y))){
        discard;
    }

    fragColor = color * ColorModulator;
}
