package atmosphere.ui;

import arc.graphics.Gl;
import arc.math.Angles;
import arc.math.geom.Vec3;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.*;
import mindustry.type.Planet;

public class CustomPlanetRenderer extends PlanetRenderer
{
    @Override
    public void renderOrbit(Planet planet, PlanetParams params)
    {
        if ((Vars.ui.planet instanceof CustomPlanetDialog customPlanetDialog && !customPlanetDialog.shouldRenderGUI) || (planet.parent == null || !planet.visible() || params.uiAlpha <= 0.02f || !planet.drawOrbit))
            return;

        Vec3 center = planet.parent.position;
        float radius = planet.orbitRadius;
        int points = (int) (radius * 10);
        Angles.circleVectors(points, radius, (cx, cy) -> batch.vertex(Tmp.v32.set(center).add(cx, 0, cy), Pal.gray.write(Tmp.c1).a(params.uiAlpha)));
        batch.flush(Gl.lineLoop);
    }
}
