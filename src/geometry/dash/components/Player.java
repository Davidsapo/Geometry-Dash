package geometry.dash.components;

import geometry.dash.engine.Component;
import geometry.dash.engine.GameObject;
import geometry.dash.engine.KeyDetector;
import geometry.dash.engine.Window;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.CollisionDetectors;
import geometry.dash.utils.Constants;

import static geometry.dash.utils.Constants.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Component {

    private final String playerImagePath;
    public boolean onGround = true;
    private final KeyDetector keyDetector;
    private final boolean active;

    public Player(String playerImagePath, Color color, KeyDetector keyDetector, boolean active) {
        this.playerImagePath = playerImagePath;
        this.keyDetector = keyDetector;
        this.active = active;
        BufferedImage image = AssetPool.getImage(playerImagePath);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixel = new Color(image.getRGB(x, y));
                if (changeColor(pixel))
                    image.setRGB(x, y, color.getRGB());
            }
        }
    }

    private boolean changeColor(Color pixel) {
        int r = pixel.getRed();
        int b = pixel.getBlue();
        int g = pixel.getGreen();
        return (r > 150 && r < 200 & b > 150 & b < 200 & g > 150 & g < 200);
    }

    @Override
    public void update() {
        if (!active)
            return;

        if (onGround && keyDetector.isKeyPressed(KeyEvent.VK_SPACE)) {
            onGround = false;
            addJumpForce();
        }

        if (!onGround)
            gameObject.getTransform().rotateObject(ROTATION);
        else {
            gameObject.getTransform().setDefaultRotationPosition();
            gameObject.getComponent(RigidBody.class).velocity.y = 0;
        }
    }

    private void addJumpForce() {
            gameObject.getComponent(RigidBody.class).velocity.y = JUMP_FORCE;
    }

    public void resolveCollision(GameObject object){
        CollisionDetectors detector = object.checkCollisionWith(gameObject);
        if (detector != CollisionDetectors.NO_COLLISION) {
            if (detector == CollisionDetectors.TOP_COLLISION) {
                onGround = true;
                gameObject.getTransform().getPosition().y = object.getTransform().getPosition().y - PLAYER_HEIGHT;
            } else {
                die();
            }
        }
    }

    public void die() {
        gameObject.getTransform().getPosition().x = PLAYER_SPAWN_X;
        gameObject.getTransform().getPosition().y = PLAYER_SPAWN_Y;
        Window.getWindow().getCurrentScene().getCamera().position.x = 0;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(AssetPool.getImage(playerImagePath), gameObject.getTransform(), null);
    }
}
