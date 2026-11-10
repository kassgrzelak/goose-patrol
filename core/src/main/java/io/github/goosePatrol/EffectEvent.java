package io.github.goosePatrol;

import com.badlogic.gdx.math.Vector2;

/**
 * A class for a hidden event that applies an effect to the player.
 */
public class EffectEvent extends HiddenEvent {
    private final Player player;
    private final Effect effect;

    public EffectEvent(Player player, Effect effect, Vector2 position, float radius) {
        super(position, radius);
        this.player = player;
        this.effect = effect;
    }

    @Override
    protected void onTrigger() {
        player.addEffect(effect);
    }
}
