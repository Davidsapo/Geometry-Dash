package geometry.dash.components;

import geometry.dash.Window;
import geometry.dash.engine.Component;
import geometry.dash.engine.MouseDetector;
import geometry.dash.engine.Scene;
import geometry.dash.scenes.LevelEditorScene;
import geometry.dash.scenes.LevelScene;
import geometry.dash.scenes.SceneFactory;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.Constants;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static geometry.dash.utils.Constants.*;

public class EditorPane extends Component {
    public static final int BUTTONS_PER_ROW = 6;
    public static final int BUTTONS_PER_COLUMN = 2;
    public static final int TOTAL_BUTTONS = BUTTONS_PER_ROW * BUTTONS_PER_COLUMN;
    public static final int BUTTON_SIZE = 70;
    public static final int BIG_BUTTON_SIZE = 150;
    public static final int BUTTON_SPACING = 10;
    public static final int FIRST_BUTTON_POSITION_X = (SCREEN_WIDTH - BUTTONS_PER_ROW * (BUTTON_SIZE + BUTTON_SPACING)) / 2 - 170;
    public static final int FIRST_BUTTON_POSITION_Y = SCREEN_HEIGHT - (BUTTONS_PER_COLUMN * (BUTTON_SIZE + BUTTON_SPACING)) - 8;
    public static final int FIRST_BIG_BUTTON_POSITION_X = FIRST_BUTTON_POSITION_X + (BUTTON_SIZE + BUTTON_SPACING) * BUTTONS_PER_ROW + 5;
    public static final int FIRST_BIG_BUTTON_POSITION_Y = FIRST_BUTTON_POSITION_Y;

    private final BufferedImage SMALL_BUTTON_IMAGE;
    private final BufferedImage SMALL_BUTTON_HOVER;
    private int NEXT_SMALL_BUTTON_INDEX = 1;

    private final BufferedImage BIG_BUTTON_IMAGE;
    private final BufferedImage BIG_BUTTON_HOVER;
    private int NEXT_BIG_BUTTON_INDEX = 1;

    private int MODE_BUTTON_INDEX = 1;
    public static final int FIRST_MODE_BUTTON_POSITION_X = 15;
    public static final int FIRST_MODE_BUTTON_POSITION_Y = FIRST_BUTTON_POSITION_Y;
    public static final int MODE_BUTTONS_PER_ROW = 2;
    public static final int MODE_BUTTONS_PER_COLUMN = 2;

    private final BufferedImage paneImage;
    private final int yOffset;

    private final ArrayList<Button> buttons;
    private final ArrayList<Button> modeButtons;

    private final MouseDetector mouseDetector;

    public EditorPane(String paneImagePath) {
        SpriteSheet buttonsSheet = AssetPool.getSpriteSheet("assets\\buttons\\buttons2.png");
        SMALL_BUTTON_IMAGE = AssetPool.getImage("assets\\buttons\\small_button.png");
        SMALL_BUTTON_HOVER = AssetPool.getImage("assets\\buttons\\small_hover.png");

        BIG_BUTTON_IMAGE = AssetPool.getImage("assets\\buttons\\big_button.png");
        BIG_BUTTON_HOVER = AssetPool.getImage("assets\\buttons\\big_hover.png");

        paneImage = AssetPool.getImage(paneImagePath);
        yOffset = Constants.SCREEN_HEIGHT - paneImage.getHeight();
        buttons = new ArrayList<>();
        modeButtons = new ArrayList<>();

        mouseDetector = Window.getWindow().getMouseDetector();
    }

    @Override
    public void update() {
        for (Button button : buttons)
            button.update();
        for (Button button : modeButtons)
            button.update();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(paneImage, 0, yOffset, null);
        for (Button button : buttons)
            button.draw(graphics2D);
        for (Button button : modeButtons)
            button.draw(graphics2D);
    }

    public class BigItemButton extends Button {

        private final String element;
        private final Bounds bounds;

        private final int xPosButton;
        private final int yPosButton;
        private final int xPosElement;
        private final int yPosElement;


