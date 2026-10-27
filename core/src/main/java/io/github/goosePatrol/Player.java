package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Player extends SpriteEntity {
    private int health = 100;

    private final Vector2 velocity;

    private long timeOfLastHit = -999999;

    private static final float PLAYER_SPEED = 5;
    private static final float PLAYER_ACCEL = 10;
    private static final long PLAYER_HIT_COOLDOWN = 1000;

    public Player(SpriteBatch batch) {
        super(new Sprite(new Texture("player.jpg")), batch);
        sprite.setSize(1, 1);
        sprite.setPosition(-0.5f, -0.5f); // Player starts at (0,0), this centers them.

        velocity = new Vector2(0, 0);
    }

    /**
     * Handle keyboard/mouse inputs.
     * @param delta Time passed since the last frame, in seconds.
     */
    private void handleInput(float delta) {
        Vector2 targetVel = new Vector2(0, 0);

        // Input keys determine target direction.
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            targetVel.add(0, 1);
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            targetVel.add(-1, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            targetVel.add(0, -1);
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            targetVel.add(1, 0);

        // Normalize then scale to player speed.
        targetVel.nor().scl(PLAYER_SPEED);

        // Smoothly change player's velocity to the target velocity. The effect is a smoother movement than instantly
        // changing to the target velocity, with configurable smoothness by changing the PLAYER_ACCEL constant.
        velocity.lerp(targetVel, delta * PLAYER_ACCEL);
    }

    /**
     * Update the player.
     * @param delta the time passed since the last frame, in seconds.
     */
    public void update(float delta) {
        handleInput(delta);

        // Move player.
        deltaPosition(velocity.cpy().scl(delta));
    }

    /**
     * Get the player's current health.
     * @return The player's current health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get whether the player can be hit again.
     * @return true if the hit cooldown period has passed, false otherwise.
     */
    private boolean canBeHit() {
        return TimeUtils.timeSinceMillis(timeOfLastHit) > PLAYER_HIT_COOLDOWN;
    }

    /**
     * Lower the player's health, but only if the hit cooldown period has passed.
     * @param damage The amount the player's health should be reduced by.
     */
    public void takeDamage(int damage) {
        if (!canBeHit())
            return;

        health -= damage;
        timeOfLastHit = TimeUtils.millis();
    }

    /**
     * Get whether the player is unconscious or not.
     * @return true if the player's health is <= 0, false otherwise.
     */
    public boolean isUnconscious() {
        return health <= 0;
    }
}
