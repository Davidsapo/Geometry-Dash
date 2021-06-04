package geometry.dash.components;

import geometry.dash.scenes.LevelScene;
import geometry.dash.engine.*;
import geometry.dash.engine.Component;
import geometry.dash.engine.Vector;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import static geometry.dash.utils.Constants.*;

public class BlockBuilder extends Component {

    private String blockImage;
    private Bounds bounds;
    private Camera camera;
    private LevelScene levelScene;
    private MouseDetector mouseDetector;
    private HashMap<Vector, GameObject> blockedPositions;

    private boolean ready;

    public int mode = 1;

    public BlockBuilder(Camera camera, MouseDetector mouseDetector, LevelScene levelScene) {
        this.camera = camera;
        this.mouseDetector = mouseDetector;
        blockedPositions = new HashMap<>();
        this.levelScene = levelScene;
    }

    public BlockBuilder(String blockImage, Camera camera, MouseDetector mouseDetector, LevelScene levelScene) {
        this(camera, mouseDetector, levelScene);
        this.blockImage = blockImage;
        blockedPositions = new HashMap<>();
    }

    public BlockBuilder(String blockImage, Bounds bounds, Camera camera, MouseDetector mouseDetector, LevelScene levelScene) {
        this(blockImage, camera, mouseDetector, levelScene);
        this.blockImage = blockImage;
        this.bounds = bounds;

    }

    @Override
    public void update() {
        switch (mode) {
            case (1):
                build();
                break;
            case (2):
                delete();
                break;
            case (3):
                rotate();

        }
    }

    Vector currVect;

    private void rotate() {
        if (mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1 && mouseDetector.layer == 1) {
            int camXPos = (int) camera.position.x;
            int camYPos = (int) camera.position.y + TOP_BORDER_HEIGHT;
            int x = ((camXPos + mouseDetector.xPos) / PLAYER_WIDTH) * PLAYER_WIDTH - camXPos;
            int y = ((camYPos + mouseDetector.yPos) / TILE_HEIGHT) * TILE_HEIGHT - camYPos;
            currVect = new Vector(x + camXPos, y + camera.position.y);

            ready = true;
        }
        if (ready) {
            if (!mouseDetector.pressed && blockedPositions.containsKey(currVect) && currVect.y + TILE_HEIGHT <= SCREEN_HEIGHT - GROUND_HEIGHT) {
                GameObject curr = blockedPositions.get(currVect);
                curr.getTransform().rotate90();
                curr.getComponent(Bounds.class).init();

                ready = false;
            }
        }
    }

    private void delete() {
        if (mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1 && mouseDetector.layer == 1) {
            int camXPos = (int) camera.position.x;
            int camYPos = (int) camera.position.y + TOP_BORDER_HEIGHT;
            int x = ((camXPos + mouseDetector.xPos) / PLAYER_WIDTH) * PLAYER_WIDTH - camXPos;
            int y = ((camYPos + mouseDetector.yPos) / TILE_HEIGHT) * TILE_HEIGHT - camYPos;

            Vector vector = new Vector(x + camXPos, y + camera.position.y);
            if (blockedPositions.containsKey(vector) && vector.y + TILE_HEIGHT <= SCREEN_HEIGHT - GROUND_HEIGHT) {
                blockedPositions.get(vector).delete();
                blockedPositions.remove(vector);
            }
        }
    }

    private void build() {
        if (blockImage != null && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1 && mouseDetector.layer == 1) {
            int camXPos = (int) camera.position.x;
            int camYPos = (int) camera.position.y + TOP_BORDER_HEIGHT;
            int x = ((camXPos + mouseDetector.xPos) / PLAYER_WIDTH) * PLAYER_WIDTH - camXPos;
            int y = ((camYPos + mouseDetector.yPos) / TILE_HEIGHT) * TILE_HEIGHT - camYPos;

            Vector vector = new Vector(x + camXPos, y + camera.position.y);
            if (!blockedPositions.containsKey(vector) && vector.y + TILE_HEIGHT <= SCREEN_HEIGHT - GROUND_HEIGHT) {
                GameObject block = new GameObject("Block", new Transform(vector, TILE_WIDTH, TILE_HEIGHT));
                block.addComponent(new Sprite(blockImage));
                if (bounds != null) {
                    Bounds newBounds = (Bounds)bounds.copy();
                    block.addComponent(newBounds);
                    newBounds.init();
                    block.hasCollision = true;
                }
                levelScene.addGameObject(block);
                blockedPositions.put(vector, block);
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
        return null;
    }

    public void setBlockedPositions(ArrayList<Vector> blockedPositions) {
        //this.blockedPositions = blockedPositions;
    }
}
