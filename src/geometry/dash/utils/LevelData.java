package geometry.dash.utils;

import geometry.dash.engine.GameObject;

import java.io.Serializable;
import java.util.ArrayList;

public class LevelData implements Serializable {

    public final ArrayList<GameObject> gameObjects;
    public final ArrayList<Vector> positions;

    public LevelData(ArrayList<GameObject> gameObjects, ArrayList<Vector> positions) {
        this.gameObjects = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject.isSerializable())
                this.gameObjects.add(gameObject);
        }
        this.positions = positions;
    }
}
