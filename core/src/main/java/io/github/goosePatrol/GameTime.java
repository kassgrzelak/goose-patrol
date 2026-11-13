package io.github.goosePatrol;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * Class that keeps track of game time, accounting for time spent paused.
 */
public class GameTime {
    private float timeSpentPaused = 0;

    /**
     * Get the current game time in milliseconds.
     * @return The current time.
     */
    public long getTimeMillis() {
        return TimeUtils.millis() - (long)(timeSpentPaused * 1000);
    }

    /**
     * Add time spent paused.
     * @param time The time spent paused in seconds.
     */
    public void addTimePaused(float time) {
        timeSpentPaused += time;
    }
}
