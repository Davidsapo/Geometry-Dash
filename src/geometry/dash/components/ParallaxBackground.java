package geometry.dash.components;

import geometry.dash.engine.Camera;
import geometry.dash.engine.Component;
import geometry.dash.strucrures.AssetPool;

import static geometry.dash.utils.Constants.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ParallaxBackground extends Component {

    private final BufferedImage backgroundImage;
    private final Camera camera;
    private final Color color;
    public boolean movable;
    private final int backGroundWidth;
    private final int backGroundHeight;
    private double xPos;
    private int yPos;
    private int xDrawPos;
    private int yDrawPos;


    public ParallaxBackground(String imagePath, Camera camera, Color color, boolean movable) {
        this.camera = camera;
        this.color = color;
        this.movable = movable;
        backgroundImage = AssetPool.getImage(imagePath);
        backGroundWidth = backgroundImage.getWidth();
        backGroundHeight = backgroundImage.getHeight();
        xPos = 0;
        yPos = SCREEN_HEIGHT - (GROUND_HEIGHT + backgroundImage.getHeight());
        xDrawPos = (int)xPos;
        yDrawPos = yPos;
    }

    @Override
    public void update() {
        if (camera.position.y < yPos)
            camera.position.y = yPos;
        if (camera.position.x == 0)
            xPos = 0;
        int camPos = (int) camera.position.x - (int)xPos;
        xDrawPos = (camPos / backGroundWidth) * backGroundWidth - camPos;
        yDrawPos = yPos - (int) camera.position.y;

        if (movable && camPos != 0)
            xPos += BACKGROUND_SPEED;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(color);
        graphics2D.fillRect(0, yDrawPos, SCREEN_WIDTH, backGroundHeight);
        while (xDrawPos <= SCREEN_WIDTH) {
            graphics2D.drawImage(backgroundImage, xDrawPos, yDrawPos, null);
            xDrawPos += backGroundWidth;
        }
    }
}
