package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Texture;

public abstract class LimitedItem extends Item {
    private int remainingUses;

    public LimitedItem(Texture texture, int initialUses) {
        super(texture);
        remainingUses = initialUses;
    }

    @Override
    public boolean useIfPossible() {
        if (remainingUses == 0)
            return false;

        use();

        remainingUses--;
        return true;
    }

    /**
     * Get the number of remaining uses this item has.
     * @return The number of remaining uses. 0 means the item cannot be used unless more uses are added.
     */
    public int getRemainingUses() {
        return remainingUses;
    }

    /**
     * Add more uses to the item.
     * @param amount The amount of uses to add to the item.
     */
    public void addUses(int amount) {
        remainingUses += amount;
    }

    /**
     * Actually perform the action for this item. The useIfPossible() method handles limited uses for you.
     */
    protected abstract void use();
}
