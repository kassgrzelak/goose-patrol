package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Basic goose enemy.
 */
public class BasicGoose extends Enemy {
    public BasicGoose(Batch batch, Vector2 position) {
        super(3, new Sprite(new Texture("basic-goose.png")), batch);
        sprite.setSize(1, 1);
        setPosition(position.x, position.y);
    }

    @Override
    public void update(float delta, Vector2 playerPosition) {
        deltaPosition(playerPosition.cpy().sub(getPosition()).nor().scl(delta * 2));
    }
}
