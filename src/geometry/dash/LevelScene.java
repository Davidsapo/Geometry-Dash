package geometry.dash;

import static geometry.dash.utils.Constants.*;

import geometry.dash.components.Ground;
import geometry.dash.components.ParallaxBackground;
import geometry.dash.components.Player;
import geometry.dash.components.SpriteSheet;
import geometry.dash.engine.*;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.Vector;

import java.awt.*;
import java.util.ArrayList;

public abstract class LevelScene implements Scene {

    protected String name;
    protected Camera camera;
    protected Ground ground;
    protected ParallaxBackground parallaxBackground;
    protected GameObject player;
    protected Renderer renderer;
    protected GameObject supportComponents;
    protected ArrayList<GameObject> gameObjects;

    public LevelScene(String name) {
        this.name = name;
        camera = new Camera(new Vector());
        renderer = new Renderer(camera);
        gameObjects = new ArrayList<>();
        player = new GameObject("Player", new Transform(new Vector(PLAYER_SPAWN_X, PLAYER_SPAWN_Y), PLAYER_WIDTH, PLAYER_HEIGHT));
        supportComponents = new GameObject("Support object", null);
        ground = new Ground("assets/ground/ground01.png", camera, new Color(3, 18, 192));
        parallaxBackground = new ParallaxBackground("assets/background/background.png", camera, new Color(44, 61, 245), false);
    }

    public void init() {
        SpriteSheet spriteSheet = AssetPool.getSpriteSheet("assets\\player\\playerSkins2.png");
        player.addComponent(new Player(spriteSheet.getTilePath(2), Color.GREEN, Window.getWindow().getKeyDetector(), false));
        player.setNonSerializable();
        renderer.submit(player);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        renderer.submit(gameObject);
    }

    public Camera getCamera() {
        return camera;
    }

    public GameObject getPlayer() {
        return player;
    }
}
