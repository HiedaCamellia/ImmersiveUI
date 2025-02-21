#version 150

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform vec4 uvCoords;

in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);
    if (color.a < 0.1) {
        discard;
    }

    vec2 uv = texCoord0;
    if (uv.x >= uvCoords.x && uv.y <= uvCoords.y && uv.x <= uvCoords.z && uv.y >= uvCoords.w) {
        fragColor = color * ColorModulator;
    }else{
        discard;
    }
}
