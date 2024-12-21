#version 330 core

in vec4 vertexColor;
in vec2 texCoord0;

uniform vec4 ColorModulator;
uniform float Radius;
uniform float Ratio;
uniform float BorderThickness;
uniform vec4 BorderColor;

out vec4 fragColor;

void main() {
    vec2 p = abs(step(0.5, texCoord0.xy) - texCoord0.xy);

    float edgeX = step(Radius, p.x);
    float edgeY = step(Radius, p.y * Ratio);
    float dist = step(length(vec2(p.x - Radius, p.y * Ratio - Radius)), Radius);
    float Radius2 = Radius + BorderThickness;
    float borderdist = step(length(vec2(p.x - Radius2, p.y * Ratio - Radius2)), Radius2);

    float distToEdge = min(min(p.x, p.y * Ratio), min(1.0 - p.x, 1.0 - p.y * Ratio));

    float alpha = min(1.0, edgeX + edgeY + dist);
    float borderalpha = min(1.0, edgeX + edgeY + borderdist);

    vec4 color = vertexColor;
    if(borderalpha == 0.0 ){
        color = vec4(BorderColor.rgb, color.a*BorderColor.a);
    }
    color.a *= alpha;
    if (color.a == 0.0) {
        discard;
    }
    if (distToEdge < BorderThickness/2) {
        color = vec4(BorderColor.rgb, color.a*BorderColor.a);
    }
    fragColor = color * ColorModulator;
}
