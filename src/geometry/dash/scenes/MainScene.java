package geometry.dash.scenes;

import geometry.dash.Window;
import geometry.dash.components.*;
import geometry.dash.engine.*;
import geometry.dash.engine.Component;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.LevelData;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static geometry.dash.utils.Constants.*;

public class MainScene implements Scene {

    protected String name;
    protected Camera camera;
    protected GameObject firstLayerComponents;
    protected Renderer secondLayerRender;
    protected GameObject thirdLayerComponents;
    protected ArrayList<GameObject> gameObjects;

    protected ParallaxBackground parallaxBackground;

    private int nextLevel = 1;
    private ArrayList<Component> levels;

    private MainScene(String name) {
        this.name = name;
        camera = new Camera(new Vector());
        secondLayerRender = new Renderer(camera);
        gameObjects = new ArrayList<>();
        firstLayerComponents = new GameObject("First layer", null);
        thirdLayerComponents = new GameObject("SecondLayer", null);
        CameraController cameraController = new CameraController(camera, Window.getWindow().getMouseDetector());
        cameraController.onlyX = true;
        firstLayerComponents.addComponent(cameraController);
        parallaxBackground = (ParallaxBackground) new ParallaxBackground("assets/background/background.png", camera, new Color(44, 61, 245), false).initLayer(1);
        parallaxBackground.setxDrawPos(0);
        firstLayerComponents.addComponent(new Ground("assets/ground/ground01.png", camera, new Color(3, 18, 192)).initLayer(3));
    }

    private static MainScene mainScene;

    public static MainScene getInstance() {
        if (mainScene == null)
            mainScene = new MainScene("");
        return mainScene;
    }

    public void init() {
        camera.position.x = 0;
        nextLevel = 1;
        levels = new ArrayList<>();
        for (LevelData levelData : AssetPool.levels)
            new LevelLabel(levelData);
        levels.add(new CustomLevelLabel());
    }

    @Override
    public void update() {
        firstLayerComponents.update();
        for (GameObject object : gameObjects)
            object.update();
        thirdLayerComponents.update();

        for (Component levelLabel : levels) {
            levelLabel.update();
        }
        parallaxBackground.setxDrawPos(0);
    }


    @Override
    public void draw(Graphics2D graphics2D) {
        parallaxBackground.draw(graphics2D);

        firstLayerComponents.draw(graphics2D);
        secondLayerRender.render(graphics2D);
        thirdLayerComponents.draw(graphics2D);
        BufferedImage title = AssetPool.getImage("assets\\top.png");
        BufferedImage leftCorner = AssetPool.getImage("assets\\left_corner.png");
        BufferedImage rightCorner = AssetPool.getImage("assets\\right_corner.png");

        graphics2D.drawImage(title, (SCREEN_WIDTH - title.getWidth()) / 2, 25, null);
        graphics2D.drawImage(leftCorner, 0, SCREEN_HEIGHT - 170, null);
        graphics2D.drawImage(rightCorner, SCREEN_WIDTH - 170, SCREEN_HEIGHT - 170, null);

        for (Component levelLabel : levels) {
            levelLabel.draw(graphics2D);
        }
    }


    public class LevelLabel extends Component {

        private int levelID;

        private BufferedImage labelImage;
        private BufferedImage buttonImage;

        private final int xPosButton;
        private final int yPosButton;
        private double buttonWidth;
        private double buttonHeight;
        private int xDrawPosButton;

        private final int xPosLabel;
        private final int yPosLabel;
        private int xDrawPosLabel;

        MouseDetector mouseDetector;

        AffineTransform transform = new AffineTransform();
        LevelData levelData;


        public LevelLabel(LevelData levelData) {
            levelID = nextLevel++;
            this.buttonImage = AssetPool.getImage("assets\\play.png");
            this.labelImage = AssetPool.getImage("assets\\level_icons\\level" + levelID + ".png");
            this.levelData = levelData;


            xPosButton = SCREEN_WIDTH * (levelID - 1) + (SCREEN_WIDTH - buttonImage.getWidth()) / 2;
            yPosButton = 342;
            buttonWidth = buttonImage.getWidth();
            buttonHeight = buttonImage.getHeight();

            xPosLabel = SCREEN_WIDTH * (levelID - 1) + (SCREEN_WIDTH - labelImage.getWidth()) / 2;
            yPosLabel = 130;

            mouseDetector = Window.getWindow().getMouseDetector();
            transform.translate(xPosButton, yPosButton);

            xDrawPosButton = xPosButton;

            xDrawPosLabel = xPosLabel;
            levels.add(this);
        }


