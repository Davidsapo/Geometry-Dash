package geometry.dash.engine;

import java.awt.*;
import java.io.Serializable;

public abstract class Component implements Serializable {

    protected GameObject gameObject;

    public void update() {
    }

    public void draw(Graphics2D graphics2D) {
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
