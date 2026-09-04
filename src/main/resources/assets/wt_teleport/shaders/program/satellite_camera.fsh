#version 150

uniform sampler2D DiffuseSampler;

uniform float ExposureIntensity;
uniform float ColorGradeStrength;

in vec2 texCoord;

out vec4 fragColor;

const vec3 LUMA = vec3(0.2126, 0.7152, 0.0722);

void main() {
    vec4 color = texture(DiffuseSampler, texCoord);
    vec3 rgb = color.rgb;

    float grade = ColorGradeStrength;
    if (grade > 0.001) {
        vec3 graded = rgb;
        graded.r = rgb.r * (1.0 - grade * 0.16);
        graded.g = rgb.g * (1.0 + grade * 0.12);
        graded.b = rgb.b * (1.0 - grade * 0.24);
        rgb = mix(rgb, graded, grade);
    }

    float exposure = ExposureIntensity;
    if (exposure > 0.001) {
        float luma = dot(rgb, LUMA);

        // Bright pixels blow out first; dark areas barely move (GTA camera adaptation).
        float brightMask = smoothstep(0.35, 0.92, luma);
        float gammaBoost = 1.0 + exposure * (0.25 + brightMask * brightMask * 5.5);
        vec3 exposed = rgb * gammaBoost;

        vec3 reinhard = exposed / (exposed + vec3(1.0));
        exposed = mix(reinhard * (rgb + vec3(1.0)), exposed, exposure * brightMask * 0.85);
        rgb = mix(rgb, exposed, exposure);
    }

    rgb = clamp(rgb, 0.0, 1.0);
    fragColor = vec4(rgb, color.a);
}