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

    private static final long GAME_TIME_LIMIT_MILLIS = 5 * 60 * 1000;

    /**
     * A value between 0 and 1 that determines how easily the player pushes away enemies when colliding with them. Higher
     * values lead to the player easily walking through hordes of enemies, while lower values cause the player to become
     * more easily surrounded.
     * <p>
     * PLAYER_PUSH_STRENGTH = 0.0f -> The player cannot push enemies away at all.
     * <p>
     * PLAYER_PUSH_STRENGTH = 0.5f -> The player is pushed by enemies as much as the player can push enemies back.
     * <p>
     * PLAYER_PUSH_STRENGTH = 1.0f -> The player is not pushed by enemies at all.
     */
    private static final float PLAYER_PUSH_STRENGTH = 0.8f;

    private final ExtendViewport mainViewport;
    private final GameCamera camera;
    private final ScreenViewport uiViewport;

    private final ArrayList<Enemy> enemies;

    private Player player;

    private final Sprite background;

    private long timeStarted = 0;

    private boolean gameLost = false;
    private boolean gameWon = false;

    private final HiddenEventManager hiddenEventManager;

    public GoosePatrolScreen(Main game) {
        super(game);

        mainViewport = new ExtendViewport(CAMERA_WIDTH, CAMERA_HEIGHT);
        camera = new GameCamera(mainViewport.getCamera());
        uiViewport = new ScreenViewport();

        enemies = new ArrayList<>();

        player = new Player(game.batch);

        background = new Sprite(new Texture("bg.jpg"));
        background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        background.setPosition(-WORLD_WIDTH / 2, - WORLD_HEIGHT / 2);

        ArrayList<HiddenEvent> events = new ArrayList<>();

        events.add(new EffectEvent(player, new SpeedEffect(3000, 0.5f), -2, -1, 1, 1));
        events.add(new EffectEvent(player, new SpeedEffect(3000, -0.5f), -2, 1, 1, 1));

        hiddenEventManager = new HiddenEventManager(events);
    }

    @Override
    public void show() {
        enemies.clear();
        enemies.add(new BasicGoose(game.batch, new Vector2(5, 5))); // For testing.
        enemies.add(new BasicGoose(game.batch, new Vector2(-5, 5))); // For testing.

        timeStarted = TimeUtils.millis();
    }

    /**
     * Resolve the collision (if any) between two sprite entities, moving each entity by a fraction of the offset vector.
     *
     * @param entity1 The first entity.
     * @param entity2 The second entity.
     * @param entity1Fraction The fraction of the collision resolution offset to be given to entity1. 1.0f means entity1
     *                        moves the full amount and entity2 moves none, 0.5f is an even split, etc.
     *
     * @return true if there was a collision, false if there was not.
     */
    private boolean resolveCollision(SpriteEntity entity1, SpriteEntity entity2, float entity1Fraction) {
        final float entity1Radius = entity1.getRadius();
        final float entity2Radius = entity2.getRadius();
        final float minDist = entity1Radius + entity2Radius;
        final float dist = entity1.getPosition().dst(entity2.getPosition());
        final float intrusionDepth = minDist - dist;

        // No collision.
        if (intrusionDepth <= 0)
            return false;

        final Vector2 collisionResolveLine = entity1.getPosition().sub(entity2.getPosition()).nor();
        final Vector2 offset = collisionResolveLine.cpy().scl(intrusionDepth);

        // Move each entity half the offset.
        entity1.deltaPosition(offset.cpy().scl(entity1Fraction));
        entity2.deltaPosition(offset.cpy().scl(entity1Fraction - 1));
        return true;
    }

    /**
     * Resolve enemy-enemy collisions as well as enemy-player collisions.
     * Note that collision boundaries of enemies and the player are modeled as circles.
     */
    private void resolveCollisions() {
        // Resolve enemy-enemy collisions.
        for (int i = 0; i < enemies.size() - 1; i++) {
            for (int j = i + 1; j < enemies.size(); j++) {
                final Enemy enemy1 = enemies.get(i);
                final Enemy enemy2 = enemies.get(j);

                resolveCollision(enemy1, enemy2, 0.5f);
            }
        }

        // Resolve enemy-player collisions.
        for (Enemy enemy : enemies) {
            // Make player take damage if there is a collision and hit cooldown period has passed.
            if (resolveCollision(enemy, player, PLAYER_PUSH_STRENGTH))
                player.takeDamage(enemy.getDamage());
        }
    }

    /**
     * Set the game screen to the appropriate win/loss if a win/loss condition is met. Otherwise, do nothing.
     */
    private void checkWinLossConditions() {
        if (player.isUnconscious())
            gameLost = true;
        if (TimeUtils.timeSinceMillis(timeStarted) > GAME_TIME_LIMIT_MILLIS)
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
        hiddenEventManager.checkEventCollision(player);
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

        hiddenEventManager.debugDrawEvents(camera.getCamera().combined);
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
