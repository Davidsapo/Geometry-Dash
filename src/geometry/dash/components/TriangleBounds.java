package geometry.dash.components;

import geometry.dash.engine.Component;
import geometry.dash.engine.Vector;
import geometry.dash.utils.CollisionDetectors;
import geometry.dash.utils.GameElementRotationPosition;

import java.awt.*;

public class TriangleBounds extends Bounds {

    private Point[] points;


    public TriangleBounds(int width, int height) {
        super(width, height);
        points = new Point[3];
    }

    @Override
    void init() {
        GameElementRotationPosition position = gameObject.getTransform().rotationPosition;
        if (position == GameElementRotationPosition.BUTTON) {
            points[0] = new Point(width / 2, (50 - height));
            points[1] = new Point(0, 50);
            points[2] = new Point(50, 50);
        } else if (position == GameElementRotationPosition.TOP) {
            points[0] = new Point(width / 2, height);
            points[1] = new Point(0, 0);
            points[2] = new Point(50, 0);
        } else if (position == GameElementRotationPosition.LEFT) {
            points[0] = new Point(height, width / 2);
            points[1] = new Point(0, 0);
            points[2] = new Point(0, 50);
        } else if (position == GameElementRotationPosition.RIGHT) {
            points[0] = new Point(50 - height, width / 2);
            points[1] = new Point(50, 0);
            points[2] = new Point(50, 50);
        }
    }


    @Override
    public CollisionDetectors getCollision(BoxBounds boxBounds) {
        int x = (int) gameObject.getTransform().getPosition().x;
        int y = (int) gameObject.getTransform().getPosition().y;
        for (Point point : points) {
            point = new Point(point.x + x, point.y + y);
            if (boxBounds.containPoint(point))
                return CollisionDetectors.DIE;
        }
        return CollisionDetectors.NO_COLLISION;
    }


    @Override
    public Component copy() {
        return new TriangleBounds(width, height);
    }
}
