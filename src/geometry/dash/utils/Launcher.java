package geometry.dash.utils;

import geometry.dash.components.SpriteSheet;
import geometry.dash.strucrures.AssetPool;

public final class Launcher {

    public static void downloadAssets(){
        AssetPool.downloadImageToPool("assets/ground/ground01.png");
        AssetPool.downloadImageToPool("assets/editor_pane.png");
        AssetPool.downloadImageToPool("assets/blue_mod/background.png");
        AssetPool.downloadImageToPool("assets/background/background.png");
        AssetPool.downloadImageToPool("assets/blocks/platform2.png");
        AssetPool.downloadImageToPool("assets/blocks/big_spike.png");
        AssetPool.downloadImageToPool("assets/blocks/small_spike.png");
        AssetPool.downloadImageToPool("assets/blocks/fly_jumper.png");
        AssetPool.downloadImageToPool("assets/blocks/jumper.png");
        AssetPool.downloadImageToPool("assets/blocks/ship.png");

        AssetPool.downloadImageToPool("assets\\buttons\\big_button.png");
        AssetPool.downloadImageToPool("assets\\buttons\\big_hover.png");
        AssetPool.downloadImageToPool("assets\\buttons\\small_button.png");
        AssetPool.downloadImageToPool("assets\\buttons\\small_hover.png");

        AssetPool.downloadImageToPool("assets\\buttons\\del_button.png");

        AssetPool.downloadImageToPool("assets/blocks/portal2.png");
        AssetPool.downloadImageToPool("assets/blocks/die_floor.png");
        AssetPool.downloadImageToPool("assets\\buttons\\rotate_image.png");

        AssetPool.downloadImageToPool("assets\\buttons\\blue_button.png");
        AssetPool.downloadImageToPool("assets\\buttons\\green_button.png");
        AssetPool.downloadImageToPool("assets\\buttons\\yellow_button.png");
        AssetPool.downloadImageToPool("assets\\buttons\\red_button.png");
        AssetPool.downloadImageToPool("assets\\buttons\\back_button.png");
        AssetPool.downloadImageToPool("assets\\buttons\\save_button.png");


        AssetPool.downloadImageToPool("assets\\blue_mod\\cube.png");
        AssetPool.downloadImageToPool("assets\\blue_mod\\ship.png");
        AssetPool.downloadImageToPool("assets\\blue_mod\\background.png");
        AssetPool.downloadImageToPool("assets\\blue_mod\\ground.png");

        AssetPool.downloadImageToPool("assets\\green_mod\\cube.png");
        AssetPool.downloadImageToPool("assets\\green_mod\\ship.png");
        AssetPool.downloadImageToPool("assets\\green_mod\\background.png");
        AssetPool.downloadImageToPool("assets\\green_mod\\ground.png");

        AssetPool.downloadImageToPool("assets\\yellow_mode\\cube.png");
        AssetPool.downloadImageToPool("assets\\yellow_mode\\ship.png");
        //AssetPool.downloadImageToPool("assets\\yellow_mode\\background.png");
       //AssetPool.downloadImageToPool("assets\\yellow_mode\\ground.png");

        AssetPool.downloadImageToPool("assets\\red_mode\\cube.png");
        AssetPool.downloadImageToPool("assets\\red_mode\\ship.png");
        AssetPool.downloadImageToPool("assets\\red_mode\\background.png");
        AssetPool.downloadImageToPool("assets\\red_mode\\ground.png");

        AssetPool.downloadImageToPool("assets\\title.png");
        AssetPool.downloadImageToPool("assets\\start.png");
        AssetPool.downloadImageToPool("assets\\sign.png");





        SpriteSheet blocks = new SpriteSheet("assets\\blocks\\blocks.png", 50, 50, 1, 7);
        AssetPool.addSpriteSheet(blocks);
    }
}

