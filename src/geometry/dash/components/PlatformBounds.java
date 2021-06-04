package geometry.dash.components;

import geometry.dash.engine.Component;
import geometry.dash.utils.CollisionDetectors;
import geometry.dash.utils.Constants;
import geometry.dash.utils.GameElementRotationPosition;

import java.awt.*;

import static geometry.dash.utils.CollisionDetectors.*;
import static geometry.dash.utils.CollisionDetectors.NO_COLLISION;


public class PlatformBounds extends BoxBounds {

    private GameElementRotationPosition position;


    public PlatformBounds(int width, int height) {
        super(width, height);
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
            int overlapY = (int) (distanceToCollideY - ABSRealYDistance) -10;
            if (overlapX + 1>= overlapY) {
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

        return NO_COLLISION;
    }

    @Override
    public void calculateCenter() {
        if (position == GameElementRotationPosition.TOP) {
            centre.x = gameObject.getTransform().getPosition().x + halfWidth;
            centre.y = gameObject.getTransform().getPosition().y + 50 - 12;
            realPos = gameObject.getTransform().getPosition().y + 27;
        }
        else if (position == GameElementRotationPosition.BUTTON) {
            centre.x = gameObject.getTransform().getPosition().x + halfWidth;
            centre.y = gameObject.getTransform().getPosition().y + 12;
        }
        else if (position == GameElementRotationPosition.LEFT) {
            centre.x = gameObject.getTransform().getPosition().x + 50 - 12;
            centre.y = gameObject.getTransform().getPosition().y + halfHeight;
        }

        else if (position == GameElementRotationPosition.RIGHT) {
            centre.x = gameObject.getTransform().getPosition().x + 12;
            centre.y = gameObject.getTransform().getPosition().y + halfHeight;
        }
    }

    @Override
    void init() {
        position = gameObject.getTransform().rotationPosition;
        if (position == GameElementRotationPosition.TOP) {
            height = 23;
            width = 50;
            halfHeight = 12;
            halfWidth = 25;
            centre.x = gameObject.getTransform().getPosition().x + halfWidth;
            centre.y = gameObject.getTransform().getPosition().y + 50 - 12;
            realPos = gameObject.getTransform().getPosition().y + 27;
        }
        else if (position == GameElementRotationPosition.BUTTON) {
            height = 23;
            width = 50;
            halfHeight = 12;
            halfWidth = 25;
            centre.x = gameObject.getTransform().getPosition().x + halfWidth;
            centre.y = gameObject.getTransform().getPosition().y + 12;
        }
        else if (position == GameElementRotationPosition.LEFT) {
            height = 50;
            width = 23;
            halfHeight = 25;
            halfWidth = 12;
            centre.x = gameObject.getTransform().getPosition().x + 50 - 12;
            centre.y = gameObject.getTransform().getPosition().y + halfHeight;
        }

        else if (position == GameElementRotationPosition.RIGHT) {
            height = 50;
            width = 23;
            halfHeight = 25;
            halfWidth = 12;
            centre.x = gameObject.getTransform().getPosition().x + 12;
            centre.y = gameObject.getTransform().getPosition().y + halfHeight;
        }
    }

    @Override
    public Component copy() {
        return new PlatformBounds(width, height);
    }
}
