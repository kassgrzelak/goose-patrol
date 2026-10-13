package io.github.goosePatrol;

import com.badlogic.gdx.Screen;

public abstract class GameScreen implements Screen {
    /**
     * This function is called once per frame. It calls update() then draw().
     * @param delta The time passed since the last frame, in seconds.
     */
    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    /**
     * Update objects that need to be updated every frame, such as enemies or the player.
     * @param delta the time passed since the last frame, in seconds.
     */
    protected abstract void update(float delta);

    /**
     * Draw objects to the screen.
     */
    protected abstract void draw();
}
