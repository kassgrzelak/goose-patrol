package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static io.github.goosePatrol.GameConstants.WORLD_HEIGHT;
import static io.github.goosePatrol.GameConstants.WORLD_WIDTH;

public class GoosePatrolScreen extends GameScreen {
    private static final float CAMERA_ASPECT_RATIO = 9f / 16;
    private static final float CAMERA_WIDTH = 16;
    private static final float CAMERA_HEIGHT = CAMERA_WIDTH * CAMERA_ASPECT_RATIO;

    private final ExtendViewport mainViewport;
    private final GameCamera camera;
    private final ScreenViewport uiViewport;

    private final Player player;

    private final Sprite background;

    public GoosePatrolScreen(Main game) {
        super(game);

        mainViewport = new ExtendViewport(CAMERA_WIDTH, CAMERA_HEIGHT);
        camera = new GameCamera(mainViewport.getCamera());
        uiViewport = new ScreenViewport();

        player = new Player(game.batch);

        background = new Sprite(new Texture("bg.jpg"));
        background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        background.setPosition(-WORLD_WIDTH / 2, - WORLD_HEIGHT / 2);
    }

    @Override
    public void show() {

    }

    @Override
    protected void update(float delta) {
        handleInput(delta);

        player.update(delta);
        camera.update(delta, player.getPosition());
    }

    /**
     * Handle keyboard/mouse inputs.
     * @param delta Time passed since last frame, in seconds.
     */
    private void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Gdx.app.exit(); // Temporary for testing.
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

        background.draw(game.batch);
        player.draw();
    }

    /**
     * Use world units for drawing rather than pixels.
     */
    private void useWorldCoordinates() {
        game.batch.setProjectionMatrix(camera.getCamera().combined);
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
