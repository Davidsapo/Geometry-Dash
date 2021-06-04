package geometry.dash.scenes;

import geometry.dash.Window;
import geometry.dash.components.*;
import geometry.dash.engine.*;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.LevelData;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

import static geometry.dash.utils.Constants.*;
import static geometry.dash.utils.Constants.CAMERA_OFFSET_X;


public class StartScene extends LevelScene {

    private Player playerSupportComponent;
    private String[] skins = {"assets\\blue_mod\\cube.png", "assets\\green_mod\\cube.png", "assets\\yellow_mode\\cube.png", "assets\\red_mode\\cube.png"};
    private int currSkin = 1;
    private Random random;

    private StartButton startButton;

    public StartScene(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();
        player.addComponent(new RigidBody(new Vector(14, 0)));
        player.addComponent(new BoxBounds(PLAYER_WIDTH, PLAYER_HEIGHT));
        playerSupportComponent = player.getComponent(Player.class);
        firstLayerComponents.getComponent(ParallaxBackground.class).movable = true;
        player.hasCollision = true;
        playerSupportComponent.active = true;
        random = new Random();
        startButton = new StartButton();

    }

    @Override
    public void update() {
        playerSupportComponent.onGround = false;
        playerSupportComponent.jumpAvailable = false;
        firstLayerComponents.update();
        for (GameObject object : gameObjects) {
            object.update();
            //playerSupportComponent.resolveCollision(object);
        }
        if (random.nextInt(35) == 0) {
            playerSupportComponent.addJumpForce();
            playerSupportComponent.onGround = false;
        }
        player.update();
        thirdLayerComponents.update();
        startButton.update();


        if (player.getTransform().getPosition().x - camera.position.x > SCREEN_WIDTH + 200) {
            player.getTransform().getPosition().x = camera.position.x - 100;
            player.getComponent(Player.class).setPlayerImagePath(skins[currSkin++]);
            if (currSkin > 3)
                currSkin = 0;
        }

        camera.position.x += 8;

    }


    @Override
    public void draw(Graphics2D graphics2D) {
        firstLayerComponents.draw(graphics2D);
        secondLayerRender.render(graphics2D);
        thirdLayerComponents.draw(graphics2D);
        BufferedImage image = AssetPool.getImage("assets\\title.png");
        BufferedImage image3 = AssetPool.getImage("assets\\sign.png");
        graphics2D.drawImage(image, (SCREEN_WIDTH - image.getWidth()) / 2, 75, null);
        graphics2D.drawImage(image3, (SCREEN_WIDTH - image3.getWidth()) / 2, 510, null);
        startButton.draw(graphics2D);
    }

    public class StartButton {

        private BufferedImage element;

        private final int xPosButton;
        private final int yPosButton;

        private double width;
        private double height;
        MouseDetector mouseDetector;

        AffineTransform transform = new AffineTransform();


        public StartButton() {
            this.element = AssetPool.getImage("assets\\start.png");


            xPosButton = (SCREEN_WIDTH - element.getWidth()) / 2;
            yPosButton = 170;

            width = element.getWidth();
            height = element.getHeight();

            mouseDetector = Window.getWindow().getMouseDetector();
            transform.translate(xPosButton, yPosButton);


        }


        public void update() {
            int mouseX = mouseDetector.xPos;
            int mouseY = mouseDetector.yPos;
            boolean onButton = (mouseX >= xPosButton && mouseX <= xPosButton + width &&
                    mouseY >= yPosButton && mouseY <= yPosButton + height);
            if (onButton) {
                if (transform.getScaleX() < 1.2) {
                    transform.translate(-(height * 0.01), -(height * 0.01));
                    transform.scale(1.02, 1.02);
                    height *= 1.02;
                    width *= 1.02;
                }
            } else if (transform.getScaleX() > 1) {
                transform.scale(0.98, 0.98);
                transform.translate((width * 0.01), (height * 0.01));
                height *= 0.98;
                width *= 0.98;
            }

            if (onButton && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                Scene scene = SceneFactory.createScene(1);
                scene.init();
                Window.getWindow().setScene(scene);

            }
            //transform.translate(xPosButton,yPosButton);
        }

        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(element, transform, null);

        }
    }

}
