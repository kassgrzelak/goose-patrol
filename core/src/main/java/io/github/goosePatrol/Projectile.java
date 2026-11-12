package io.github.goosePatrol;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Projectile extends SpriteEntity {
    private final Vector2 velocity;
    public final int damage;

    public Projectile(Sprite sprite, Batch batch, Vector2 position, Vector2 velocity, int damage) {
        super(sprite, batch);
        this.velocity = velocity.cpy();
        this.damage = damage;
        setPosition(position.x, position.y);
    }

    /**
     * Update the projectile.
     * @param delta The time passed since the last frame, in seconds.
     */
    public void update(float delta) {
        deltaPosition(velocity.cpy().scl(delta));
    }
}
