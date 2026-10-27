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
    private final GlyphLayout layout;

    public MainMenuScreen(Main game) {
        super(game);
        viewport = new ScreenViewport();
        layout = new GlyphLayout();
    }

    @Override
    protected void update(float delta)
    {
        if (Gdx.input.isTouched())
            game.setScreen(new GoosePatrolScreen(game));
    }

    @Override
    protected void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        game.batch.begin();

        layout.setText(game.font, "Click to start");
        game.font.draw(game.batch, "Click to start", viewport.getScreenWidth() / 2f - layout.width / 2,
                viewport.getScreenHeight() / 2f - layout.height / 2);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
