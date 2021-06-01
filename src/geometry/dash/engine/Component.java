package geometry.dash.engine;

import java.awt.*;
import java.io.Serializable;

public abstract class Component implements Serializable, Comparable<Component> {

    protected GameObject gameObject;
    protected int layer = 0;

    public Component() {
    }

    public Component(int layer) {
        this.layer = layer;
    }

    public void update() {
    }

    public void draw(Graphics2D graphics2D) {
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public int compareTo(Component o) {
        return layer - o.layer;
    }

    public Component initLayer(int layer) {
        this.layer = layer;
        return this;
    }
}
