package geometry.dash.components;

import geometry.dash.engine.Component;
import geometry.dash.utils.CollisionDetectors;

public abstract class Bounds extends Component {

    public int width;
    public int height;

    public Bounds(int width, int height) {
        this.width = width;
        this.height = height;
    }


    abstract public CollisionDetectors getCollision(Bounds bounds);

    public abstract Component copy();
}
