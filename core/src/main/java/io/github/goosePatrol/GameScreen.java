package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private final Main game;

    private static final float WORLD_WIDTH = 100;
    private static final float WORLD_HEIGHT = 100;

    private static final float CAMERA_ASPECT_RATIO = 9f / 16;

    private static final float CAMERA_WIDTH = 16;
    private static final float CAMERA_HEIGHT = CAMERA_WIDTH * CAMERA_ASPECT_RATIO;

    private final ExtendViewport mainViewport;
    private final Camera camera;
    private final ScreenViewport uiViewport;

    public GameScreen(Main game) {
        this.game = game;

        mainViewport = new ExtendViewport(CAMERA_WIDTH, CAMERA_HEIGHT);
        camera = mainViewport.getCamera();
        uiViewport = new ScreenViewport();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        handleInput(delta);

        // Update frame-by-frame things in the world here.
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            camera.translate(-10f * delta, 0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            camera.translate(10f * delta, 0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camera.translate(0, -10f * delta, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            camera.translate(0, 10f * delta, 0);

        camera.update();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        game.batch.begin();
        drawWorld();
        drawUI();
        game.batch.end();
    }

    private void drawWorld() {
        useWorldCoordinates();


        // Draw world things here.
    }

    private void useWorldCoordinates() {
        game.batch.setProjectionMatrix(camera.combined);
    }

    private void drawUI() {
        useScreenCoordinates();

        // Draw UI elements here.
    }

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
