package io.github.goosePatrol;

public class MudSlowDownEvent extends HiddenEvent{
    public MudSlowDownEvent(float x, float y) {
        super(x,y,1.0f,1.0f);
    }
    @Override
    public void onTrigger(Player player) {
        if (!isTriggered()) {
            setTriggered(true);
            player.applySpeedMultiplier(0.5f, 3.0f); // appllies a 0.5x speed for 8 seconds
        }
    }


}
