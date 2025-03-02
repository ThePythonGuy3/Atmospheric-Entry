package atmosphere;

import arc.*;
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
        if (Vars.headless) return; // This is purely visual lol

        Events.on(EventType.ClientLoadEvent.class, e -> {
            InputRegister.Initialize();

            Vars.ui.loadfrag = new CustomLoadingFragment();
            Vars.ui.loadfrag.build(Core.scene.root);
            Vars.renderer.planets = new CustomPlanetRenderer();
            Vars.ui.planet = new CustomPlanetDialog();
        });
    }
}