package io.github.goosePatrol;
import com.badlogic.gdx.math.Rectangle;
public abstract class HiddenEvent {
    private final Rectangle bounds;
    private boolean triggered = false;

    public HiddenEvent(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    /**
     * Get the bounds of the event for collision detection.
     * @return The bounds of the event.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Trigger the even. This does nothing if the event has already been triggered.
     */
    public void trigger() {
        if (triggered)
            return;

        onTrigger();

        triggered = true;
    }

    /**
     * The abstract method that does whatever it is the event is supposed to do when triggered.
     */
    protected abstract void onTrigger();
}
