package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class GoosePatrolScreen extends GameScreen {
    private static final float WORLD_WIDTH = 30;
    private static final float WORLD_HEIGHT = 30;

    private static final float CAMERA_ASPECT_RATIO = 9f / 16;
    private static final float CAMERA_WIDTH = 16;
    private static final float CAMERA_HEIGHT = CAMERA_WIDTH * CAMERA_ASPECT_RATIO;

    private static final long GAME_TIME_LIMIT = 5 * 60 * 1000;

    private final ExtendViewport mainViewport;
    private final GameCamera camera;
    private final ScreenViewport uiViewport;

    private final ArrayList<Enemy> enemies;

    private final Player player;

    private final Sprite background;

    private long timeStarted = 0;

    private boolean gameLost = false;
    private boolean gameWon = false;

    public GoosePatrolScreen(Main game) {
        super(game);

        mainViewport = new ExtendViewport(CAMERA_WIDTH, CAMERA_HEIGHT);
        camera = new GameCamera(mainViewport.getCamera());
        uiViewport = new ScreenViewport();

        enemies = new ArrayList<>();
        enemies.add(new BasicGoose(game.batch)); // For testing.

        player = new Player(game.batch);

        background = new Sprite(new Texture("bg.jpg"));
        background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        background.setPosition(-WORLD_WIDTH / 2, - WORLD_HEIGHT / 2);
    }

    @Override
    public void show() {
        timeStarted = TimeUtils.millis();
    }

    /**
     * Resolve collisions between the player and enemies.
     */
    private void resolveCollisions() {
        for (Enemy enemy : enemies) {
            final float playerRadius = player.getWidth() / 2;
            final float enemyRadius = enemy.getWidth() / 2;
            final float minDist = playerRadius + enemyRadius;
            final float dist = player.getPosition().dst(enemy.getPosition());
            final float intrusionDepth = minDist - dist;

            // No collision.
            if (intrusionDepth <= 0)
                continue;

            final Vector2 collisionResolveLine = enemy.getPosition().cpy().sub(player.getPosition()).nor();
            final Vector2 offset = collisionResolveLine.cpy().scl(intrusionDepth);

            enemy.deltaPosition(offset);

            player.takeDamage(enemy.getDamage());
        }
    }

    private void checkWinLossConditions() {
        if (player.isUnconscious())
            gameLost = true;
        if (TimeUtils.timeSinceMillis(timeStarted) > GAME_TIME_LIMIT)
            gameWon = true;

        if (gameLost)
            game.setScreen(new LossScreen(game));
        else if (gameWon)
            game.setScreen(new WinScreen(game));
    }

    @Override
    protected void update(float delta) {
        checkWinLossConditions();

        handleInput(delta);

        player.update(delta);
        camera.update(delta, player.getPosition());

        for (Enemy enemy : enemies)
            enemy.update(delta, player.getPosition());

        resolveCollisions();
    }

    /**
     * Handle keyboard/mouse inputs.
     * @param delta Time passed since the last frame, in seconds.
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

        for (Enemy enemy : enemies)
            enemy.draw();

        player.draw();
    }

    /**
     * Use world units for drawing rather than pixels.
     */
    private void useWorldCoordinates() {
        game.batch.setProjectionMatrix(camera.getCamera().combined);
    }

    /**
     * Draw the player's health UI.
     */
    private void drawHealth() {
        game.font.draw(game.batch, "Health: " + player.getHealth() + "/100", 5, 30);
    }

    /**
     * Draw the timer UI.
     */
    private void drawTimer() {
        final long timeElapsed = TimeUtils.timeSinceMillis(timeStarted) / 1000;
        final long minutes = timeElapsed / 60;
        final long seconds = timeElapsed % 60;
        final String timeString = String.format("%02d:%02d", minutes, seconds);
        game.font.draw(game.batch, timeString, 5, 60);
    }

    /**
     * Draw UI elements.
     */
    private void drawUI() {
        useScreenCoordinates();

        drawHealth();
        drawTimer();
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
