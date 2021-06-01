package geometry.dash.scenes;

import static geometry.dash.utils.Constants.*;

import geometry.dash.Window;
import geometry.dash.components.Ground;
import geometry.dash.components.ParallaxBackground;
import geometry.dash.components.Player;
import geometry.dash.components.SpriteSheet;
import geometry.dash.engine.*;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.engine.Vector;

import java.awt.*;
import java.util.ArrayList;

public abstract class LevelScene implements Scene {

    protected String name;
    protected Camera camera;
    protected GameObject player;
    protected GameObject firstLayerComponents;
    protected Renderer secondLayerRender;
    protected GameObject thirdLayerComponents;
    protected ArrayList<GameObject> gameObjects;

    public LevelScene(String name) {
        this.name = name;
        camera = new Camera(new Vector());
        secondLayerRender = new Renderer(camera);
        gameObjects = new ArrayList<>();
        player = new GameObject("Player", new Transform(new Vector(PLAYER_SPAWN_X, PLAYER_SPAWN_Y), PLAYER_WIDTH, PLAYER_HEIGHT));
        firstLayerComponents = new GameObject("First layer", null);
        thirdLayerComponents = new GameObject("SecondLayer", null);
    }

    public void init() {
        SpriteSheet spriteSheet = AssetPool.getSpriteSheet("assets\\player\\playerSkins2.png");
        player.addComponent(new Player(spriteSheet.getTilePath(2), Color.GREEN, Window.getWindow().getKeyDetector(), false));
        player.setNonSerializable();
        secondLayerRender.submit(player);

        firstLayerComponents.addComponent(new ParallaxBackground("assets/background/background.png", camera, new Color(44, 61, 245), false).initLayer(1));
        firstLayerComponents.addComponent(new Ground("assets/ground/ground01.png", camera, new Color(3, 18, 192)).initLayer(3));
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        secondLayerRender.submit(gameObject);
    }

    public Camera getCamera() {
        return camera;
    }

    public GameObject getPlayer() {
        return player;
    }
}
