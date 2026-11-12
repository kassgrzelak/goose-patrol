package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Base class for enemies. Implementors must override update(), which determines how the enemy behaves in-game.
 */
public abstract class Enemy extends SpriteEntity {
    private static Texture healthBarTexture;

    private final int maxHealth;
    private int health;
    private final int damage;

    Enemy(int health, int damage, Sprite sprite, Batch batch) {
        super(sprite, batch);
        this.maxHealth = health;
        this.health = health;
        this.damage = damage;
    }

    private static Texture getHealthBarTexture() {
        if (healthBarTexture == null) {
            // Pixmap allows creation of simple textures at runtime.
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.GREEN);
            pixmap.fill();
            healthBarTexture = new Texture(pixmap);
            pixmap.dispose();
        }
        return healthBarTexture;
    }

    /**
     * Get the current health of this enemy.
     * @return The enemy's health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get the maximum health of this enemy.
     * @return The enemy's maximum health.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Get the damage this enemy does to the player.
     * @return The damage this enemy does to the player.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Get whether the enemy is unconscious or not.
     * @return true if the enemy's health is <= 0, false otherwise.
     */
    public boolean isUnconscious() {
        return health <= 0;
    }

    /**
     * Make the enemy take damage.
     * @param amount The amount of damage to take.
     */
    public void takeDamage(int amount) {
        health -= amount;
    }

    /**
     * Draw the enemy and its health bar.
     */
    @Override
    public void draw() {
        super.draw();
        drawHealthBar();
    }

    /**
     * Draw the green health bar above the enemy.
     */
    protected void drawHealthBar() {
        if (health <= 0 || maxHealth <= 0)
            return;

        final float healthFraction = (float) health / maxHealth;
        final float barWidth = sprite.getWidth() * Math.min(1.0f, Math.max(0.0f, healthFraction));
        final float barHeight = 0.1f * sprite.getHeight();
        final float barX = sprite.getX();
        final float barY = sprite.getY() + sprite.getHeight() + 0.1f * sprite.getHeight();

        batch.draw(getHealthBarTexture(), barX, barY, barWidth, barHeight);
    }

    /**
     * Update the enemy. This can be overridden to give different enemies different behaviors.
     * @param delta The time passed since the last frame, in seconds.
     * @param playerPosition The current position of the player in world space.
     */
    public abstract void update(float delta, Vector2 playerPosition);
}
