package geometry.dash.components;

import geometry.dash.engine.*;
import geometry.dash.engine.Component;
import geometry.dash.engine.Window;
import geometry.dash.utils.Vector;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static geometry.dash.utils.Constants.*;

public class BlockBuilder extends Component {

    private String blockImage;
    private Bounds bounds;
    private Camera camera;
    private MouseDetector mouseDetector;
    private ArrayList<Vector> blockedPositions;

    public BlockBuilder(Camera camera, MouseDetector mouseDetector) {
        this.camera = camera;
        this.mouseDetector = mouseDetector;
        blockedPositions = new ArrayList<>();
    }

    public BlockBuilder(String blockImage, Camera camera, MouseDetector mouseDetector) {
        this(camera, mouseDetector);
        this.blockImage = blockImage;
        blockedPositions = new ArrayList<>();
    }

    public BlockBuilder(String blockImage, Bounds bounds, Camera camera, MouseDetector mouseDetector) {
        this(blockImage, camera, mouseDetector);
        this.blockImage = blockImage;
        this.bounds = bounds;

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        if (blockImage != null && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
            int camXPos = (int)camera.position.x;
            int camYPos = (int)camera.position.y + TOP_BORDER_HEIGHT;
            int x = ((camXPos + mouseDetector.xPos) / PLAYER_WIDTH) * PLAYER_WIDTH - camXPos;
            int y = ((camYPos + mouseDetector.yPos) / TILE_HEIGHT) * TILE_HEIGHT - camYPos;

            Vector vector = new Vector(x + camXPos, y + camera.position.y);
            if (!blockedPositions.contains(vector) && vector.y + TILE_HEIGHT <= SCREEN_HEIGHT - GROUND_HEIGHT) {
                blockedPositions.add(vector);
                GameObject block = new GameObject("Block", new Transform(vector, TILE_WIDTH, TILE_HEIGHT));
                block.addComponent(new Sprite(blockImage));
                if (bounds != null) {
                    block.addComponent(bounds.copy());
                    block.hasCollision=true;
                }
                Window.getWindow().getCurrentScene().addGameObject(block);
            }
        }
    }

    public void setBlockImage(String blockImage) {
        this.blockImage = blockImage;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public ArrayList<Vector> getBlockedPositions() {
        return blockedPositions;
    }

    public void setBlockedPositions(ArrayList<Vector> blockedPositions) {
        this.blockedPositions = blockedPositions;
    }
}
