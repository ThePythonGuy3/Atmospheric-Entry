package atmosphere;

import arc.Core;
import arc.graphics.Texture;
import arc.graphics.gl.Shader;
import mindustry.Vars;

public class Shaders
{
    public static final MaskShader maskShader = new MaskShader();

    public static class MaskShader extends ModShader
    {
        public float alpha;
        public Texture mask;

        public MaskShader()
        {
            super("mask");
        }

        @Override
        public void apply()
        {
            setUniformf("u_alpha", alpha);

            mask.bind(1);
            Vars.renderer.effectBuffer.getTexture().bind(0);

            setUniformi("u_mask", 1);
        }
    }

    public static class ModShader extends Shader
    {
        public ModShader(String frag)
        {
            super(Core.files.internal("shaders/default.vert"), Vars.tree.get("shaders/" + frag + ".frag"));
        }
    }
}
