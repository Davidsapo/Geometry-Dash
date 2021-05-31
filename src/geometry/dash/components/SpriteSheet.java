package geometry.dash.components;

import geometry.dash.strucrures.AssetPool;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet {

    private final String path;
    private final ArrayList<String> subImagePaths;
    private final int items;

    public SpriteSheet(String fileName, int tileWidth, int tileHeight, int space, int items) {
        this.items = items;
        path = fileName;
        subImagePaths = new ArrayList<>();
        AssetPool.downloadImageToPool(fileName);
        BufferedImage sheet = AssetPool.getImage(fileName);
        int x = space;
        int y = space;
        int num = 1;
        while (y + tileHeight <= sheet.getHeight()) {
            if (x + tileWidth <= sheet.getWidth()) {
                BufferedImage subImage = sheet.getSubimage(x, y, tileWidth, tileHeight);
                String subName = fileName.replace(".png", ".sub" + num);
                subImagePaths.add(subName);
                AssetPool.setImageToPool(subName, subImage);
                x += tileWidth + space + 1;
                num++;
                if (num > items)
                    break;
                continue;
            }
            x = space;
            y += tileHeight + space + 1;
        }
    }

    public String getTilePath(int n) {
        return subImagePaths.get(n - 1);
    }

    public int getItems() {
        return items;
    }

    public String getPath() {
        return path;
    }
}
