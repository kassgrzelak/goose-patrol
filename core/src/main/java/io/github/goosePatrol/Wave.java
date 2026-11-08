package io.github.goosePatrol;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Wave {
    private final int waveNumber;
    private final Array<Enemy> longbois;
    private float waveCountdown;
    private boolean completed;

    public Wave(int waveNumber,Array<Enemy> longbois, float waveCountdown){
        this.waveNumber = waveNumber;
        this.longbois = longbois;
        this.waveCountdown = waveCountdown;
        this.completed = false;
    }

public void update(float delta, Vector2 playerPosition){
        if (completed) {
            return;
        }
        waveCountdown -= delta;

        // checks if all the longbois have been killed
    if(longbois.size==0 || waveCountdown<=0){
        completed = true;
    }

}

    public boolean isCompleted() {
        return completed;
    }

    public Array<Enemy> getLongbois() {
        return longbois;
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public float getWaveCountdown() {
        return waveCountdown;
    }

}
