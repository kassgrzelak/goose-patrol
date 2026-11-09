package io.github.goosePatrol;

/**
 * A class for a hidden event that applies an effect to the player.
 */
public class EffectEvent extends HiddenEvent {
    private final Player player;
    private final Effect effect;

    public EffectEvent(Player player, Effect effect, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.player = player;
        this.effect = effect;
    }

    @Override
    protected void onTrigger() {
        player.addEffect(effect);
    }
}
