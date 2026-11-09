package io.github.goosePatrol;

/**
 * An effect which changes the player's speed multiplier.
 */
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