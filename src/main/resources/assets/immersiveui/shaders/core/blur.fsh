#version 150

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float Radius;

in vec2 texCoord0;

out vec4 fragColor;

vec4 getBlurColor (vec2 pos) {
    vec4 color = vec4(0); // 初始颜色
    float sum = 0.0; // 总权重


    float blurstep = Radius / 5; // 步长
    // 卷积过程
    for(int i =0 ;i<5;i++){
        for(int j =0 ;j<5;j++){
            vec2 target = pos + vec2(i-2, j-2)*blurstep; // 目标像素位置
            float weight = (Radius - abs(i-2)) * (Radius - abs(j-2)); // 计算权重
            color += texture(Sampler0, target) * weight; // 累加颜色
            sum += weight; // 累加权重
        }
    };
    color /= sum; // 求出一个平均值
    return color;
}
void main() {
    vec4 color = texture(Sampler0, texCoord0);
    vec4 blurcolor = getBlurColor(texCoord0); // 获取模糊后的颜色
    blurcolor.a = color.a; // 还原透明度

    fragColor = blurcolor * ColorModulator;
}
