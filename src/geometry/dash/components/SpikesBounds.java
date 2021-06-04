package geometry.dash.components;

import geometry.dash.engine.Component;
import geometry.dash.engine.Vector;
import geometry.dash.utils.CollisionDetectors;
import geometry.dash.utils.Constants;

import java.awt.*;

import static geometry.dash.utils.CollisionDetectors.*;
import static geometry.dash.utils.CollisionDetectors.NO_COLLISION;

public class SpikesBounds extends Bounds {

    public Vector centre;
    public double halfWidth;
    public double halfHeight;

    public SpikesBounds(int width, int height) {
        super(width, height);
        halfWidth = width / 2.0;
        halfHeight = height / 2.0;
        centre = new Vector();
    }

    @Override
    void init() {
        gameObject.getTransform().rotatable = false;
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

        if (ABSRealXDistance <= distanceToCollideX && ABSRealYDistance <= distanceToCollideY - 10) {
            return DIE;
        }

        return NO_COLLISION;
    }

    public void calculateCenter() {
        centre.x = gameObject.getTransform().getPosition().x + halfWidth;
        centre.y = gameObject.getTransform().getPosition().y + halfHeight;
    }

    @Override
    public Component copy() {
        return new SpikesBounds(width, height);
    }


}
