package io.github.goosePatrol;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * An abstract class representing some effect on the player.
 */
public abstract class Effect {
    protected final long durationMillis;
    protected long endTime = -999999;

    public Effect(long durationMillis) {
        this.durationMillis = durationMillis;
    }

    /**
     * Apply the effect on the player and set the effect's end time. This method should only be called once.
     * @param player The player to apply the effect onto.
     */
    public void apply(Player player) {
        endTime = TimeUtils.millis() + durationMillis;
        applyEffect(player);
    }

    /**
     * The abstract method that actually applies the effect. For example, adding a speed boost.
     * @param player The player to apply the effect onto.
     */
    protected abstract void applyEffect(Player player);

    /**
     * Unapply the effect if it has worn off, returning whether the effect has been unapplied or not.
     * @param player The player to possibly unapply the effect from.
     * @return true if the effect has worn off and been unapplied, or false if it has not.
     */
    public boolean unapplyIfWoreOff(Player player) {
        if (TimeUtils.millis() < endTime)
            return false;

        unapplyEffect(player);
        return true;
    }

    /**
     * The abstract method that unapplies the effect that applyEffect() put on the player. For example, subtracting out
     * a speed boost.
     * @param player The player to unapply the effect from.
     */
    protected abstract void unapplyEffect(Player player);
}
