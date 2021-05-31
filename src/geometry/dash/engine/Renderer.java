package geometry.dash.engine;

import geometry.dash.utils.Constants;

import java.awt.*;
import java.util.ArrayList;

public class Renderer {

    private Camera camera;
    private ArrayList<GameObject> gameObjects;

    public Renderer(Camera camera) {
        this.camera = camera;
        gameObjects = new ArrayList<>();
    }

    public void submit(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void render(Graphics2D graphics2D) {
        for (GameObject gameObject : gameObjects) {
            Transform transform = gameObject.getTransform();
            transform.moveXAsic(-camera.position.x);
            transform.moveYAsic(-camera.position.y);

            gameObject.visible = transform.getPosition().x >= -100 && transform.getPosition().x <= Constants.SCREEN_WIDTH &&
                    transform.getPosition().y >= 0 && transform.getPosition().y <= Constants.SCREEN_HEIGHT;

            gameObject.draw(graphics2D);
            transform.moveXAsic(camera.position.x);
            transform.moveYAsic(camera.position.y);
        }
    }
}
