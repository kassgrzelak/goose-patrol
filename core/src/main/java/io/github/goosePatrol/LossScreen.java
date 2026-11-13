package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The screen the player sees when the game has been lost.
 */
public class LossScreen extends GameScreen {

    LossScreen(Main game) {
        super(game);
    }

    @Override
    protected void update(float delta) {
    }

    @Override
    protected void draw() {
        ScreenUtils.clear(Color.RED);
    }
}
