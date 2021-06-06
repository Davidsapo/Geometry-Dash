package geometry.dash.scenes;

import geometry.dash.Window;
import geometry.dash.components.*;
import geometry.dash.engine.*;
import geometry.dash.engine.Component;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.LevelData;
import geometry.dash.utils.MediaPlayer;
import javafx.scene.media.Media;

import static geometry.dash.utils.Constants.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class LevelRunScene extends LevelScene {

    public boolean startPlay = true;

    private Player playerSupportComponent;
    private int cameraRightLimit;
    boolean end = false;
    ArrayList<Component> menu = new ArrayList<>();


    public LevelRunScene(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();
        player.addComponent(new RigidBody(new Vector(PLAYER_SPEED, 0)));
        player.addComponent(new BoxBounds(PLAYER_WIDTH, PLAYER_HEIGHT));
        playerSupportComponent = player.getComponent(Player.class);
        firstLayerComponents.getComponent(ParallaxBackground.class).movable = true;
        player.hasCollision = true;
        playerSupportComponent.active = true;
        thirdLayerComponents.addComponent(new ExitButton());
    }

    @Override
    public void update() {
        if (camera.position.x + SCREEN_WIDTH < cameraRightLimit) {
            if (player.getTransform().getPosition().x - camera.position.x > CAMERA_OFFSET_X)
                camera.position.x = player.getTransform().getPosition().x - CAMERA_OFFSET_X;
        } else {
            firstLayerComponents.getComponent(ParallaxBackground.class).movable = false;
        }

        if (player.getTransform().getPosition().y < camera.position.y + CAMERA_OFFSET_Y)
            camera.position.y = player.getTransform().getPosition().y - CAMERA_OFFSET_Y;
        else if (player.getTransform().getPosition().y > camera.position.y + 425 && camera.position.y < 0)
            camera.position.y = player.getTransform().getPosition().y - 425;
        if (camera.position.y > -10)
            camera.position.y = 0;

        /*if (camera.position.y < CAMERA_TOP_LIMIT)
            camera.position.y = CAMERA_TOP_LIMIT;*/

        playerSupportComponent.onGround = false;
        playerSupportComponent.jumpAvailable = false;
        firstLayerComponents.update();
        if (!end) {
            for (GameObject object : gameObjects) {
                object.update();
                playerSupportComponent.resolveCollision(object);
            }
        }
        player.update();
        thirdLayerComponents.update();

        if (player.getTransform().getPosition().x > cameraRightLimit + 200 && !end) {
            player.getComponent(RigidBody.class).velocity.x = 0;
            new CompletedLabel();
            new ReplayButton();
            new HomeButton();
            end = true;
            MediaPlayer.player.stop();
        }
        for (Component component : menu) {
            component.update();
        }

        if (startPlay && player.getTransform().getPosition().x >= -50) {
            MediaPlayer.player.play();
            startPlay = false;
        }

        {
            if (geometry.dash.Window.getWindow().getKeyDetector().isKeyPressed(KeyEvent.VK_SHIFT)) {
                MediaPlayer.player.stop();
                Scene scene = SceneFactory.createScene(1);
                scene.init();
                Window.getWindow().setScene(scene);
            }
        }
    }


    @Override
    public void draw(Graphics2D graphics2D) {
        firstLayerComponents.draw(graphics2D);
        secondLayerRender.render(graphics2D);
        thirdLayerComponents.draw(graphics2D);
        for (Component component : menu) {
            component.draw(graphics2D);
        }
    }

    public void setLevelData(LevelData levelData) {
        int lastX = 0;
        for (GameObject object : levelData.gameObjects) {
            addGameObject(object);
            int x = (int) object.getTransform().getPosition().x;
            if (x > lastX)
                lastX = x;
        }
        lastX += 650;
        cameraRightLimit = lastX + 100;

        playerSupportComponent.setPlayerImagePath(levelData.playerImage);
        playerSupportComponent.setShipImagePath(levelData.shipImage);
        firstLayerComponents.getComponent(ParallaxBackground.class).setBackgroundImage(levelData.backgroundImage);
        firstLayerComponents.getComponent(Ground.class).setGroundImagePath(levelData.groundImage);
        firstLayerComponents.getComponent(ParallaxBackground.class).setColor(levelData.backgroundColor);
        firstLayerComponents.getComponent(Ground.class).setGroundColor(levelData.groundColor);

        int startY = SCREEN_HEIGHT - GROUND_HEIGHT - 100;
        for (int i = 0; i < 12; i++) {
            GameObject finish = new GameObject("", new Transform(new Vector(lastX, startY), 50, 50));
            finish.addComponent(new Sprite("assets/blocks/end_block.png"));
            addGameObject(finish);
            startY -= 50;
        }

        if (levelData.track != null) {
            MediaPlayer.init(levelData.track);
        }
    }


    public class ExitButton extends Component {

        private BufferedImage element;

        private final int xPosButton;
        private final int yPosButton;

        private double width;
        private double height;
        MouseDetector mouseDetector;

        AffineTransform transform = new AffineTransform();


        public ExitButton() {
            this.element = AssetPool.getImage("assets\\close_button.png");


            xPosButton = 1130;
            yPosButton = 45;

            width = element.getWidth();
            height = element.getHeight();

            mouseDetector = Window.getWindow().getMouseDetector();
            transform.translate(xPosButton, yPosButton);


        }

        boolean ready = false;

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

            if (ready && onButton && !mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                MediaPlayer.player.stop();
                Scene scene = SceneFactory.createScene(2);
                //scene.init();
                Window.getWindow().setScene(scene);
            }
            ready = onButton && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1;
        }

        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(element, transform, null);

        }
    }

    public class CompletedLabel extends Component {

        private BufferedImage element;

        private final int xPosButton;
        private final int yPosButton;

        private double width;
        private double height;
        MouseDetector mouseDetector;

        AffineTransform transform = new AffineTransform();

        public CompletedLabel() {
            this.element = AssetPool.getImage("assets\\level_completed.png");
            xPosButton = (SCREEN_WIDTH - element.getWidth()) / 2;
            yPosButton = 125;

            width = element.getWidth();
            height = element.getHeight();

            mouseDetector = Window.getWindow().getMouseDetector();
            transform.translate(xPosButton, yPosButton);
            transform.scale(0.1, 0.1);
            menu.add(this);
        }

        public void update() {
            if (transform.getScaleX() < 0.9) {
                transform.scale(1.1, 1.1);
            }
        }

        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(element, transform, null);
        }
    }

    public class ReplayButton extends Component {

        private BufferedImage element;

        private final int xPosButton;
        private final int yPosButton;

        private double width;
        private double height;
        MouseDetector mouseDetector;

        boolean appearing = true;

        AffineTransform transform = new AffineTransform();


        public ReplayButton() {
            this.element = AssetPool.getImage("assets\\repeat.png");


            xPosButton = SCREEN_WIDTH - 420;
            yPosButton = 380;

            width = element.getWidth();
            height = element.getHeight();

            mouseDetector = Window.getWindow().getMouseDetector();
            transform.translate(xPosButton, yPosButton);
            transform.scale(0.1, 0.1);
            menu.add(this);

        }

        boolean ready = false;

        public void update() {
            if (appearing) {
                if (transform.getScaleX() < 0.9) {
                    transform.scale(1.1, 1.1);
                } else appearing = false;
            } else {
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

                if (ready && onButton && !mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                    playerSupportComponent.normalMode();
                    player.getComponent(RigidBody.class).velocity.x = PLAYER_SPEED;
                    firstLayerComponents.getComponent(ParallaxBackground.class).movable = true;
                    playerSupportComponent.die();
                    menu = new ArrayList<>();
                    end = false;
                    startPlay = true;
                }

                ready = onButton && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1;
            }

        }

        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(element, transform, null);

        }
    }

    public class HomeButton extends Component {

        private BufferedImage element;

        private final int xPosButton;
        private final int yPosButton;

        private double width;
        private double height;
        MouseDetector mouseDetector;

        boolean appearing = true;

        AffineTransform transform = new AffineTransform();


        public HomeButton() {
            this.element = AssetPool.getImage("assets\\home.png");


            xPosButton = 300;
            yPosButton = 380;

            width = element.getWidth();
            height = element.getHeight();

            mouseDetector = Window.getWindow().getMouseDetector();
            transform.translate(xPosButton, yPosButton);
            transform.scale(0.1, 0.1);
            menu.add(this);

        }

        boolean ready = false;

        public void update() {
            if (appearing) {
                if (transform.getScaleX() < 0.9) {
                    transform.scale(1.1, 1.1);
                } else appearing = false;
            } else {
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

                if (ready && onButton && !mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                    MediaPlayer.player.stop();
                    Scene scene = SceneFactory.createScene(2);
                    //scene.init();
                    Window.getWindow().setScene(scene);
                }

                ready = onButton && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1;
            }

        }

        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(element, transform, null);
        }
    }

}
