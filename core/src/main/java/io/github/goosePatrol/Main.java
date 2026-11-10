package io.github.goosePatrol;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Main game class that controls which screen is visible.
 */
public class Main extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2);

        setScreen(new MainMenuScreen(this));
    }

    /**
     * Draw text centered with a coordinate system where (0,0) is the center of the screen.
     * @param viewport A viewport, used to obtain the screen size.
     * @param text The text to draw.
     * @param x The x-coordinate to draw to.
     * @param y The y-coordinate to draw to.
     */
    public void drawTextCentered(Viewport viewport, String text, float x, float y) {
        final GlyphLayout layout = new GlyphLayout();

        layout.setText(font, text);

        float textX = viewport.getScreenWidth() / 2f + x - layout.width / 2;
        float textY = viewport.getScreenHeight() / 2f + y + layout.height / 2;

        font.draw(batch, text, textX, textY);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
