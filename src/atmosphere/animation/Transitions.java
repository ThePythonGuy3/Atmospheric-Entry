package atmosphere.animation;

import arc.Events;
import arc.func.Cons;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.game.EventType;

public class Transitions
{
    private final Seq<Transition> transitions = new Seq<>();
    private float time = 0f;

    private boolean isActive = false;

    public void Add(Cons<Float> transition, float duration)
    {
        transitions.insert(0, new Transition(transition, duration));
    }

    public void Start()
    {
        if (!isActive)
        {
            Events.run(EventType.Trigger.update, this::Update);
            isActive = true;
        }

        time = transitions.size;
    }

    public boolean IsRunning()
    {
        return time > 0f;
    }

    public void Stop()
    {
        time = 0f;
    }

    private void Update()
    {
        if (time > 0f)
        {
            for (int i = transitions.size - 1; i >= 0; i--)
            {
                if (time > i && time <= i + 1f)
                {
                    Transition transition = transitions.get(i);

                    transition.runnable.get(1f - (time - i));

                    time -= Time.delta / (transition.duration * 60f);
                }
            }

            if (time < 0f) time = 0f;
        }
    }

    private class Transition
    {
        private final Cons<Float> runnable;
        private final float duration;

        public Transition(Cons<Float> runnable, float duration)
        {
            this.runnable = runnable;
            this.duration = duration;
        }
    }
}
