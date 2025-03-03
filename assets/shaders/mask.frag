#define HIGHP

uniform sampler2D u_texture;
uniform sampler2D u_mask;

uniform float u_alpha;

varying vec2 v_texCoords;

void main(){
    vec4 maskColor = texture2D(u_mask, v_texCoords.xy);

    vec4 finalColor = texture2D(u_texture, v_texCoords.xy);
    finalColor.a = (1.0 - maskColor.a) * u_alpha;

    gl_FragColor = finalColor;
}