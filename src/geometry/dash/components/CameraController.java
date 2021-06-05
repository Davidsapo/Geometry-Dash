package geometry.dash.components;

import geometry.dash.engine.Camera;
import geometry.dash.engine.Component;
import geometry.dash.engine.MouseDetector;
import geometry.dash.utils.Constants;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class CameraController extends Component {

    private Camera camera;
    private MouseDetector mouseDetector;
    private int prevXPos;
    private int prevYPos;

    public boolean onlyX = false;

    public CameraController(Camera camera, MouseDetector mouseDetector) {
        this.camera = camera;
        this.mouseDetector = mouseDetector;
        prevXPos = 0;
        prevYPos = 0;
    }

    @Override
    public void update() {
        if (mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON3) {
            camera.moveXAsic(-(mouseDetector.xPos - prevXPos));
            if (!onlyX) {
                camera.moveYAsic(-(mouseDetector.yPos - prevYPos));
            }
            if (camera.position.y > Constants.CAMERA_BUTTON_LIMIT)
                camera.position.y = Constants.CAMERA_BUTTON_LIMIT;
            if (camera.position.y < Constants.CAMERA_TOP_LIMIT)
                camera.position.y = Constants.CAMERA_TOP_LIMIT;
            if (camera.position.x < Constants.CAMERA_LEFT_LIMIT)
                camera.position.x = Constants.CAMERA_LEFT_LIMIT;
        }
        prevXPos = mouseDetector.xPos;
        prevYPos = mouseDetector.yPos;
    }
}
