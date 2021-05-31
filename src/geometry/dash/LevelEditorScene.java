package geometry.dash;

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
        supportComponents.addComponent( new Grid(camera));
        supportComponents.addComponent(ground);
        supportComponents.addComponent(new CameraController(camera, Window.getWindow().getMouseDetector()));
        supportComponents.addComponent(new BlockBuilder(camera, Window.getWindow().getMouseDetector(), this));
        supportComponents.addComponent(new EditorPane(AssetPool.getSpriteSheet("assets\\blocks\\blocks.png")));
    }

    @Override
    public void update() {
        for (GameObject object : gameObjects)
            object.update();
        player.update();
        supportComponents.update();
        {
            if (Window.getWindow().getKeyDetector().isKeyPressed(KeyEvent.VK_ENTER)) {
                LevelRunScene scene = (LevelRunScene) SceneFactory.createScene(0);
                scene.init();
                scene.setLevelData(getLevelData());
                Window.getWindow().setScene(scene);

            }

            if (Window.getWindow().getKeyDetector().isKeyPressed(KeyEvent.VK_F1)) {
                serialize("test.lvl");
            }
            if (Window.getWindow().getKeyDetector().isKeyPressed(KeyEvent.VK_F2)) {
                deserialize("test.lvl");
            }
        }
    }


    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.blue);
        graphics2D.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        renderer.render(graphics2D);
        supportComponents.draw(graphics2D);
    }

    public LevelData getLevelData() {
        return new LevelData(gameObjects, supportComponents.getComponent(BlockBuilder.class).getBlockedPositions());
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
            supportComponents.getComponent(BlockBuilder.class).setBlockedPositions(levelData.positions);
        } catch (IOException | ClassNotFoundException ex) {
            System.exit(0);
        }
    }
}
