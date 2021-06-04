package geometry.dash.components;

import geometry.dash.engine.Component;
import geometry.dash.utils.Constants;
import geometry.dash.engine.Vector;

public class RigidBody extends Component {

    public Vector velocity;
    public boolean flyMode = false;

    public RigidBody(Vector velocity) {
        this.velocity = velocity;
    }

    @Override
    public void update() {
        gameObject.getTransform().getPosition().y += velocity.y;
        gameObject.getTransform().getPosition().x += velocity.x;
        if (!flyMode) {
            if (velocity.y < Constants.GRAVITY)
                velocity.y += Constants.ACCELERATION;
        } else {
            if (velocity.y < Constants.SHIP_GRAVITY)
                velocity.y += Constants.SHIP_ACCELERATION;
        }

    }
}
