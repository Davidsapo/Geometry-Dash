package geometry.dash;

import geometry.dash.components.*;
import geometry.dash.engine.GameObject;
import geometry.dash.engine.Scene;
import geometry.dash.engine.Transform;
import geometry.dash.utils.Constants;
import geometry.dash.utils.LevelData;
import geometry.dash.utils.Vector;

import static geometry.dash.utils.Constants.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LevelRunScene extends LevelScene {

    private Player playerSupportComponent;

    public LevelRunScene(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();
        supportComponents.addComponent(ground);
        supportComponents.addComponent(parallaxBackground);
        player.addComponent(new RigidBody(new Vector(PLAYER_SPEED, 0)));
        player.addComponent(new BoxBounds(PLAYER_WIDTH, PLAYER_HEIGHT));
        playerSupportComponent  = player.getComponent(Player.class);
        parallaxBackground.movable = true;
        player.hasCollision = true;
        playerSupportComponent.active = true;
    }

    @Override
    public void update() {
        playerSupportComponent.onGround = false;
        for (GameObject object : gameObjects) {
            object.update();
            playerSupportComponent.resolveCollision(object);
        }
        supportComponents.update();
        player.update();

        if (player.getTransform().getPosition().x - camera.position.x > CAMERA_OFFSET_X)
            camera.position.x = player.getTransform().getPosition().x - CAMERA_OFFSET_X;
        {
        if (Window.getWindow().getKeyDetector().isKeyPressed(KeyEvent.VK_SHIFT)) {
            Scene scene = SceneFactory.createScene(1);
            scene.init();
            Window.getWindow().setScene(scene);
        }
        }
    }


    @Override
    public void draw(Graphics2D graphics2D) {
        supportComponents.draw(graphics2D);
        renderer.render(graphics2D);
    }

    public void setLevelData(LevelData levelData) {
        for (GameObject object : levelData.gameObjects)
            addGameObject(object);
    }
}
