package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class BreadItem extends CooldownItem {
    private final Player player;
    private final ArrayList<Projectile> projectiles;
    private final Viewport viewport;
    private final Texture projectileTexture;

    public BreadItem(GameTime time, Viewport viewport, Player player, ArrayList<Projectile> projectiles) {
        super(new Texture("bread.png"), time, 500);
        this.viewport = viewport;
        this.player = player;
        this.projectiles = projectiles;
        projectileTexture = new Texture("bread.png");
    }

    @Override
    protected void use() {
        final Vector2 mousePosition = GameScreen.getMousePosition(viewport);
        final Vector2 projectileDir = mousePosition.cpy().sub(player.getPosition()).nor();
        final Vector2 velocity = projectileDir.cpy().scl(3);

        projectiles.add(new Projectile(projectileTexture, 0.5f, player.getPosition(), velocity, 2));
    }
}
