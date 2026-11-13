package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class ItemManager {
    private final ArrayList<Item> items = new ArrayList<>();
    private int selected = 0;
    private final Texture selectBox = new Texture("selectBox.png");

    /**
     * Draw the inventory.
     * @param batch The batch to use to draw.
     */
    public void draw(SpriteBatch batch) {
        for (int i = 0; i < items.size(); i++) {
            final Item item = items.get(i);
            batch.draw(item.texture, 5, 800 - 75 * i, 50, 50);

            if (i == selected)
                batch.draw(selectBox, 5, 800 - 75 * i, 50, 50);
        }
    }

    /**
     * Use the currently selected item.
     */
    public void useSelected() {
        if (items.isEmpty())
            return;

        items.get(selected).useIfPossible();
    }

    /**
     * Move the selection up, wrapping around if at the end.
     */
    public void moveSelectedUp() {
        selected--;
        if (selected < 0)
            selected = items.size() - 1;
    }

    /**
     * Move the selection down, wrapping around if at the end.
     */
    public void moveSelectedDown() {
        selected++;
        if (selected > items.size() - 1)
            selected = 0;
    }

    /**
     * Add an item to the item list.
     * @param item The item to add.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Get the ItemManager used by the game.
     * @param time The game's GameTime object.
     * @return The game's ItemManager.
     */
    public static ItemManager getGameItemManager(GameTime time, Viewport viewport, Player player, ArrayList<Projectile> projectiles, SpriteBatch batch) {
        ItemManager itemManager = new ItemManager();

        itemManager.addItem(new BreadItem(time, viewport, player, projectiles, batch));
        itemManager.addItem(new BandageItem(player));

        return itemManager;
    }
}
