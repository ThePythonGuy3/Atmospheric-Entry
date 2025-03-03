package atmosphere.animation;

import arc.Events;
import arc.func.*;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.game.EventType;

public class Transitions
{
    private final Seq<Transition> transitions = new Seq<>();
    private float time = 0f;
    private int progress = 0;

    private boolean isActive = false;

    public void Add(Cons<Float> transition, float duration)
    {
        transitions.add(new Transition(transition, duration, false));
    }

    public void AddInstant(Runnable action)
    {
        transitions.add(new Transition(t -> action.run(), 0f, true));
    }

    public void AddWait(float duration)
    {
        transitions.add(new Transition(t -> {}, duration, false));
    }

    public void AddWaitWhile(Boolp condition)
    {
        transitions.add(new WaitAction(condition));
    }

    public void Start()
    {
        if (!isActive)
        {
            Events.run(EventType.Trigger.update, this::Update);
            isActive = true;
        }

        time = 1f;
        progress = 0;
    }

    public boolean IsRunning()
    {
        return time > 0f && progress < transitions.size;
    }

    public void Stop()
    {
        time = 0f;
        progress = transitions.size;
    }

    private void Update()
    {
        if (time > 0f)
        {
            Transition transition = transitions.get(progress);

            transition.runnable.get(1f - time);

            if (!(transition instanceof WaitAction)) time -= Time.delta / (transition.duration * 60f);

            if ((time < 0f || transition.instant) && !(transition instanceof WaitAction waitAction && waitAction.condition.get()))
            {
                if (progress < transitions.size - 1)
                {
                    progress++;
                    time = 1f;
                }
                else Stop();
            }
        }
    }

    private static class Transition
    {
        private final Cons<Float> runnable;
        private final float duration;
        private final boolean instant;

        public Transition(Cons<Float> runnable, float duration, boolean instant)
        {
            this.runnable = runnable;
            this.duration = duration;
            this.instant = instant;
        }
    }

    private static class WaitAction extends Transition
    {
        private final Boolp condition;

        public WaitAction(Boolp condition)
        {
            super (t -> {}, 0f, true);

            this.condition = condition;
        }
    }
}