        public BigItemButton(String element, Bounds bounds) {
            int buttonIndex = NEXT_BIG_BUTTON_INDEX++;
            this.element = element;
            this.bounds = bounds;

            xPosButton = FIRST_BIG_BUTTON_POSITION_X + ((buttonIndex - 1) * (BIG_BUTTON_SIZE + BUTTON_SPACING));
            yPosButton = FIRST_BIG_BUTTON_POSITION_Y;

            xPosElement = xPosButton + (BIG_BUTTON_SIZE - bounds.width) / 2;

            yPosElement = yPosButton + (BIG_BUTTON_SIZE - bounds.height) / 2;
            buttons.add(this);
        }

        @Override
        public void update() {
            int mouseX = mouseDetector.xPos;
            int mouseY = mouseDetector.yPos;
            boolean onButton = (mouseX >= xPosButton && mouseX <= xPosButton + BIG_BUTTON_SIZE &&
                    mouseY >= yPosButton && mouseY <= yPosButton + BIG_BUTTON_SIZE);
            if (onButton)
                mouseDetector.layer = 2;

            if (onButton && !selected && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                selected = true;
                for (Button button : buttons) {
                    if (button != this)
                        button.selected = false;
                }
                BlockBuilder blockBuilder = EditorPane.this.gameObject.getComponent(BlockBuilder.class);
                blockBuilder.setBlockImage(element);
                blockBuilder.setBounds(bounds);
                blockBuilder.mode = 1;
            }
        }

