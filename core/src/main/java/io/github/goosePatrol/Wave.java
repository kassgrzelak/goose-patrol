package io.github.goosePatrol;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

/**
 * A class encapsulating a single wave of enemies. A wave has a list of the enemies it contains as well as a duration
 * after which the next wave will spawn in.
 */
public class Wave {
    private final ArrayList<Enemy> enemies;
    private final long durationMillis;
    private final GameTime time;
    private long endTime = -999999;

    public Wave(ArrayList<Enemy> enemies, GameTime time, long durationMillis) {
        this.enemies = enemies;
        this.time = time;
        this.durationMillis = durationMillis;
    }

    /**
     * Add all the wave's enemies to the given enemy array and set the wave's end time. This should only be called once.
     * @param enemies The enemy array to add the wave's enemies to.
     */
    public void startWave(ArrayList<Enemy> enemies) {
        enemies.addAll(this.enemies);
        endTime = time.getTimeMillis() + durationMillis;
    }

    /**
     * Get whether the wave has finished or not.
     * @return true if the wave's end time has passed, false otherwise.
     */
    public boolean isFinished() {
        return time.getTimeMillis() >= endTime;
    }
}
