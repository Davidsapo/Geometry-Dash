package geometry.dash.components;

import geometry.dash.engine.Component;

import static geometry.dash.utils.CollisionDetectors.*;

import geometry.dash.utils.CollisionDetectors;
import geometry.dash.utils.Constants;
import geometry.dash.engine.Vector;

import java.awt.*;

public class BoxBounds extends Bounds {

    public Vector centre;
    public double halfWidth;
    public double halfHeight;

    boolean ship = false;

    public BoxBounds(int width, int height) {
        super(width, height);
        halfWidth = width / 2.0;
        halfHeight = height / 2.0;
        centre = new Vector();
    }

    @Override
    void init() {

    }

    @Override
    public CollisionDetectors getCollision(BoxBounds boxBounds) {
        realPos = gameObject.getTransform().getPosition().y;
        calculateCenter();
        boxBounds.calculateCenter();

        double distanceToCollideX = boxBounds.halfWidth + halfWidth;
        double distanceToCollideY = boxBounds.halfHeight + halfHeight;

        double realXDistance = boxBounds.centre.x - centre.x;
        double realYDistance = boxBounds.centre.y - centre.y;

        double ABSRealXDistance = Math.abs(realXDistance);
        double ABSRealYDistance = Math.abs(realYDistance);

        if (ABSRealXDistance <= distanceToCollideX && ABSRealYDistance <= distanceToCollideY) {
            int overlapX = (int) (distanceToCollideX - ABSRealXDistance);
            int overlapY = (int) (distanceToCollideY - ABSRealYDistance) - 10;
            if (overlapX >= overlapY) {
                if (realYDistance < 0)
                    return TOP_COLLISION;
                else {
                    return BUTTON_COLLISION;
                }
            } else {
                if (realXDistance > 0) {
                    return RIGHT_COLLISION;
                } else {
                    if (overlapY < Constants.OVERLAP_POSSIBLE) {
                        System.out.println("TYDA SUDA");
                        return TOP_COLLISION;
                    }
                    return LEFT_COLLISION;
                }
            }
        }

        return NO_COLLISION;
    }

    public void calculateCenter() {
        centre.x = gameObject.getTransform().getPosition().x + halfWidth;
        centre.y = gameObject.getTransform().getPosition().y + halfHeight;
        if (ship) {
            centre.y = gameObject.getTransform().getPosition().y + 30;
        }

    }

    @Override
    public Component copy() {
        return new BoxBounds(width, height);
    }

    public boolean containPoint(Point point) {
        double x1 = gameObject.getTransform().getPosition().x;
        double x2 = x1 + width;
        double y1 = gameObject.getTransform().getPosition().y;
        double y2 = y1 + height;
        return point.x >= x1 && point.x <= x2 && point.y >= y1 && point.y <= y2;
    }
}
