package atmosphere.animation;

import arc.Events;
import arc.func.*;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.game.EventType;

public class Transitions
{
    private final Seq<Transition> transitions = new Seq<>();
    private float time = 0f, progressTime = 0f;
    private int progress = 0;

    private boolean isActive = false;

    public void Add(Cons<Float> transition, float duration)
    {
        transitions.add(new Transition(transition, duration, false));
        Stop();
    }

    public void AddInstant(Runnable action)
    {
        transitions.add(new Transition(t -> action.run(), 0f, true));
        Stop();
    }

    public void AddWait(float duration)
    {
        transitions.add(new Transition(t -> {}, duration, false));
        Stop();
    }

    public void AddWaitWhile(Boolp condition)
    {
        transitions.add(new WaitAction(condition, t -> {}));
        Stop();
    }

    public void AddWaitWhileAction(Boolp condition, Cons<Float> action)
    {
        transitions.add(new WaitAction(condition, action));
        Stop();
    }

    public void Start()
    {
        if (!isActive)
        {
            Events.run(EventType.Trigger.update, this::Update);
            isActive = true;
        }

        time = 1f;
        progressTime = 0f;
        progress = 0;
    }

    public boolean IsRunning()
    {
        return progress < transitions.size;
    }

    public void Stop()
    {
        time = 0f;
        progressTime = 0f;
        progress = transitions.size;
    }

    public void Next()
    {
        if (progress < transitions.size - 1)
        {
            progress++;
            time = 1f;
            progressTime = 0f;
        }
        else Stop();
    }

    private void Update()
    {
        if (time > 0f)
        {
            Transition transition = transitions.get(progress);

            transition.runnable.get((transition instanceof WaitAction) ? progressTime : 1f - time);

            progressTime += Time.delta;

            if (!(transition instanceof WaitAction)) time -= Time.delta / (transition.duration * 60f);

            if ((time < 0f || transition.instant) && !(transition instanceof WaitAction waitAction && waitAction.condition.get()))
            {
                if (!transition.instant) transition.runnable.get(1f);

                Next();
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

        public WaitAction(Boolp condition, Cons<Float> action)
        {
            super (action, 0f, true);

            this.condition = condition;
        }
    }
}
