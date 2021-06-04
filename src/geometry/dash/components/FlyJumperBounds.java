package geometry.dash.components;

import geometry.dash.engine.Component;
import geometry.dash.utils.CollisionDetectors;

public class FlyJumperBounds extends BoxBounds {
    public FlyJumperBounds(int width, int height) {
        super(width, height);
    }

    @Override
    public CollisionDetectors getCollision(BoxBounds boxBounds) {
        return (super.getCollision(boxBounds) == CollisionDetectors.NO_COLLISION) ? CollisionDetectors.NO_COLLISION : CollisionDetectors.FLY_JUMPER;
    }

    @Override
    public Component copy() {
        return new FlyJumperBounds(width,height);
    }
}
