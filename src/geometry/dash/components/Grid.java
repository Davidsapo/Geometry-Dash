package geometry.dash.components;

import geometry.dash.LevelScene;
import geometry.dash.engine.Camera;
import geometry.dash.engine.Component;
import geometry.dash.Window;
import geometry.dash.utils.Constants;

import static geometry.dash.utils.Constants.*;

import java.awt.*;

public class Grid extends Component {

    private Camera camera;
    private int tileWidth;
    private int tileHeight;

    public Grid() {
        camera = ((LevelScene)Window.getWindow().getCurrentScene()).getCamera();
        tileWidth = Constants.TILE_WIDTH;
        tileHeight = Constants.TILE_HEIGHT;
    }

    public Grid(Camera camera) {
        this.camera = camera;
        tileWidth = Constants.TILE_WIDTH;
        tileHeight = Constants.TILE_HEIGHT;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.setColor(Color.DARK_GRAY);

        int camPosX = (int)camera.position.x;
        int x = ((camPosX / tileWidth) * tileWidth) - camPosX;
        while (x <= SCREEN_WIDTH) {
            graphics2D.drawLine(x, 0, x, SCREEN_HEIGHT);
            x += tileWidth;
        }

        int camPosY = (int)camera.position.y + TOP_BORDER_HEIGHT;
        int y = (camPosY / TILE_HEIGHT) * TILE_HEIGHT - camPosY;
        while (y <= SCREEN_HEIGHT) {
            graphics2D.drawLine(0, y, SCREEN_WIDTH, y);
            y +=tileHeight;
        }
    }
}
