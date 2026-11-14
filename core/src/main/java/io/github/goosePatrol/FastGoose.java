package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class FastGoose extends Enemy {
    public FastGoose(Batch batch, Vector2 position) {
        super(10, 3, new Sprite(new Texture("goose.png")), batch);
        sprite.setSize(0.5f, 0.5f);
        sprite.setColor(Color.CYAN);
        setPosition(position.x, position.y);
    }

    @Override
    public void update(float delta, Vector2 playerPosition) {

        deltaPosition(playerPosition.cpy().sub(getPosition()).nor().scl(delta * 8));
    }
}
