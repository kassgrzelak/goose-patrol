package io.github.goosePatrol;

/**
 * Interface representing a basic item.
 */
public interface Item {
    /**
     * Perform whatever action the Item does, only if whatever internal value (cooldown timer, use count, etc.) is
     * valid. If not, this method does nothing.
     */
    void useIfPossible();
}
