package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import static io.github.goosePatrol.GameConstants.WORLD_HEIGHT;
import static io.github.goosePatrol.GameConstants.WORLD_WIDTH;

/**
 * Player class.
 */
public class Player extends SpriteEntity {
    private int health = 100;

    private final Vector2 velocity;

    private final GameTime time;
    private long timeOfLastHit = -999999;

    private static final float PLAYER_SPEED = 5;
    private static final float PLAYER_ACCEL = 10;
    private static final long PLAYER_HIT_COOLDOWN_MILLIS = 1000;

    /**
     * A value between 0 and 1 that determines how easily the player pushes away enemies when colliding with them. Higher
     * values lead to the player easily walking through hordes of enemies, while lower values cause the player to become
     * more easily surrounded.
     * <p>
     * PLAYER_PUSH_STRENGTH = 0.0f -> The player cannot push enemies away at all.
     * <p>
     * PLAYER_PUSH_STRENGTH = 0.5f -> The player is pushed by enemies as much as the player can push enemies back.
     * <p>
     * PLAYER_PUSH_STRENGTH = 1.0f -> The player is not pushed by enemies at all.
     */
    public static final float PLAYER_PUSH_STRENGTH = 0.8f;

    private static final float PLAYER_MIN_SPEED_MULTIPLIER = 0.1f;
    private static final float PLAYER_MAX_SPEED_MULTIPLIER = 2.0f;

    private float speedMultiplier = 1.0f;

    private final ArrayList<Effect> activeEffects = new ArrayList<>();

    public Player(SpriteBatch batch, GameTime time) {
        super(new Sprite(new Texture("player.jpg")), batch);
        sprite.setSize(1, 1);
        sprite.setPosition(-0.5f, -0.5f); // Player starts at (0,0), this centers them.

        velocity = new Vector2(0, 0);

        this.time = time;
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

        // Normalize then scale to player speed, scaled by speed multiplier.
        targetVel.nor().scl(PLAYER_SPEED *
                MathUtils.clamp(speedMultiplier, PLAYER_MIN_SPEED_MULTIPLIER, PLAYER_MAX_SPEED_MULTIPLIER));

        // Smoothly change player's velocity to the target velocity. The effect is a smoother movement than instantly
        // changing to the target velocity, with configurable smoothness by changing the PLAYER_ACCEL constant.
        velocity.lerp(targetVel, delta * PLAYER_ACCEL);
    }

    private void updateEffects() {
        final ArrayList<Effect> removeEffects = new ArrayList<>();

        for (Effect effect : activeEffects) {
            if (effect.unapplyIfWoreOff(this))
                removeEffects.add(effect);
        }

        for (Effect effect : removeEffects)
            activeEffects.remove(effect);
    }

    public void update(float delta) {
        updateEffects();

        handleInput(delta);

        // Move player.
        deltaPosition(velocity.cpy().scl(delta));

        if (getX() < -WORLD_WIDTH / 2 + getRadius())
            setX(-WORLD_WIDTH / 2 + getRadius());
        if (getX() > WORLD_WIDTH / 2 - getRadius())
            setX(GameConstants.WORLD_WIDTH / 2 - getRadius());
        if (getY() < -WORLD_HEIGHT / 2 + getRadius())
            setY(-WORLD_HEIGHT / 2 + getRadius());
        if (getY() > WORLD_HEIGHT / 2 - getRadius())
            setY(WORLD_HEIGHT / 2 - getRadius());
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
        return time.getTimeMillis() - timeOfLastHit > PLAYER_HIT_COOLDOWN_MILLIS;
    }

    /**
     * Lower the player's health, but only if the hit cooldown period has passed.
     * @param damage The amount the player's health should be reduced by.
     */
    public void takeDamage(int damage) {
        if (!canBeHit())
            return;

        health -= damage;
        timeOfLastHit = time.getTimeMillis();
    }

    /**
     * Add health to the player. This method will not take the player's health above 100.
     * @param amount The amount to heal by.
     */
    public void addHealth(int amount) {
        health += amount;
        health = MathUtils.clamp(health, 0, 100);
    }

    /**
     * Get whether the player is unconscious or not.
     * @return true if the player's health is <= 0, false otherwise.
     */
    public boolean isUnconscious() {
        return health <= 0;
    }

    public void addEffect(Effect effect) {
        effect.apply(this);
        activeEffects.add(effect);
    }

    /**
     * Change the player's speed multiplier by some amount.
     * @param delta The amount to change the player's speed multiplier by.
     */
    public void deltaSpeedMultiplier(float delta) {
        speedMultiplier += delta;
    }
}




