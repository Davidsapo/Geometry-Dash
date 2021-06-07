package geometry.dash.components;

import geometry.dash.engine.Camera;
import geometry.dash.engine.Component;
import geometry.dash.strucrures.AssetPool;

import static geometry.dash.utils.Constants.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ParallaxBackground extends Component {

    private String backgroundImage;
    private final Camera camera;
    private Color color;
    public boolean movable;
    private final int backGroundWidth;
    private final int backGroundHeight;
    private double xPos;
    private int yPos;
    private int xDrawPos;
    private int yDrawPos;

    private boolean secondLayer = false;

    BufferedImage reverse;


    public ParallaxBackground(String imagePath, Camera camera, Color color, boolean movable) {
        this.camera = camera;
        this.color = color;
        this.movable = movable;
        setBackgroundImage(imagePath);
        backGroundWidth = AssetPool.getImage(backgroundImage).getWidth();
        backGroundHeight = AssetPool.getImage(backgroundImage).getHeight();
        xPos = 0;
        yPos = SCREEN_HEIGHT - (GROUND_HEIGHT + AssetPool.getImage(backgroundImage).getHeight());
        xDrawPos = (int) xPos;
        yDrawPos = 25;
    }

    @Override
    public void update() {
        if (camera.position.x == 0)
            xPos = 0;
        int camPos = (int) camera.position.x - (int) xPos;
        xDrawPos = (camPos / backGroundWidth) * backGroundWidth - camPos;

        if (movable && camPos != 0)
            xPos += BACKGROUND_SPEED;

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(color);
        graphics2D.fillRect(0, yDrawPos, SCREEN_WIDTH, 600);
        while (xDrawPos < SCREEN_WIDTH) {
            BufferedImage image = AssetPool.getImage(backgroundImage);
            if (xDrawPos < 0) {
                int xStart = -xDrawPos;
                image = image.getSubimage(-xDrawPos, 0, image.getWidth() - xStart, image.getHeight());
                graphics2D.drawImage(image, 0, yDrawPos, null);
                //if (camera.position.y < -63)
                    //graphics2D.drawImage(reverse.getSubimage(-xDrawPos, 0, reverse.getWidth() - xStart, reverse.getHeight()), 0, 537, null);

            } else {
                int imageEnd = xDrawPos + image.getWidth();
                int imageWidth = image.getWidth();
                if (imageEnd > SCREEN_WIDTH)
                    imageWidth -= imageEnd - SCREEN_WIDTH;
                image = image.getSubimage(0, 0, imageWidth, image.getHeight());
                graphics2D.drawImage(image, xDrawPos, yDrawPos, null);
                //if (camera.position.y < -63)
                    //graphics2D.drawImage(reverse.getSubimage(0, 0, imageWidth, reverse.getHeight()), xDrawPos, 537, null);

            }
            xDrawPos += backGroundWidth;
        }
    }

    public void setBackgroundImage(String backgroundImage) {
        BufferedImage image = AssetPool.getImage(backgroundImage);
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -image.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = op.filter(image, null);
        image = image.getSubimage(0,0,image.getWidth(), 88);
        this.backgroundImage = backgroundImage;
        reverse = image;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getBackgroundImage() {

        return backgroundImage;
    }

    public Color getColor() {
        return color;
    }

    public void setxDrawPos(int xDrawPos) {
        this.xDrawPos = xDrawPos;
    }
}
