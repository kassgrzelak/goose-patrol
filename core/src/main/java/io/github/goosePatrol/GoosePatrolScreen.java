package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
        enemies.add(new BasicGoose(game.batch, new Vector2(5, 5))); // For testing.
        enemies.add(new BasicGoose(game.batch, new Vector2(-5, 5))); // For testing.

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
     * Resolve the collision (if any) between two sprite entities, moving both entities half the offset vector (i.e.,
     * sharing the collision resolution).
     *
     * @param entity1 The first entity
     * @param entity2 The second entity
     */
    private void resolveCollisionShared(SpriteEntity entity1, SpriteEntity entity2) {
        final float entity1Radius = entity1.getRadius();
        final float entity2Radius = entity2.getRadius();
        final float minDist = entity1Radius + entity2Radius;
        final float dist = entity1.getPosition().dst(entity2.getPosition());
        final float intrusionDepth = minDist - dist;

        // No collision.
        if (intrusionDepth <= 0)
            return;

        final Vector2 collisionResolveLine = entity1.getPosition().sub(entity2.getPosition()).nor();
        final Vector2 halfOffset = collisionResolveLine.cpy().scl(intrusionDepth / 2);

        // Move each entity half the offset.
        entity1.deltaPosition(halfOffset);
        entity2.deltaPosition(halfOffset.cpy().scl(-1));

    }

    /**
     * Resolve the collision (if any) between two sprite entities, moving one entity by the entire offset vector instead
     * of sharing the offset between them.
     * @param stationary The stationary entity. This entity will not be moved if there is a collision.
     * @param moving The moving entity. This entity will be moved to resolve a collision if there is one.
     * @return true if a collision occurred, false otherwise.
     */
    private boolean resolveCollisionOneSided(SpriteEntity stationary, SpriteEntity moving) {
        final float stationaryRadius = stationary.getRadius();
        final float movingRadius = moving.getRadius();
        final float minDist = stationaryRadius + movingRadius;
        final float dist = stationary.getPosition().dst(moving.getPosition());
        final float intrusionDepth = minDist - dist;

        // No collision.
        if (intrusionDepth <= 0)
            return false;

        // Move moving entity away from stationary entity until collision is resolved.
        final Vector2 collisionResolveLine = moving.getPosition().sub(stationary.getPosition()).nor();
        final Vector2 offset = collisionResolveLine.cpy().scl(intrusionDepth);

        moving.deltaPosition(offset);
        return true;
    }

    /**
     * Resolve collisions between the player and enemies.
     * Note that collision boundaries of enemies and the player are modeled as circles.
     */
    private void resolveCollisions() {
        // Resolve enemy-enemy collisions.
        for (int i = 0; i < enemies.size() - 1; i++) {
            for (int j = i + 1; j < enemies.size(); j++) {
                final Enemy enemy1 = enemies.get(i);
                final Enemy enemy2 = enemies.get(j);

                resolveCollisionShared(enemy1, enemy2);
            }
        }

        // Resolve enemy-player collisions.
        for (Enemy enemy : enemies) {
            // Make player take damage if there is a collision and hit cooldown period has passed.
            if (resolveCollisionOneSided(player, enemy))
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
}
