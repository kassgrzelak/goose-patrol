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
            Vector2 eventCenter = new Vector2();
            event.getBounds().getCenter(eventCenter);

            if ()
                event.trigger();
        }
    }

    /**
     * Temporary function to draw events as red boxes to the screen.
     */
    public void debugDrawEvents(Matrix4 matrix) {
        shapeRenderer.setProjectionMatrix(matrix);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (HiddenEvent event : events) {
            shapeRenderer.rect(event.getBounds().x, event.getBounds().y,
                    event.getBounds().width, event.getBounds().height,
                    Color.RED, Color.RED, Color.RED, Color.RED);
        }
        shapeRenderer.end();
    }
}
