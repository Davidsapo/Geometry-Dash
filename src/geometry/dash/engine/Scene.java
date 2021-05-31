package geometry.dash.engine;

import geometry.dash.utils.Vector;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Scene {

    private String name;
    protected Camera camera;
    protected Renderer renderer;
    protected ArrayList<GameObject> gameObjects;

    public Scene(String name) {
        this.name = name;
        camera = new Camera(new Vector());
        renderer = new Renderer(camera);
        gameObjects = new ArrayList<>();
    }

    public abstract void init();

    public abstract void update();

    public abstract void draw(Graphics2D graphics2D);

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        renderer.submit(gameObject);
    }

    public Camera getCamera() {
        return camera;
    }
}
