package io.github.goosePatrol;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Base class for enemies. Implementors must override update(), which determines how the enemy behaves in-game.
 */
public abstract class Enemy extends SpriteEntity {
    private final int damage;

    Enemy(int damage, Sprite sprite, Batch batch) {
        super(sprite, batch);
        this.damage = damage;
    }

    /**
     * Get the damage this enemy does to the player.
     * @return The damage this enemy does to the player.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Update the enemy. This can be overridden to give different enemies different behaviors.
     * @param delta The time passed since the last frame, in seconds.
     * @param playerPosition The current position of the player in world space.
     */
    public abstract void update(float delta, Vector2 playerPosition);
}
