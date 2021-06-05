package geometry.dash.engine;

import geometry.dash.Window;
import geometry.dash.components.Bounds;
import geometry.dash.components.BoxBounds;
import geometry.dash.scenes.LevelScene;
import geometry.dash.utils.CollisionDetectors;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;

public class GameObject implements Serializable {

    private String name;
    public ArrayList<Component> components;
    private Transform transform;
    private boolean serializable = true;
    public boolean hasCollision;
    public boolean visible;

    public GameObject(String name, Transform transform) {
        this.name = name;
        this.transform = transform;
        components = new ArrayList<>();
    }

    public void update() {
        for (Component component : components)
            component.update();

    }

    public CollisionDetectors checkCollisionWith(GameObject object) {
        if (object.visible && object.hasCollision && visible && hasCollision && this != object)
            return getComponent(Bounds.class).getCollision(object.getComponent(BoxBounds.class));
        return CollisionDetectors.NO_COLLISION;
    }

    public void draw(Graphics2D graphics2D) {
        for (Component component : components) {
            component.draw(graphics2D);
        }
    }

    public void addComponent(Component component) {
        components.add(component);
        component.setGameObject(this);
        Collections.sort(components);
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        for (Component component : components) {
            if (clazz.isAssignableFrom(component.getClass()))
                return (T) component;
        }
        return null;
    }

    public void setNonSerializable() {
        serializable = false;
    }

    public boolean isSerializable() {
        return serializable;
    }

    public Transform getTransform() {
        return transform;
    }

    public void delete() {
        ((LevelScene) (Window.getWindow().getCurrentScene())).deleteObject(this);
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}
