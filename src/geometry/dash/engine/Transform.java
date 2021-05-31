package geometry.dash.engine;

import geometry.dash.utils.Vector;

import java.awt.geom.AffineTransform;
import java.io.Serializable;


public class Transform extends AffineTransform implements Serializable {

    private static final double RADIANS_360 = 6.283;
    private static final double RADIANS_90 = 1.571;

    private Vector position;
    private final double halfWidth;
    private final double halfHeight;
    private double rotate = 0;

    public Transform(Vector position, int width, int height) {
        halfWidth = width / 2.0;
        halfHeight = height / 2.0;
        this.position = position;
        setToTranslation(position.x, position.y);
    }

    public void moveXAsic(double step) {
        position.x += step;
        setToTranslation(position.x, position.y);
        super.rotate(rotate, halfWidth, halfHeight);
    }

    public void moveYAsic(double step) {
        position.y += step;
        setToTranslation(position.x, position.y);
        super.rotate(rotate, halfWidth, halfHeight);
    }

    public void rotateObject(double rotation) {
        setToTranslation(position.x, position.y);
        rotate += rotation;
        rotate = rotate % 6.283;
        super.rotate(rotate, halfWidth, halfHeight);
    }

    public void setDefaultRotationPosition() {
        if (rotate % RADIANS_90 != 0) {
            rotate = (int)(Math.round(rotate / RADIANS_90)) * RADIANS_90;
            setToTranslation(position.x, position.y);
            super.rotate(rotate, halfWidth, halfHeight);
        }
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
        setToTranslation(position.x, position.y);
        super.rotate(rotate, halfWidth, halfHeight);
    }
}
