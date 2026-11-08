package io.github.goosePatrol;
import com.badlogic.gdx.math.Rectangle;
public abstract class HiddenEvent {


    protected Rectangle bounds;//
    protected boolean triggered = false;


    public HiddenEvent(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
    }


    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    public abstract void onTrigger(Player player);

}
