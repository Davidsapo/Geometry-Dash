package geometry.dash.components;

import geometry.dash.Main;
import geometry.dash.scenes.LevelEditorScene;
import geometry.dash.scenes.LevelRunScene;
import geometry.dash.engine.*;
import geometry.dash.engine.Component;
import geometry.dash.Window;
import geometry.dash.scenes.LevelScene;
import geometry.dash.scenes.MainScene;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ground extends Component {

    private  String groundImagePath;
    private final Camera camera;
    private  Color groundColor;
    private final int width;
    private int yGroundPos;

    public Ground(String groundImagePath, Camera camera, Color groundColor) {
        this.groundImagePath = groundImagePath;
        this.camera = camera;
        this.groundColor = groundColor;
        BufferedImage groundImage = AssetPool.getImage(groundImagePath);
        width = groundImage.getWidth();
        int height = groundImage.getHeight();
    }

    @Override
    public void update() {
        yGroundPos = Constants.SCREEN_HEIGHT - Constants.GROUND_HEIGHT - (int) camera.position.y;
        Scene scene = Window.getWindow().getCurrentScene();
        if ((scene instanceof LevelEditorScene || scene instanceof MainScene))
            return;
        GameObject player = ((LevelScene) scene).getPlayer();
        int playerHeight = player.getComponent(BoxBounds.class).height;
        if (player.getTransform().getPosition().y + playerHeight >= yGroundPos) {
            player.getTransform().getPosition().y = yGroundPos - playerHeight;
            player.getComponent(Player.class).onGround = true;
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int startX = ((int) camera.position.x / width * width - (int) camera.position.x);
        graphics2D.setColor(groundColor);
        graphics2D.fillRect(0, yGroundPos, Constants.SCREEN_WIDTH, Constants.GROUND_HEIGHT + 50);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawLine(0, yGroundPos, Constants.SCREEN_WIDTH, yGroundPos);
        BufferedImage groundImage = AssetPool.getImage(groundImagePath);
        while (startX < Constants.SCREEN_WIDTH) {
            graphics2D.drawImage(groundImage, startX, yGroundPos, null);
            startX += width;
        }
    }

    public void setGroundImagePath(String groundImagePath) {
        this.groundImagePath = groundImagePath;
    }

    public void setGroundColor(Color groundColor) {
        this.groundColor = groundColor;
    }

    public String getGroundImagePath() {
        return groundImagePath;
    }

    public Color getGroundColor() {
        return groundColor;
    }
}
