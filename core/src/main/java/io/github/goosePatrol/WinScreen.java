package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The screen the player sees when the game has been won.
 */
public class WinScreen extends GameScreen {
    WinScreen(Main game) {
        super(game);
    }

    @Override
    protected void update(float delta) {
        ScreenUtils.clear(Color.GREEN);
    }

    @Override
    protected void draw() {

    }
}
