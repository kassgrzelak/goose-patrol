package io.github.goosePatrol;
import com.badlogic.gdx.math.Vector2;

/**
 * Abstract class for a hidden event which triggers upon the player colliding with it.
 */
public abstract class HiddenEvent {
    private final Vector2 center;
    private final float radius;
    private boolean triggered = false;

    public HiddenEvent(Vector2 center, float radius) {
        this.center = center.cpy();
        this.radius = radius;
    }

    /**
     * Get the event's center.
     * @return The event's center.
     */
    public Vector2 getCenter() {
        return center.cpy();
    }

    /**
     * Get the event's radius.
     * @return The event's radius.
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Trigger the event. This does nothing if the event has already been triggered.
     * @return true if the event was succcessfully triggered, false otherwise.
     */
    public boolean trigger() {
        if (triggered)
            return false;

        onTrigger();

        triggered = true;
        return true;
    }

    /**
     * The abstract method that does whatever it is the event is supposed to do when triggered.
     */
    protected abstract void onTrigger();
}
