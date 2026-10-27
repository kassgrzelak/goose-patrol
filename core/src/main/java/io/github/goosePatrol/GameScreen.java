package io.github.goosePatrol;

import com.badlogic.gdx.ScreenAdapter;

public abstract class GameScreen extends ScreenAdapter
{
    protected final Main game;

    GameScreen(Main game) {
        this.game = game;
    }

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
