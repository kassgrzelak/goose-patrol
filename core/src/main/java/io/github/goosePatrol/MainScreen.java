package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

/**
 * The main screen where the actual game is played.
 */
public class MainScreen extends GameScreen {
    private static final float WORLD_WIDTH = 30;
    private static final float WORLD_HEIGHT = 30;

    private static final float CAMERA_ASPECT_RATIO = 9f / 16;
    private static final float CAMERA_WIDTH = 16;
    private static final float CAMERA_HEIGHT = CAMERA_WIDTH * CAMERA_ASPECT_RATIO;

    private static final long GAME_TIME_LIMIT_MILLIS = 5 * 60 * 1000;

    private final ExtendViewport mainViewport;
    private final GameCamera camera;
    private final ScreenViewport uiViewport;

    private final ArrayList<Enemy> enemies;

    private final Player player;

    private final Sprite background;

    private final long timeStarted;

    private boolean gameLost = false;
    private boolean gameWon = false;

    private final HiddenEventManager hiddenEventManager;
    private final WaveManager waveManager;

    private boolean paused = false;

    private final ShapeRenderer shapeRenderer;

    private Rectangle resumeRect = new Rectangle();
    private Rectangle restartRect = new Rectangle();
    private Rectangle mainMenuRect = new Rectangle();

    private final GameTime time = new GameTime();

    private final ItemManager itemManager;

    private final ArrayList<Projectile> projectiles = new ArrayList<>();
    
    private int itemsUsed = 0;

