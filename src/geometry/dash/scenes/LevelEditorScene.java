package geometry.dash.scenes;

import geometry.dash.Window;
import geometry.dash.components.*;
import geometry.dash.engine.GameObject;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.Constants;
import geometry.dash.utils.LevelData;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

public class LevelEditorScene extends LevelScene {

    private static LevelEditorScene scene;
    private boolean init = true;

    private LevelEditorScene(String name) {
        super(name);
    }

    public static LevelEditorScene getInstance() {
        if (scene == null) {
            scene = new LevelEditorScene("");
        }
        return scene;
    }

    @Override
    public void init() {
        System.out.println(init);
        if (init) {
            super.init();
            player.getTransform().getPosition().x = Constants.CAMERA_OFFSET_X;
            firstLayerComponents.addComponent(new CameraController(camera, Window.getWindow().getMouseDetector()));
            firstLayerComponents.addComponent(new Grid(camera).initLayer(2));
            thirdLayerComponents.addComponent(new BlockBuilder(camera, Window.getWindow().getMouseDetector(), this).initLayer(2));
            thirdLayerComponents.addComponent(editorPane);
        }
        init = false;
    }

    @Override
    public void update() {
        firstLayerComponents.update();
        for (GameObject object : gameObjects)
            object.update();
        player.update();
        thirdLayerComponents.update();
        {
            if (geometry.dash.Window.getWindow().getKeyDetector().isKeyPressed(KeyEvent.VK_ENTER)) {
                LevelRunScene scene = (LevelRunScene) SceneFactory.createScene(0);
                scene.init();
                scene.setLevelData(getLevelData());
                geometry.dash.Window.getWindow().setScene(scene);

            }
        }
    }


    @Override
    public void draw(Graphics2D graphics2D) {
        firstLayerComponents.draw(graphics2D);
        secondLayerRender.render(graphics2D);
        thirdLayerComponents.draw(graphics2D);
    }

    public LevelData getLevelData() {
        LevelData levelData = new LevelData();
        levelData.gameObjects = gameObjects;
        levelData.positions = thirdLayerComponents.getComponent(BlockBuilder.class).getBlockedPositions();
        levelData.playerImage = player.getComponent(Player.class).getPlayerImagePath();
        levelData.shipImage = player.getComponent(Player.class).getShipImagePath();
        levelData.backgroundImage = firstLayerComponents.getComponent(ParallaxBackground.class).getBackgroundImage();
        levelData.groundImage = firstLayerComponents.getComponent(Ground.class).getGroundImagePath();
        levelData.backgroundColor = firstLayerComponents.getComponent(ParallaxBackground.class).getColor();
        levelData.groundColor = firstLayerComponents.getComponent(Ground.class).getGroundColor();
        levelData.track = track;

        return levelData;
    }

}
