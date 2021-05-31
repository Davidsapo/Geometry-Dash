package geometry.dash.utils;

import geometry.dash.components.SpriteSheet;
import geometry.dash.strucrures.AssetPool;

public final class Launcher {

    public static void downloadAssets(){
        AssetPool.downloadImageToPool("assets/ground/ground01.png");
        SpriteSheet playerSkins = new SpriteSheet("assets\\player\\playerSkins2.png", 50, 50, 1, 4);
        SpriteSheet blocks = new SpriteSheet("assets\\blocks\\blocks.png", 50, 50, 1, 7);
        SpriteSheet buttonsSheet = new SpriteSheet("assets\\buttons\\buttons2.png", 70, 70, 1, 2);
        AssetPool.addSpriteSheet(playerSkins);
        AssetPool.addSpriteSheet(blocks);
        AssetPool.addSpriteSheet(buttonsSheet);
    }
}

