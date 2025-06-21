#version 330 core

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float Radius;

in vec2 texCoord0;
out vec4 fragColor;

// 根据模糊半径估算高斯权重（固定权重数量，动态调整强度）
const int MAX_RADIUS = 10;

float gaussian(float x, float sigma) {
    return exp(-(x * x) / (2.0 * sigma * sigma)) / (2.0 * 3.14159265 * sigma * sigma);
}

void main() {
    vec2 texelSize = 1.0 / vec2(textureSize(Sampler0, 0));
    float sigma = Radius;
    vec3 result = vec3(0.0);
    float weightSum = 0.0;

    for (int x = -MAX_RADIUS; x <= MAX_RADIUS; ++x) {
        for (int y = -MAX_RADIUS; y <= MAX_RADIUS; ++y) {
            vec2 offset = vec2(float(x), float(y)) * texelSize;
            float weight = gaussian(length(vec2(x, y)), sigma);
            result += texture(Sampler0, texCoord0 + offset).rgb * weight;
            weightSum += weight;
        }
    }

    result /= weightSum;
    fragColor = vec4(result, 1.0) * ColorModulator;
}
