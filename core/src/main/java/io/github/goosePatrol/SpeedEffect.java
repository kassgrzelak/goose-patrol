package io.github.goosePatrol;

public class SpeedEffect extends Effect {
    private final float speedBoost;

    public SpeedEffect(long durationMillis, float speedBoost) {
        super(durationMillis);
        this.speedBoost = speedBoost;
    }

    @Override
    protected void applyEffect(Player player) {
        player.deltaSpeedMultiplier(speedBoost);
    }

    @Override
    protected void unapplyEffect(Player player) {
        player.deltaSpeedMultiplier(-speedBoost);
    }
}