        @Override
        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(BIG_BUTTON_IMAGE, xPosButton, yPosButton, null);
            graphics2D.drawImage(AssetPool.getImage(element), xPosElement, yPosElement, null);
            if (selected)
                graphics2D.drawImage(BIG_BUTTON_HOVER, xPosButton, yPosButton, null);
        }
    }

    public abstract class Button {
        public boolean selected;

        abstract void update();

        abstract void draw(Graphics2D graphics2D);
    }

    public class SmallItemButton extends Button {

        private String element;
        private final Bounds bounds;

        private final int xPosButton;
        private final int yPosButton;
        private final int xPosElement;
        private final int yPosElement;


        public SmallItemButton(String element, Bounds bounds) {
            int buttonIndex = NEXT_SMALL_BUTTON_INDEX++;
            this.element = element;
            this.bounds = bounds;

            xPosButton = FIRST_BUTTON_POSITION_X + (BUTTON_SIZE + BUTTON_SPACING) * ((buttonIndex - 1) % BUTTONS_PER_ROW);
            yPosButton = FIRST_BUTTON_POSITION_Y + (BUTTON_SIZE + BUTTON_SPACING) * ((buttonIndex - 1) / BUTTONS_PER_ROW);

            xPosElement = xPosButton + (BUTTON_SIZE - TILE_WIDTH) / 2;
            yPosElement = yPosButton + (BUTTON_SIZE - TILE_HEIGHT) / 2;
            buttons.add(this);
        }

        @Override
        public void update() {
            int mouseX = mouseDetector.xPos;
            int mouseY = mouseDetector.yPos;
            boolean onButton = (mouseX >= xPosButton && mouseX <= xPosButton + BUTTON_SIZE &&
                    mouseY >= yPosButton && mouseY <= yPosButton + BUTTON_SIZE);
            if (onButton)
                mouseDetector.layer = 2;

            if (onButton && !selected && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                selected = true;
                for (Button button : buttons) {
                    if (button != this) {
                        button.selected = false;
                    }
                }
                BlockBuilder blockBuilder = EditorPane.this.gameObject.getComponent(BlockBuilder.class);
                blockBuilder.setBlockImage(element);
                blockBuilder.setBounds(bounds);
                blockBuilder.mode = 1;
            }
        }

        @Override
        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(SMALL_BUTTON_IMAGE, xPosButton, yPosButton, null);
            graphics2D.drawImage(AssetPool.getImage(element), xPosElement, yPosElement, null);
            if (selected)
                graphics2D.drawImage(SMALL_BUTTON_HOVER, xPosButton, yPosButton, null);
        }
    }

    public class DellItemButton extends Button {

        private String element;

        private final int xPosButton;
        private final int yPosButton;


        public DellItemButton(String element) {
            this.element = element;

            xPosButton = 1020;
            yPosButton = FIRST_BIG_BUTTON_POSITION_Y;

            buttons.add(this);
        }

        @Override
        public void update() {
            int mouseX = mouseDetector.xPos;
            int mouseY = mouseDetector.yPos;
            boolean onButton = (mouseX >= xPosButton && mouseX <= xPosButton + BUTTON_SIZE &&
                    mouseY >= yPosButton && mouseY <= yPosButton + BUTTON_SIZE);
            if (onButton)
                mouseDetector.layer = 2;

            if (onButton && !selected && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                selected = true;
                for (Button button : buttons) {
                    if (button != this) {
                        button.selected = false;
                    }
                }
                BlockBuilder blockBuilder = EditorPane.this.gameObject.getComponent(BlockBuilder.class);
                blockBuilder.setBlockImage(null);
                blockBuilder.mode = 2;

            }
        }

        @Override
        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(AssetPool.getImage(element), xPosButton, yPosButton, null);
            if (selected)
                graphics2D.drawImage(SMALL_BUTTON_HOVER, xPosButton, yPosButton, null);
        }
    }

    public class RotateItemButton extends Button {

        private String element;

        private final int xPosButton;
        private final int yPosButton;


        public RotateItemButton(String element) {
            this.element = element;

            xPosButton = 1105;
            yPosButton = FIRST_BIG_BUTTON_POSITION_Y;

            buttons.add(this);
        }

        @Override
        public void update() {
            int mouseX = mouseDetector.xPos;
            int mouseY = mouseDetector.yPos;
            boolean onButton = (mouseX >= xPosButton && mouseX <= xPosButton + BUTTON_SIZE &&
                    mouseY >= yPosButton && mouseY <= yPosButton + BUTTON_SIZE);
            if (onButton)
                mouseDetector.layer = 2;

            if (onButton && !selected && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                selected = true;
                for (Button button : buttons) {
                    if (button != this) {
                        button.selected = false;
                    }
                }
                BlockBuilder blockBuilder = EditorPane.this.gameObject.getComponent(BlockBuilder.class);
                blockBuilder.setBlockImage(null);
                blockBuilder.mode = 3;

            }
        }

        @Override
        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(AssetPool.getImage(element), xPosButton, yPosButton, null);
            if (selected)
                graphics2D.drawImage(SMALL_BUTTON_HOVER, xPosButton, yPosButton, null);
        }
    }

    public class ModeButton extends Button {

        private final int xPosButton;
        private final int yPosButton;

        BufferedImage buttonImage;

        String playerImage;
        String shipImage;
        String backgroundImage;
        String groundImage;
        Color backgroundColor;
        Color groundColor;

        public ModeButton(String buttonImage, String playerImage, String shipImage, String backgroundImage, String groundImage, Color backgroundColor, Color groundColor) {
            this.buttonImage = AssetPool.getImage(buttonImage);
            this.playerImage = playerImage;
            this.shipImage = shipImage;
            this.backgroundImage = backgroundImage;
            this.groundImage = groundImage;
            this.backgroundColor = backgroundColor;
            this.groundColor = groundColor;

            int buttonIndex = MODE_BUTTON_INDEX++;

            xPosButton = FIRST_MODE_BUTTON_POSITION_X + (BUTTON_SIZE + BUTTON_SPACING) * ((buttonIndex - 1) % MODE_BUTTONS_PER_ROW);
            yPosButton = FIRST_MODE_BUTTON_POSITION_Y + (BUTTON_SIZE + BUTTON_SPACING) * ((buttonIndex - 1) / MODE_BUTTONS_PER_ROW);

            modeButtons.add(this);

        }

        @Override
        public void update() {
            int mouseX = mouseDetector.xPos;
            int mouseY = mouseDetector.yPos;
            boolean onButton = (mouseX >= xPosButton && mouseX <= xPosButton + BUTTON_SIZE &&
                    mouseY >= yPosButton && mouseY <= yPosButton + BUTTON_SIZE);
            if (onButton)
                mouseDetector.layer = 2;

            if (onButton && !selected && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                selected = true;
                for (Button button : modeButtons) {
                    if (button != this) {
                        button.selected = false;
                    }
                    LevelScene levelScene = (LevelScene) (Window.getWindow().getCurrentScene());

                    Player player = ((LevelScene) (Window.getWindow().getCurrentScene())).getPlayer().getComponent(Player.class);
                    player.setPlayerImagePath(playerImage);
                    player.setShipImagePath(shipImage);

                    ParallaxBackground background = levelScene.getFirstLayerComponents().getComponent(ParallaxBackground.class);
                    background.setBackgroundImage(backgroundImage);
                    background.setColor(backgroundColor);

                    Ground ground = levelScene.getFirstLayerComponents().getComponent(Ground.class);
                    ground.setGroundImagePath(groundImage);
                    ground.setGroundColor(groundColor);
                }
            }
        }

        @Override
        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(buttonImage, xPosButton, yPosButton, null);
            if (selected)
                graphics2D.drawImage(SMALL_BUTTON_HOVER, xPosButton, yPosButton, null);
        }
    }


    public class BackButton extends Button {

        private String element;

        private final int xPosButton;
        private final int yPosButton;


        public BackButton(String element) {
            this.element = element;

            xPosButton = 1020;
            yPosButton = FIRST_BIG_BUTTON_POSITION_Y + BUTTON_SIZE + BUTTON_SPACING;

            buttons.add(this);
        }

        boolean ready;
        boolean pressed;
        @Override
        public void update() {
            int mouseX = mouseDetector.xPos;
            int mouseY = mouseDetector.yPos;
            boolean onButton = (mouseX >= xPosButton && mouseX <= xPosButton + BUTTON_SIZE &&
                    mouseY >= yPosButton && mouseY <= yPosButton + BUTTON_SIZE);
            if (onButton)
                mouseDetector.layer = 2;

            if (ready && onButton&& !mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                Scene scene = SceneFactory.createScene(2);
                scene.init();
                Window.getWindow().setScene(scene);
            }
            ready = onButton && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1;
        }

        @Override
        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(AssetPool.getImage(element), xPosButton, yPosButton, null);
            if (ready)
                graphics2D.drawImage(SMALL_BUTTON_HOVER, xPosButton, yPosButton, null);
        }
    }


    public class SaveButton extends Button {

        private String element;

        private final int xPosButton;
        private final int yPosButton;


        public SaveButton(String element) {
            this.element = element;

            xPosButton = 1105;
            yPosButton = FIRST_BIG_BUTTON_POSITION_Y + BUTTON_SIZE + BUTTON_SPACING;

            buttons.add(this);
        }

        boolean ready;
        @Override
        public void update() {
            int mouseX = mouseDetector.xPos;
            int mouseY = mouseDetector.yPos;
            boolean onButton = (mouseX >= xPosButton && mouseX <= xPosButton + BUTTON_SIZE &&
                    mouseY >= yPosButton && mouseY <= yPosButton + BUTTON_SIZE);
            if (onButton)
                mouseDetector.layer = 2;

            if (onButton && ready && !mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
                AssetPool.levels.add(((LevelEditorScene) Window.getWindow().getCurrentScene()).getLevelData());
            }

            ready = onButton && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1;
        }

        @Override
        public void draw(Graphics2D graphics2D) {
            graphics2D.drawImage(AssetPool.getImage(element), xPosButton, yPosButton, null);
            if (ready)
                graphics2D.drawImage(SMALL_BUTTON_HOVER, xPosButton, yPosButton, null);
        }
    }
}
