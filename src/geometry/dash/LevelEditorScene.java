package geometry.dash;

import geometry.dash.components.*;
import geometry.dash.engine.GameObject;
import geometry.dash.engine.Scene;
import geometry.dash.engine.Transform;
import geometry.dash.engine.Window;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.Constants;
import geometry.dash.utils.LevelData;
import geometry.dash.utils.Vector;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

import static geometry.dash.utils.Constants.*;
import static geometry.dash.utils.Constants.CAMERA_OFFSET_X;

public class LevelEditorScene extends Scene {

    private GameObject player;
    private Grid grid;
    private GameObject supportComponents;

    public LevelEditorScene(String name) {
        super(name);
    }

    @Override
    public void init() {
        player = new GameObject("Player", new Transform(new Vector(CAMERA_OFFSET_X, SCREEN_HEIGHT - GROUND_HEIGHT - PLAYER_HEIGHT), PLAYER_WIDTH, PLAYER_HEIGHT));
        SpriteSheet spriteSheet = AssetPool.getSpriteSheet("assets\\player\\playerSkins2.png");
        player.addComponent(new Player(spriteSheet.getTilePath(2), Color.GREEN, Window.getWindow().getKeyDetector(),false));
        //player.addComponent(new RigidBody(new Vector(1, 0)));
        player.setNonSerializable();
        addGameObject(player);

        grid = new Grid(camera);

        supportComponents = new GameObject("Support components", null);
        supportComponents.addComponent(new Ground("assets/ground/ground01.png", camera, new Color(0, 0, 170)));
        supportComponents.addComponent(new CameraController(camera, Window.getWindow().getMouseDetector()));
        supportComponents.addComponent(new BlockBuilder(camera, Window.getWindow().getMouseDetector()));
        supportComponents.addComponent(new EditorPane(AssetPool.getSpriteSheet("assets\\blocks\\blocks.png")));
    }

    @Override
    public void update() {
        for (GameObject object : gameObjects)
            object.update();
        supportComponents.update();

        /*if (player.getTransform().getPosition().x - camera.position.x != CAMERA_OFFSET_X)
            camera.position.x = player.getTransform().getPosition().x - CAMERA_OFFSET_X;*/


        if (Window.getWindow().getKeyDetector().isKeyPressed(KeyEvent.VK_ENTER)){
            LevelScene scene = (LevelScene)SceneFactory.createScene(0);
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


    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.blue);
        graphics2D.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        grid.draw(graphics2D);
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
