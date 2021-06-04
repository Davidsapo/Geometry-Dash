package geometry.dash.engine;

import geometry.dash.utils.Constants;

import java.awt.*;
import java.util.ArrayList;

public class Renderer {

    private Camera camera;
    private GameObject player;
    private ArrayList<GameObject> gameObjects;

    public Renderer(Camera camera) {
        this.camera = camera;
        gameObjects = new ArrayList<>();
    }

    public void submit(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void render(Graphics2D graphics2D) {
        Transform transform;

        transform = player.getTransform();
        transform.moveXAsic(-camera.position.x);
        transform.moveYAsic(-camera.position.y);

        player.visible = transform.getPosition().x >= -100 && transform.getPosition().x <= Constants.SCREEN_WIDTH &&
                transform.getPosition().y >= 0 && transform.getPosition().y <= Constants.SCREEN_HEIGHT;

        player.draw(graphics2D);
        transform.moveXAsic(camera.position.x);
        transform.moveYAsic(camera.position.y);

        for (GameObject gameObject : gameObjects) {
            transform = gameObject.getTransform();
            transform.moveXAsic(-camera.position.x);
            transform.moveYAsic(-camera.position.y);

            gameObject.visible = transform.getPosition().x >= -100 && transform.getPosition().x <= Constants.SCREEN_WIDTH &&
                    transform.getPosition().y >= 0 && transform.getPosition().y <= Constants.SCREEN_HEIGHT;

            gameObject.draw(graphics2D);
            transform.moveXAsic(camera.position.x);
            transform.moveYAsic(camera.position.y);
        }

    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setPlayer(GameObject player) {
        this.player = player;
    }
}
