package atmosphere;

import arc.Events;
import atmosphere.animation.Transitions;
import atmosphere.input.InputRegister;
import atmosphere.ui.*;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;

public class Atmosphere extends Mod
{
    private final Transitions transitions = new Transitions();

    @Override
    public void init()
    {
        Events.on(EventType.ClientLoadEvent.class, e -> {
            InputRegister.Initialize();

            Vars.renderer.planets = new CustomPlanetRenderer();
            Vars.ui.planet = new CustomPlanetDialog();
        });
    }
}