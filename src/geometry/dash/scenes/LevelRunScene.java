package geometry.dash.scenes;

import geometry.dash.Window;
import geometry.dash.components.*;
import geometry.dash.engine.GameObject;
import geometry.dash.engine.Scene;
import geometry.dash.utils.LevelData;
import geometry.dash.engine.Vector;

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
        player.addComponent(new RigidBody(new Vector(PLAYER_SPEED, 0)));
        player.addComponent(new BoxBounds(PLAYER_WIDTH, PLAYER_HEIGHT));
        playerSupportComponent  = player.getComponent(Player.class);
        firstLayerComponents.getComponent(ParallaxBackground.class).movable = true;
        player.hasCollision = true;
        playerSupportComponent.active = true;

    }

    @Override
    public void update() {
        playerSupportComponent.onGround = false;
        playerSupportComponent.jumpAvailable = false;
        firstLayerComponents.update();
        for (GameObject object : gameObjects) {
            object.update();
            playerSupportComponent.resolveCollision(object);
        }
        player.update();
        thirdLayerComponents.update();

        if (player.getTransform().getPosition().x - camera.position.x > CAMERA_OFFSET_X)
            camera.position.x = player.getTransform().getPosition().x - CAMERA_OFFSET_X;
        {
        if (geometry.dash.Window.getWindow().getKeyDetector().isKeyPressed(KeyEvent.VK_SHIFT)) {
            Scene scene = SceneFactory.createScene(1);
            scene.init();
            Window.getWindow().setScene(scene);
        }
        }
    }


    @Override
    public void draw(Graphics2D graphics2D) {
        firstLayerComponents.draw(graphics2D);
        secondLayerRender.render(graphics2D);
        thirdLayerComponents.draw(graphics2D);
    }

    public void setLevelData(LevelData levelData) {
        for (GameObject object : levelData.gameObjects)
            addGameObject(object);

        playerSupportComponent.setPlayerImagePath(levelData.playerImage);
        playerSupportComponent.setShipImagePath(levelData.shipImage);
        firstLayerComponents.getComponent(ParallaxBackground.class).setBackgroundImage(levelData.backgroundImage);
        firstLayerComponents.getComponent(Ground.class).setGroundImagePath(levelData.groundImage);
        firstLayerComponents.getComponent(ParallaxBackground.class).setColor(levelData.backgroundColor);
        firstLayerComponents.getComponent(Ground.class).setGroundColor(levelData.groundColor);
    }
}
