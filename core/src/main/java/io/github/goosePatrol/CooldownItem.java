package io.github.goosePatrol;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Abstract class for an item with unlimited uses but with a cooldown between each use.
 */
public abstract class CooldownItem extends Item {
    private final GameTime time;
    private final long cooldownDurationMillis;
    private long lastUseTime = -999999;

    public CooldownItem(Texture texture, GameTime time, long cooldownDurationMillis) {
        super(texture);
        this.time = time;
        this.cooldownDurationMillis = cooldownDurationMillis;
    }

    @Override
    public void useIfPossible() {
        if (time.getTimeMillis() - lastUseTime < cooldownDurationMillis)
            return;

        use();

        lastUseTime = time.getTimeMillis();
    }

    /**
     * Returns the fraction of the cooldown duration that has passed. 0 means the item has just been used and none of
     * the cooldown period has passed. As the value goes to 1 the item is closer and closer to being able to be used,
     * and when the fraction is >= 1, the item can be used.
     * @return The time passed since last use divided by cooldown duration.
     */
    public float getCooldownFraction() {
        return (float)(time.getTimeMillis() - lastUseTime) / cooldownDurationMillis;
    }

    /**
     * Actually perform the action for this item. The useIfPossible() method handles cooldown for you.
     */
    protected abstract void use();
}
