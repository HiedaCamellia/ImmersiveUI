#version 150

in vec2 texCoord0;

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float Smooth;

out vec4 fragColor;

void main() {
    vec2 center = vec2(0.5f,0.5f);
    float dist = distance(center, texCoord0);
    vec4 color = texture(Sampler0, texCoord0);
    if (color.a == 0.0) {
        discard;
    }
    float inSector = smoothstep(0, 0 + Smooth, dist) * (0.5 - smoothstep(0.5 - Smooth, 1, dist));
    if (inSector == 0.0) {
        discard;
    }
    fragColor = color * ColorModulator;
}