package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * The main menu screen that the player sees before entering the game.
 */
public class MainMenuScreen extends GameScreen {
    private final ScreenViewport viewport;
    private final ShapeRenderer shapeRenderer;

    private Rectangle startRect = new Rectangle();
    private Rectangle quitRect = new Rectangle();

    public MainMenuScreen(Main game) {
        super(game);
        viewport = new ScreenViewport();
        shapeRenderer = new ShapeRenderer();
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
        ScreenUtils.clear(GameConstants.BACKGROUND_COLOR);
        viewport.apply();

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(GameConstants.BUTTON_COLOR);

        startRect = getRectCentered(viewport, 0, -200, 400, 100);
        drawRectangle(shapeRenderer, startRect);



        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
