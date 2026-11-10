package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * The main menu screen that the player sees before entering the game.
 */
public class MainMenuScreen extends GameScreen {
    private final ScreenViewport viewport;

    public MainMenuScreen(Main game) {
        super(game);
        viewport = new ScreenViewport();
    }

    @Override
    protected void update(float delta)
    {
        if (Gdx.input.justTouched())
            game.setScreen(new GoosePatrolScreen(game));
    }

    @Override
    protected void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        game.batch.begin();

        game.drawTextCentered(viewport, "Click to start", 0, 0);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
