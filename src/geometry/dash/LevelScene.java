package geometry.dash;

import geometry.dash.components.*;
import geometry.dash.engine.GameObject;
import geometry.dash.engine.Scene;
import geometry.dash.engine.Transform;
import geometry.dash.engine.Window;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.CollisionDetectors;
import geometry.dash.utils.Constants;
import geometry.dash.utils.LevelData;
import geometry.dash.utils.Vector;

import static geometry.dash.utils.Constants.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LevelScene extends Scene {

    private GameObject player;
    private Ground ground;
    private Grid grid;

    public LevelScene(String name) {
        super(name);
    }

    @Override
    public void init() {
        player = new GameObject("Player", new Transform(new Vector(PLAYER_SPAWN_X, PLAYER_SPAWN_Y), PLAYER_WIDTH, PLAYER_HEIGHT));
        SpriteSheet spriteSheet = AssetPool.getSpriteSheet("assets\\player\\playerSkins2.png");
        player.addComponent(new Player(spriteSheet.getTilePath(2), Color.GREEN, Window.getWindow().getKeyDetector(), true));
        player.addComponent(new RigidBody(new Vector(PLAYER_SPEED, 0)));
        player.addComponent(new BoxBounds(PLAYER_WIDTH, PLAYER_HEIGHT));
        player.hasCollision = true;
        renderer.submit(player);

        ground = new Ground("assets/ground/ground01.png", camera, new Color(0, 0, 170));
        grid = new Grid(camera);

        /*GameObject block = new GameObject("Temp", new Transform(new Vector(100*50, PLAYER_SPAWN_Y),0,0));
        block.addComponent(new BoxBounds(50,50));
        block.addComponent(new Sprite(spriteSheet.getTilePath(1)));
        block.hasCollision=true;
        addGameObject(block);*/
    }

    @Override
    public void update() {
        Player playerSupport = player.getComponent(Player.class);
        playerSupport.onGround = false;
        for (GameObject object : gameObjects) {
            object.update();
            playerSupport.resolveCollision(object);
        }
        ground.update();
        player.update();

        if (player.getTransform().getPosition().x - camera.position.x > CAMERA_OFFSET_X)
            camera.position.x = player.getTransform().getPosition().x - CAMERA_OFFSET_X;
    }


    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.blue);
        //graphics2D.drawImage(AssetPool.getSprite("assets/background/testbackground.png").getImage(), 0, 0, null);
        graphics2D.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        grid.draw(graphics2D);
        renderer.render(graphics2D);
        ground.draw(graphics2D);
    }

    public GameObject getPlayer() {
        return player;
    }

    public void setLevelData(LevelData levelData) {
        for (GameObject object : levelData.gameObjects)
            addGameObject(object);
    }
}
