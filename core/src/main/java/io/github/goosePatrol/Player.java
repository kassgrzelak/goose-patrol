package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends SpriteEntity {
    private final Vector2 velocity;

    private final SpriteBatch batch;

    private static final float PLAYER_SPEED = 5;
    private static final float PLAYER_ACCEL = 10;

    public Player(SpriteBatch batch) {
        super(new Sprite(new Texture("player.jpg")));
        sprite.setSize(1, 1);
        sprite.setPosition(-0.5f, -0.5f);

        velocity = new Vector2(0, 0);

        this.batch = batch;
    }

    /**
     * Handle keyboard/mouse inputs.
     * @param delta Time passed since last frame, in seconds.
     */
    private void handleInput(float delta) {
        Vector2 targetVel = new Vector2(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            targetVel.add(0, 1);
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            targetVel.add(-1, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            targetVel.add(0, -1);
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            targetVel.add(1, 0);

        targetVel.nor().scl(PLAYER_SPEED);

        velocity.lerp(targetVel, delta * PLAYER_ACCEL);
    }

    /**
     * Update the player.
     * @param delta the time passed since the last frame, in seconds.
     */
    public void update(float delta) {
        handleInput(delta);

        deltaPosition(velocity.cpy().scl(delta));
    }

    /**
     * Draw the player to the screen.
     */
    public void draw() {
        sprite.draw(batch);
    }
}
