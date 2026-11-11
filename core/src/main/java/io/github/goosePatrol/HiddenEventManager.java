package io.github.goosePatrol;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Class that manages hidden events.
 */
public class HiddenEventManager {
    private final ArrayList<HiddenEvent> events;
    private final ShapeRenderer shapeRenderer;
    public int eventsTriggered = 0;

    public HiddenEventManager(ArrayList<HiddenEvent> events) {
        this.events = events;
        shapeRenderer = new ShapeRenderer();
    }

    /**
     * Iterate over all the events in the event manager and trigger them if the player is colliding with them.
     * @param player The player to check collision with.
     */
    public void checkEventCollision(Player player) {
        for (HiddenEvent event : events) {
            Vector2 eventCenter = event.getCenter();
            final float minDist = player.getRadius() + event.getRadius();
            final float dist = eventCenter.cpy().dst(player.getPosition());
            final float intrusionDepth = minDist - dist;

            if (intrusionDepth > 0 && event.trigger())
                eventsTriggered++;
        }
    }

    /**
     * Get the number of events triggered.
     * @return The number of events triggered since creation.
     */
    public int getEventsTriggered() {
        return eventsTriggered;
    }

    /**
     * Temporary function to draw events as red circles to the screen.
     */
    public void debugDrawEvents(Matrix4 matrix) {
        shapeRenderer.setProjectionMatrix(matrix);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        for (HiddenEvent event : events) {
            shapeRenderer.circle(event.getCenter().x, event.getCenter().y, event.getRadius(), 12);
        }
        shapeRenderer.end();
    }

    public static HiddenEventManager getGameHiddenEventManager(GameTime time, Player player) {
        ArrayList<HiddenEvent> events = new ArrayList<>();

        events.add(new EffectEvent(player, new SpeedEffect(time, 3000, 0.5f), new Vector2(-2, -1), 0.5f));
        events.add(new EffectEvent(player, new SpeedEffect(time, 3000, -0.5f), new Vector2(-2, 1), 0.5f));

        return new HiddenEventManager(events);
    }
}
