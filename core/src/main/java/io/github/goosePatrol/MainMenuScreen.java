package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
        final Vector2 mousePosition = getMousePosition(viewport);

        if (Gdx.input.justTouched()) {
            if (startRect.contains(mousePosition))
                game.setScreen(new MainScreen(game));
            else if (quitRect.contains(mousePosition))
                Gdx.app.exit();
        }
    }

    @Override
    protected void draw()
    {
        ScreenUtils.clear(GameConstants.BACKGROUND_COLOR);

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(GameConstants.BUTTON_COLOR);

        startRect = getRectCentered(viewport, 0, 100, 400, 100);
        drawRectangle(shapeRenderer, startRect);

        quitRect = getRectCentered(viewport, 0, -100, 400, 100);
        drawRectangle(shapeRenderer, quitRect);

        shapeRenderer.end();

        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();
        game.drawTextCentered(viewport, "Start", 0, 100);
        game.drawTextCentered(viewport, "Quit", 0, -100);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
