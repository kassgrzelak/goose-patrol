package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Texture;

/**
 * Abstract class representing a basic item.
 */
public abstract class Item {
    public final Texture texture;

    public Item(Texture texture) {
        this.texture = texture;
    }

    /**
     * Perform whatever action the Item does only if whatever internal value (cooldown timer, use count, etc.) is
     * valid. If not, this method does nothing.
     * @return true if the item was used successfully, false otherwise.
     */
    public abstract boolean useIfPossible();
}
