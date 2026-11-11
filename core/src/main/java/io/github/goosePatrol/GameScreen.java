package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * An abstract class for a screen that has a draw and update function.
 */
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



    /**
     * Get a rectangle where (x,y) is the position of its center, and the coordinate space has (0,0) in the middle of
     * the screen.
     * @param viewport A viewport, used to obtain the screen size.
     * @param x x-coordinate of the center.
     * @param y y-coordinate of the center.
     * @param width Width of the rectangle.
     * @param height Height of the rectangle.
     */
    protected static Rectangle getRectCentered(Viewport viewport, float x, float y, float width, float height) {
        final float rectX = viewport.getScreenWidth() / 2.0f + x - width / 2;
        final float rectY = viewport.getScreenHeight() / 2.0f + y - height / 2;

        return new Rectangle(rectX, rectY, width, height);
    }

    /**
     * Draw a rectangle.
     * @param shapeRenderer The ShapeRenderer to use for drawing.
     * @param rect The rectangle to draw.
     */
    protected static void drawRectangle(ShapeRenderer shapeRenderer, Rectangle rect) {
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
    }

    /**
     * Get the current position of the mouse as a Vector2.
     * @param viewport A viewport. Used to get the screen height for coordinate conversion.
     * @return A Vector2 containing the current position of the mouse.
     */
    public static Vector2 getMousePosition(Viewport viewport) {
        // Convert from left-handed coordinate system used by Gdx.input.
        return new Vector2(Gdx.input.getX(), viewport.getScreenHeight() - Gdx.input.getY());
    }
}
