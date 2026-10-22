package io.github.goosePatrol;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends SpriteEntity {
    private final int damage;

    Enemy(int damage, Sprite sprite) {
        super(sprite);
        this.damage = damage;
    }

    /**
     * Get the damage this enemy does to the player.
     * @return The damage this enemy does to the player.
     */
    public int getDamage() {
        return damage;
    }

    public abstract void update(float delta, Vector2 playerPosition);
}
