package io.github.goosePatrol;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

import static io.github.goosePatrol.GameConstants.WORLD_HEIGHT;
import static io.github.goosePatrol.GameConstants.WORLD_WIDTH;

/**
 * Class for the camera which follows the player.
 */
public class GameCamera {
    private final Camera camera;

    private final Vector2 velocity;

    private static final float CAMERA_SPEED_MULTIPLIER = 10;
    private static final float CAMERA_ACCEL = 30;

    public GameCamera(Camera camera) {
        this.camera = camera;
        velocity = new Vector2(0, 0);
    }

    /**
     * Get the LibGDX Camera object associated with this GameCamera.
     * @return The LibGDX Camera for this GameCamera.
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Update the camera.
     * @param delta Time passed since the last frame, in seconds.
     * @param playerPosition The position of the player in world space.
     */
    public void update(float delta, Vector2 playerPosition) {
        // Camera moves faster towards the player depending on distance. The effect is a smooth following of the player
        // rather than a rigid locking.
        final Vector2 targetVel = playerPosition.cpy()
                .sub(camera.position.x, camera.position.y)
                .scl(CAMERA_SPEED_MULTIPLIER);

        velocity.lerp(targetVel, delta * CAMERA_ACCEL);
        camera.position.add(velocity.x * delta, velocity.y * delta, 0);

        // Keep the camera within the world bounds.
        if (camera.position.x < -WORLD_WIDTH / 2 + camera.viewportWidth / 2)
            camera.position.x = -WORLD_WIDTH / 2 + camera.viewportWidth / 2;
        if (camera.position.x > WORLD_WIDTH / 2 - camera.viewportWidth / 2)
            camera.position.x = WORLD_WIDTH / 2 - camera.viewportWidth / 2;
        if (camera.position.y < -WORLD_HEIGHT / 2 + camera.viewportHeight / 2)
            camera.position.y = -WORLD_HEIGHT / 2 + camera.viewportHeight / 2;
        if (camera.position.y > WORLD_HEIGHT / 2 - camera.viewportHeight / 2)
            camera.position.y = WORLD_HEIGHT / 2 - camera.viewportHeight / 2;

        camera.update();
    }
}
