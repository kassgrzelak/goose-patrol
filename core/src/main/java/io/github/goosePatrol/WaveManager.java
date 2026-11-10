package io.github.goosePatrol;

import java.util.ArrayList;

/**
 * Manages waves of enemies and the process of adding them to the enemy list over the course of the game.
 */
public class WaveManager {
    private final ArrayList<Wave> waves;
    private final ArrayList<Enemy> enemies;

    public WaveManager(ArrayList<Wave> waves, ArrayList<Enemy> enemies) {
        this.waves = waves;
        this.enemies = enemies;

        waves.get(0).startWave(enemies);
    }

    /**
     * Starts the next wave if the current one has finished.
     */
    public void startNextWaveIfPossible() {
        if (waves.isEmpty())
            return;
        if (!waves.get(0).isFinished())
            return;

        waves.remove(0);

        if (waves.isEmpty())
            return;

        waves.get(0).startWave(enemies);
    }

    /**
     * Force the next wave to start immediately. For use when all the enemies have been cleared, so the player doesn't
     * have to wait around.
     */
    public void forceNextWave() {
        if (waves.isEmpty())
            return;

        waves.remove(0);

        if (waves.isEmpty())
            return;

        waves.get(0).startWave(enemies);
    }

    /**
     * Get whether all waves have been cleared or not. This does not mean the player has defeated all the enemies, just
     * that there are no waves left to come.
     * @return true if there are no waves left to come, false otherwise.
     */
    public boolean isFinished() {
        return waves.isEmpty();
    }
}
