package io.github.goosePatrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class BreadItem extends CooldownItem {
    private final Player player;
    private final ArrayList<Projectile> projectiles;
    private final Viewport viewport;
    private final Texture projectileTexture;
    private final SpriteBatch batch;

    private static final long BREAD_COOLDOWN_LONG = 200;
    private static final float BREAD_SPEED = 7.0f;

    public BreadItem(GameTime time, Viewport viewport, Player player, ArrayList<Projectile> projectiles, SpriteBatch batch) {
        super(new Texture("bread.png"), time, BREAD_COOLDOWN_LONG);
        this.viewport = viewport;
        this.player = player;
        this.projectiles = projectiles;
        projectileTexture = new Texture("bread.png");
        this.batch = batch;
    }

    @Override
    protected void use() {
        final Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        final Vector2 projectileDir = mousePosition.cpy().sub(player.getPosition()).nor();
        final Vector2 velocity = projectileDir.cpy().scl(BREAD_SPEED);

        final Sprite sprite = new Sprite(projectileTexture);
        sprite.setSize(0.5f, 0.5f);

        projectiles.add(new Projectile(sprite, batch, player.getPosition(), velocity, 2));
    }
}