        boolean ready;

        public void update() {
            xDrawPosLabel = xPosLabel - (int) camera.position.x;
            xDrawPosButton = xPosButton - (int) camera.position.x;
            int mouseX = mouseDetector.xPos;
            int mouseY = mouseDetector.yPos;
            boolean onButton = (mouseX >= xDrawPosButton && mouseX <= xDrawPosButton + buttonWidth &&
                    mouseY >= yPosButton && mouseY <= yPosButton + buttonHeight);
            if (onButton) {
                if (transform.getScaleX() < 1.1) {
                    transform.translate(-(buttonHeight * 0.01), -(buttonHeight * 0.01));
                    transform.scale(1.02, 1.02);
                    buttonHeight *= 1.02;
                    buttonWidth *= 1.02;
                }
            } else if (transform.getScaleX() > 1) {
                transform.scale(0.98, 0.98);
                transform.translate((buttonWidth * 0.01), (buttonHeight * 0.01));
                buttonHeight *= 0.98;
                buttonWidth *= 0.98;
            } else {
                transform.setToTranslation(xDrawPosButton, yPosButton);
            }

            if (ready && onButton && !mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                LevelRunScene scene = (LevelRunScene) SceneFactory.createScene(0);
                scene.init();
                scene.setLevelData(levelData);
                Window.getWindow().setScene(scene);
            }

            ready = onButton && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1;
        }

        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(buttonImage, transform, null);
            graphics2D.drawImage(labelImage, xDrawPosLabel, yPosLabel, null);
        }
    }


    public class CustomLevelLabel extends Component {

        private int levelID;

        private BufferedImage labelImage;
        private BufferedImage buttonImage;

        private final int xPosButton;
        private final int yPosButton;
        private double buttonWidth;
        private double buttonHeight;
        private int xDrawPosButton;

        private final int xPosLabel;
        private final int yPosLabel;
        private int xDrawPosLabel;

        MouseDetector mouseDetector;

        AffineTransform transform = new AffineTransform();


        public CustomLevelLabel() {
            levelID = nextLevel++;
            this.buttonImage = AssetPool.getImage("assets\\custom_button.png");
            this.labelImage = AssetPool.getImage("assets\\level_icons\\create.png");


            xPosButton = SCREEN_WIDTH * (levelID - 1) + (SCREEN_WIDTH - buttonImage.getWidth()) / 2;
            yPosButton = 342;
            buttonWidth = buttonImage.getWidth();
            buttonHeight = buttonImage.getHeight();

            xPosLabel = SCREEN_WIDTH * (levelID - 1) + (SCREEN_WIDTH - labelImage.getWidth()) / 2;
            yPosLabel = 130;

            mouseDetector = Window.getWindow().getMouseDetector();
            transform.translate(xPosButton, yPosButton);

            xDrawPosButton = xPosButton;

            xDrawPosLabel = xPosLabel;
            levels.add(this);
        }


        boolean ready = false;

        public void update() {
            xDrawPosLabel = xPosLabel - (int) camera.position.x;
            xDrawPosButton = xPosButton - (int) camera.position.x;
            int mouseX = mouseDetector.xPos;
            int mouseY = mouseDetector.yPos;
            boolean onButton = (mouseX >= xDrawPosButton && mouseX <= xDrawPosButton + buttonWidth &&
                    mouseY >= yPosButton && mouseY <= yPosButton + buttonHeight);
            if (onButton) {
                if (transform.getScaleX() < 1.1) {
                    transform.translate(-(buttonHeight * 0.01), -(buttonHeight * 0.01));
                    transform.scale(1.02, 1.02);
                    buttonHeight *= 1.02;
                    buttonWidth *= 1.02;
                }
            } else if (transform.getScaleX() > 1) {
                transform.scale(0.98, 0.98);
                transform.translate((buttonWidth * 0.01), (buttonHeight * 0.01));
                buttonHeight *= 0.98;
                buttonWidth *= 0.98;
            } else {
                transform.setToTranslation(xDrawPosButton, yPosButton);
            }

            if (ready && onButton && !mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                LevelEditorScene scene = (LevelEditorScene)SceneFactory.createScene(1);
                scene.init();
                scene.setLevelData(AssetPool.levels.get(0));
                Window.getWindow().setScene(scene);

            }

            ready = onButton && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1;
        }

        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(buttonImage, transform, null);
            graphics2D.drawImage(labelImage, xDrawPosLabel, yPosLabel, null);
        }
    }
}
