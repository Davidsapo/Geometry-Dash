package geometry.dash.components;

import geometry.dash.engine.Transform;
import geometry.dash.scenes.LevelScene;
import geometry.dash.engine.Component;
import geometry.dash.engine.GameObject;
import geometry.dash.engine.KeyDetector;
import geometry.dash.Window;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.CollisionDetectors;

import static geometry.dash.utils.Constants.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Component {

    private String currentImage;
    private String playerImagePath;
    private String shipImagePath;
    public boolean onGround = true;
    private final KeyDetector keyDetector;
    public boolean active;
    public boolean jumpAvailable;
    public boolean onRocket;
    private GameObject lastPortal;

    public Player(String playerImagePath, String  shipImagePath, KeyDetector keyDetector, boolean active) {
        this.playerImagePath = playerImagePath;
        this.shipImagePath = shipImagePath;
        currentImage = playerImagePath;
        this.keyDetector = keyDetector;
        this.active = active;

    }

    private boolean changeColor(Color pixel) {
        int r = pixel.getRed();
        int b = pixel.getBlue();
        int g = pixel.getGreen();

        return (r > 150 && r < 200 && b > 150 && b < 200 & g > 150 && g < 200);
    }

    @Override
    public void update() {
        if (!active)
            return;

        if (!onRocket) {
            if ((onGround || jumpAvailable) && keyDetector.isKeyPressed(KeyEvent.VK_SPACE)) {
                onGround = false;
                addJumpForce();
            }

            if (!onGround)
                gameObject.getTransform().rotateObject(ROTATION);
            else {
                gameObject.getTransform().setDefaultRotationPosition();
                gameObject.getComponent(RigidBody.class).velocity.y = 0;
            }
        } else {

            if (keyDetector.isKeyPressed(KeyEvent.VK_SPACE)) {
                gameObject.getComponent(RigidBody.class).velocity.y = SHIP_FORCE;
            } else if (onGround)
                gameObject.getComponent(RigidBody.class).velocity.y = 0;


        }
    }

    public void addJumpForce() {
        gameObject.getComponent(RigidBody.class).velocity.y = JUMP_FORCE;
    }

    public void resolveCollision(GameObject object) {
        CollisionDetectors detector = object.checkCollisionWith(gameObject);
        if (detector != CollisionDetectors.NO_COLLISION) {
            if (detector == CollisionDetectors.TOP_COLLISION) {
                onGround = true;
                gameObject.getTransform().getPosition().y = object.getComponent(Bounds.class).realPos - PLAYER_HEIGHT;
            } else if (detector == CollisionDetectors.JUMP && !onRocket) {
                gameObject.getComponent(RigidBody.class).velocity.y = JUMPER_FORCE;
                onGround = false;
            } else if (detector == CollisionDetectors.PORTAL) {
                if (lastPortal !=null && object == lastPortal )
                    return;
                if (!onRocket)
                    flyMode();
                else
                    normalMode();
                lastPortal = object;
            } else if (detector == CollisionDetectors.FLY_JUMPER) {
                jumpAvailable = true;
            } else {
                die();
                normalMode();
                lastPortal = null;
            }
        }
    }

    private void normalMode() {
        onRocket = false;
        gameObject.getComponent(RigidBody.class).flyMode = false;
        gameObject.setTransform(new Transform(gameObject.getTransform().getPosition(), 50, 50));
        this.currentImage = playerImagePath;
        ((BoxBounds)(gameObject.getComponent(Bounds.class))).ship = false;
        ((BoxBounds)(gameObject.getComponent(Bounds.class))).halfWidth = 25;
        ((BoxBounds)(gameObject.getComponent(Bounds.class))).halfHeight = 25;
    }

    private void flyMode() {
        onRocket = true;
        gameObject.getComponent(RigidBody.class).flyMode = true;
        gameObject.setTransform(new Transform(gameObject.getTransform().getPosition(), 100, 50));
        this.currentImage = shipImagePath;
        ((BoxBounds)(gameObject.getComponent(Bounds.class))).ship = true;
        ((BoxBounds)(gameObject.getComponent(Bounds.class))).halfWidth = 45;
        ((BoxBounds)(gameObject.getComponent(Bounds.class))).halfHeight = 20;

    }

    public void die() {
        gameObject.getTransform().getPosition().x = PLAYER_SPAWN_X;
        gameObject.getTransform().getPosition().y = PLAYER_SPAWN_Y;
        ((LevelScene) Window.getWindow().getCurrentScene()).getCamera().position.x = 0;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(AssetPool.getImage(currentImage), gameObject.getTransform(), null);
    }

    public void setPlayerImagePath(String playerImagePath) {
        currentImage = playerImagePath;
        this.playerImagePath = playerImagePath;
    }

    public void setShipImagePath(String shipImagePath) {
        this.shipImagePath = shipImagePath;
    }

    public String getPlayerImagePath() {
        return playerImagePath;
    }

    public String getShipImagePath() {
        return shipImagePath;
    }
}
