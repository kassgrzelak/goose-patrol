package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Texture;

public class BandageItem extends LimitedItem {
    private final Player player;

    public BandageItem(Player player)
    {
        super(new Texture("bandage.png"), 3);
        this.player = player;
    }

    @Override
    protected void use()
    {
        player.addHealth(10);
    }
}
