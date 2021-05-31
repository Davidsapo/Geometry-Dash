package geometry.dash.components;

import geometry.dash.engine.Component;

import static geometry.dash.utils.CollisionDetectors.*;

import geometry.dash.utils.CollisionDetectors;
import geometry.dash.utils.Constants;
import geometry.dash.utils.Vector;

public class BoxBounds extends Bounds {

    public Vector centre;
    public double halfWidth;
    public double halfHeight;

    public BoxBounds(int width, int height) {
        super(width, height);
        halfWidth = width / 2.0;
        halfHeight = height / 2.0;
        centre = new Vector();
    }

    @Override
    public CollisionDetectors getCollision(Bounds bounds) {
        if (bounds instanceof BoxBounds) {
            BoxBounds boxBounds = (BoxBounds) (bounds);
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
                int overlapY = (int) (distanceToCollideY - ABSRealYDistance);
                if (overlapX >= overlapY) {
                    if (realYDistance < 0)
                        return TOP_COLLISION;
                    else {
                        System.out.println("Button");
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
        }
        return NO_COLLISION;
    }

    private void calculateCenter() {
        centre.x = gameObject.getTransform().getPosition().x + halfWidth;
        centre.y = gameObject.getTransform().getPosition().y + halfHeight;
    }

    @Override
    public Component copy() {
        return new BoxBounds(width, height);
    }
}