    public MainScreen(Main game) {
        super(game);

        mainViewport = new ExtendViewport(CAMERA_WIDTH, CAMERA_HEIGHT);
        camera = new GameCamera(mainViewport.getCamera());
        uiViewport = new ScreenViewport();

        enemies = new ArrayList<>();
        waveManager = WaveManager.getGameWaveManager(game.batch, enemies, time);

        player = new Player(game.batch, time);

        background = new Sprite(new Texture("map.png"));
        background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        background.setPosition(-WORLD_WIDTH / 2, - WORLD_HEIGHT / 2);

        timeStarted = TimeUtils.millis();

        hiddenEventManager = HiddenEventManager.getGameHiddenEventManager(time, player);

        shapeRenderer = new ShapeRenderer();

        itemManager = ItemManager.getGameItemManager(time, mainViewport, player, projectiles, game.batch);
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
            if (resolveCollision(enemy, player, Player.PLAYER_PUSH_STRENGTH))
                player.takeDamage(enemy.getDamage());
        }
    }

    @Override
    public void pause() {
        pauseGame();
    }

    /**
     * Pause the game.
     */
    private void pauseGame() {
        paused = true;
    }

    /**
     * Unpause the game.
     */
    private void unpauseGame() {
        paused = false;
    }

    /**
     * Get whether the player is out of time or not.
     * @return true if the player is out of time and the game is not paused.
     */
    private boolean outOfTime() {
        return getTimeElapsed() > GAME_TIME_LIMIT_MILLIS && !paused;
    }

    /**
     * Get the amount of time elapsed, accounting for time spent paused.
     * @return The time remaining.
     */
    private long getTimeElapsed() {
        return time.getTimeMillis() - timeStarted;
    }

    /**
     * Set the game screen to the appropriate win/loss screen if a win/loss condition is met. Otherwise, do nothing.
     */
    private void checkWinLossConditions() {
        if (player.isUnconscious())
            gameLost = true;
        if (outOfTime())
            gameWon = true;
        if (waveManager.isFinished() && enemies.isEmpty())
            gameWon = true;

        if (gameLost)
            game.setScreen(new LossScreen(game));
        else if (gameWon)
            game.setScreen(new WinScreen(game));
    }

    private void checkProjectileCollisions() {
        final ArrayList<Projectile> projectilesToDelete = new ArrayList<>();
        final ArrayList<Enemy> enemiesToDelete = new ArrayList<>();

        for (Projectile projectile : projectiles) {
            if (projectile.isOutOfBounds()) {
                projectilesToDelete.add(projectile);
                continue;
            }

            for (Enemy enemy : enemies) {
                if (projectile.getPosition().dst(enemy.getPosition()) < projectile.getRadius() + enemy.getRadius()) {
                    enemy.takeDamage(projectile.damage);
                    projectilesToDelete.add(projectile);

                    if (enemy.isUnconscious())
                        enemiesToDelete.add(enemy);
                }
            }
        }

        for (Projectile projectile : projectilesToDelete)
            projectiles.remove(projectile);
        for (Enemy enemy : enemiesToDelete)
            enemies.remove(enemy);
    }

    @Override
    protected void update(float delta) {
        if (paused)
            time.addTimePaused(delta);

        handleInput(delta);

        if (paused)
            return;

        if (enemies.isEmpty())
            waveManager.forceNextWave();
        else
            waveManager.startNextWaveIfPossible();

        checkWinLossConditions();

        for (Projectile projectile : projectiles)
            projectile.update(delta);

        player.update(delta);
        camera.update(delta, player.getPosition());

        for (Enemy enemy : enemies)
            enemy.update(delta, player.getPosition());

        resolveCollisions();
        hiddenEventManager.checkEventCollision(player);
        checkProjectileCollisions();
    }

    /**
     * Handle keyboard/mouse inputs.
     * @param delta Time passed since the last frame, in seconds.
     */
    private void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (paused)
                unpauseGame();
            else
                pauseGame();
        }

        if (paused && Gdx.input.justTouched()) {
            final Vector2 mousePosition = getMousePosition(uiViewport);

            if (resumeRect.contains(mousePosition))
                unpauseGame();
            else if (restartRect.contains(mousePosition))
                game.setScreen(new MainScreen(game));
            else if (mainMenuRect.contains(mousePosition))
                game.setScreen(new MainMenuScreen(game));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q))
            itemManager.moveSelectedUp();
        if (Gdx.input.isKeyJustPressed(Input.Keys.E))
            itemManager.moveSelectedDown();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            if (itemManager.useSelected())
                itemsUsed++;
    }

    @Override
    protected void draw() {
        ScreenUtils.clear(Color.PURPLE);

        game.batch.begin();
        drawWorld();
        drawUI();
        game.batch.end();

        hiddenEventManager.debugDrawEvents(camera.getCamera().combined);
        drawPauseScreen();
    }

    /**
     * Draw world objects.
     */
    private void drawWorld() {
        useWorldCoordinates();

        background.draw(game.batch);

        for (Enemy enemy : enemies)
            enemy.draw();

        for (Projectile projectile : projectiles)
            projectile.draw();

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
        final long timeElapsed = getTimeElapsed() / 1000;
        final long minutes = timeElapsed / 60;
        final long seconds = timeElapsed % 60;
        final String timeString = String.format("%02d:%02d", minutes, seconds);
        game.font.draw(game.batch, timeString, 5, 60);
    }

    /**
     * Draw the pause screen.
     */
    private void drawPauseScreen() {
        if (!paused)
            return;

        shapeRenderer.setProjectionMatrix(uiViewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(GameConstants.BACKGROUND_COLOR);
        shapeRenderer.rect(0, 0, uiViewport.getScreenWidth(), uiViewport.getScreenHeight());

        shapeRenderer.setColor(GameConstants.BUTTON_COLOR);
        resumeRect = getRectCentered(uiViewport, 0, 150, 400, 100);
        drawRectangle(shapeRenderer, resumeRect);

        shapeRenderer.setColor(GameConstants.BUTTON_COLOR);
        restartRect = getRectCentered(uiViewport, 0, 0, 400, 100);
        drawRectangle(shapeRenderer, restartRect);

        shapeRenderer.setColor(GameConstants.BUTTON_COLOR);
        mainMenuRect = getRectCentered(uiViewport, 0, -150, 400, 100);
        drawRectangle(shapeRenderer, mainMenuRect);

        shapeRenderer.end();

        game.batch.begin();

        game.drawTextCentered(uiViewport, "Resume", 0, 150);
        game.drawTextCentered(uiViewport, "Restart", 0, 0);
        game.drawTextCentered(uiViewport, "Return to Main Menu", 0, -150);

        game.batch.end();
    }

    private void drawEventsTriggered() {
        game.font.draw(game.batch, "Events triggered: " + hiddenEventManager.getEventsTriggered(), 5, 90);
    }

    private void drawItemsUsed() {
        game.font.draw(game.batch, "Items used: " + itemsUsed, 5, 120);
    }

    private void drawItems() {
        itemManager.draw(game.batch);
    }

    /**
     * Draw UI elements.
     */
    private void drawUI() {
        useScreenCoordinates();

        drawHealth();
        drawTimer();
        drawEventsTriggered();
        drawItemsUsed();
        drawItems();
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
