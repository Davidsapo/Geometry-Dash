package geometry.dash.strucrures;

import geometry.dash.components.SpriteSheet;
import geometry.dash.utils.LevelData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AssetPool {

    private static final HashMap<String, BufferedImage> images = new HashMap<>();
    private static final ArrayList<SpriteSheet> spriteSheets = new ArrayList<>();

    public static ArrayList<LevelData> levels;

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

    public static void readLevelsFromFile() {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream("levels/levels_data.lvl"))) {
            levels = (ArrayList<LevelData>) (stream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            levels = new ArrayList<>();
        }

    }

    public static void writeLevelsToFile() {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("levels/levels_data.lvl"))) {
            stream.writeObject(levels);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
