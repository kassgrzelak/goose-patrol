package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GoosePatrolScreen extends GameScreen {
    private final Main game;

    private static final float CAMERA_ASPECT_RATIO = 9f / 16;

    private static final float CAMERA_WIDTH = 16;
    private static final float CAMERA_HEIGHT = CAMERA_WIDTH * CAMERA_ASPECT_RATIO;

    private final ExtendViewport mainViewport;
    private final Camera camera;
    private final ScreenViewport uiViewport;

    public GoosePatrolScreen(Main game) {
        this.game = game;

        mainViewport = new ExtendViewport(CAMERA_WIDTH, CAMERA_HEIGHT);
        camera = mainViewport.getCamera();
        uiViewport = new ScreenViewport();
    }

    @Override
    public void show() {

    }

    @Override
    protected void update(float delta) {
        handleInput(delta);

        // Update frame-by-frame things in the world here.
    }

    /**
     * Handle keyboard/mouse inputs.
     * @param delta Time passed since last frame, in seconds.
     */
    private void handleInput(float delta) {

    }

    @Override
    protected void draw() {
        ScreenUtils.clear(Color.PURPLE);

        game.batch.begin();
        drawWorld();
        drawUI();
        game.batch.end();
    }

    /**
     * Draw world objects.
     */
    private void drawWorld() {
        useWorldCoordinates();


        // Draw world things here.
    }

    /**
     * Use world units for drawing rather than pixels.
     */
    private void useWorldCoordinates() {
        game.batch.setProjectionMatrix(camera.combined);
    }

    /**
     * Draw UI elements.
     */
    private void drawUI() {
        useScreenCoordinates();

        // Draw UI elements here.
    }

    /**
     * Use pixels for drawing rather than world units.
     */
    private void useScreenCoordinates() {
        game.batch.setProjectionMatrix(uiViewport.getCamera().combined);
    }

    @Override
    public void resize(int width, int height) {
        mainViewport.update(width, height);
        uiViewport.update(width, height, true);
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
