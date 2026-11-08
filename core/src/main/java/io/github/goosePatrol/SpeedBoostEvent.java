package io.github.goosePatrol;

public class SpeedBoostEvent extends HiddenEvent{
    public SpeedBoostEvent(float x, float y) {
        super(x,y,1.0f,1.0f);
    }

    @Override
    public void onTrigger(Player player) {
        if (!triggered) {
            triggered = true;
            player.applySpeedMultiplier(1.5f,5.0f) ;
        }
    }
}