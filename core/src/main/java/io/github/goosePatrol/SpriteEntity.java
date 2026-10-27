package io.github.goosePatrol;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Base class for entities represented by a sprite, such as enemies or the player.
 * It is assumed that sprites are perfectly square and are drawn centered.
 */
public abstract class SpriteEntity
{
    protected final Sprite sprite;
    protected final Batch batch;

    SpriteEntity(Sprite sprite, Batch batch) {
        this.sprite = sprite;
        this.batch = batch;
    }

    /**
     * Draw the sprite to the screen.
     */
    public void draw() {
        sprite.draw(batch);
    }

    public float getRadius() {
        return sprite.getWidth();
    }

    /**
     * Get the sprite's current position.
     * @return The sprite's current position.
     */
    public Vector2 getPosition() {
        return new Vector2(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
    }

    /**
     * Get the sprite's current x-position.
     * @return The sprite's current x-position.
     */
    public float getX() {
        return sprite.getX();
    }

    /**
     * Get the sprite's current y-position.
     * @return The sprite's current y-position.
     */
    public float getY() {
        return sprite.getY();
    }

    /**
     * Set the sprite's position.
     * @param newX The sprite's new x-coordinate.
     * @param newY The sprite's new y-coordinate.
     */
    protected void setPosition(float newX, float newY) {
        sprite.setPosition(newX, newY);
    }

    /**
     * Add an offset to the sprite's position.
     * @param deltaX The amount to add to the sprite's x-coordinate.
     * @param deltaY The amount to add to the sprite's y-coordinate.
     */
    protected void deltaPosition(float deltaX, float deltaY) {
        sprite.setPosition(sprite.getX() + deltaX, sprite.getY() + deltaY);
    }

    /**
     * Add an offset to the sprite's position.
     * @param delta The amounts to add to the x and y-coordinates in a Vector2 object.
     */
    protected void deltaPosition(Vector2 delta) {
        sprite.setPosition(sprite.getX() + delta.x, sprite.getY() + delta.y);
    }
}
