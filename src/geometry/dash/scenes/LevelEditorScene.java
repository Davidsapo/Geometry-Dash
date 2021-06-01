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

    public LevelEditorScene(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();
        player.getTransform().getPosition().x = Constants.CAMERA_OFFSET_X;
        firstLayerComponents.addComponent(new CameraController(camera, geometry.dash.Window.getWindow().getMouseDetector()));
        firstLayerComponents.addComponent(new Grid(camera).initLayer(2));
        thirdLayerComponents.addComponent(new BlockBuilder(camera, geometry.dash.Window.getWindow().getMouseDetector(), this).initLayer(2));
        thirdLayerComponents.addComponent(new EditorPane(AssetPool.getSpriteSheet("assets\\blocks\\blocks.png")).initLayer(1));
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

            if (geometry.dash.Window.getWindow().getKeyDetector().isKeyPressed(KeyEvent.VK_F1)) {
                serialize("test.lvl");
            }
            if (Window.getWindow().getKeyDetector().isKeyPressed(KeyEvent.VK_F2)) {
                deserialize("test.lvl");
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
        return new LevelData(gameObjects, thirdLayerComponents.getComponent(BlockBuilder.class).getBlockedPositions());
    }

    public void serialize(String fileName) {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            stream.writeObject(getLevelData());
        } catch (IOException ex) {
            System.exit(0);
        }

    }

    public void deserialize(String fileName) {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(fileName))) {
            LevelData levelData = (LevelData) (stream.readObject());
            for (GameObject object : levelData.gameObjects)
                addGameObject(object);
            thirdLayerComponents.getComponent(BlockBuilder.class).setBlockedPositions(levelData.positions);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }
}