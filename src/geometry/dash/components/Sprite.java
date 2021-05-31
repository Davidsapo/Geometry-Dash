package geometry.dash.components;

import geometry.dash.engine.Component;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.Constants;

import java.awt.*;

public class Sprite extends Component {

    private final String imagePath;
    private final int width;
    private final int height;

    public Sprite(String imagePath) {
        this.imagePath = imagePath;
        width = AssetPool.getImage(imagePath).getWidth();
        height = AssetPool.getImage(imagePath).getHeight();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        if (gameObject.visible)
            graphics2D.drawImage(AssetPool.getImage(imagePath), gameObject.getTransform(), null);
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
