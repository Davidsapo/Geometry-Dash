package geometry.dash.strucrures;

import geometry.dash.components.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AssetPool {

    private static final HashMap<String, BufferedImage> images = new HashMap<>();
    private static final ArrayList<SpriteSheet> spriteSheets = new ArrayList<>();

    public static BufferedImage getImage(String path) {
        //path = extractAbsolutePath(path);
        return images.get(path);
    }

    public static void downloadImageToPool(String path) {
        //path = extractAbsolutePath(path);
        try {
            images.put(path, ImageIO.read(new File(path)));
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(0);
        }
    }

    public static void setImageToPool(String path, BufferedImage image) {
        images.put(path, image);
    }

    public static void addSpriteSheet(SpriteSheet spriteSheet) {
        spriteSheets.add(spriteSheet);
    }

    public static SpriteSheet getSpriteSheet(String path) {
        for (SpriteSheet spriteSheet : spriteSheets) {
            if (extractAbsolutePath(spriteSheet.getPath()).equals(extractAbsolutePath(path)))
                return spriteSheet;
        }
        return null;
    }

    private static String extractAbsolutePath(String imageName) {
        return (imageName.endsWith(".png")) ? new File(imageName).getAbsolutePath() : imageName;
    }
}
