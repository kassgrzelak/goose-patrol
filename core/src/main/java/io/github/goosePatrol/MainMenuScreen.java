package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen extends GameScreen {
    private final ScreenViewport viewport;

    public MainMenuScreen(Main game) {
        super(game);
        viewport = new ScreenViewport();
    }

    @Override
    public void show() {

    }

    @Override
    protected void update(float delta)
    {
        if (Gdx.input.isTouched()) {
            game.setScreen(new GoosePatrolScreen(game));
            dispose();
        }
    }

    @Override
    protected void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
