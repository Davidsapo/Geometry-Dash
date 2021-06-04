package geometry.dash.components;

import geometry.dash.engine.Component;
import geometry.dash.engine.Vector;
import geometry.dash.utils.CollisionDetectors;
import geometry.dash.utils.Constants;

import static geometry.dash.utils.CollisionDetectors.*;
import static geometry.dash.utils.CollisionDetectors.NO_COLLISION;

public class PortalBounds extends Bounds{

    public Vector centre;
    public double halfWidth;
    public double halfHeight;

    public PortalBounds(int width, int height) {
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

        if (ABSRealXDistance  <= distanceToCollideX && ABSRealYDistance <= distanceToCollideY && realXDistance<0) {
            return PORTAL;
        }

        return NO_COLLISION;
    }

    public void calculateCenter() {
        centre.x = gameObject.getTransform().getPosition().x + width + halfWidth;
        centre.y = gameObject.getTransform().getPosition().y + halfHeight;
    }

    @Override
    public Component copy() {
        return new PortalBounds(width, height);
    }
}
