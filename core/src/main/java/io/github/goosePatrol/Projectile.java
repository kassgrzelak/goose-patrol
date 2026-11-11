package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    private final Texture texture;
    private final float radius;
    private final Vector2 position;
    private final Vector2 velocity;
    public final int damage;

    public Projectile(Texture texture, float radius, Vector2 position, Vector2 velocity, int damage) {
        this.texture = texture;
        this.radius = radius;
        this.position = position;
        this.velocity = velocity;
        this.damage = damage;
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public float getRadius() {
        return radius;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x - radius / 2, position.y - radius / 2, radius, radius);
    }
}
