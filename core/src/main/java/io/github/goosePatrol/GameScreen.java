package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private final Main game;

    private final OrthographicCamera camera;
    private final ScreenViewport uiViewport;

    private final Sprite backgroundSprite;

    public GameScreen(Main game) {
        this.game = game;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiViewport = new ScreenViewport();

        backgroundSprite = new Sprite(new Texture("map.jpg"));
        backgroundSprite.setPosition(-500, -500);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            camera.translate(-100f * delta, 0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            camera.translate(100f * delta, 0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camera.translate(0, -100f * delta, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            camera.translate(0, 100f * delta, 0);

        ScreenUtils.clear(Color.BLACK);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        backgroundSprite.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
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
        backgroundSprite.getTexture().dispose();
    }
}
