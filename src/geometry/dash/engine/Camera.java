package geometry.dash.engine;

public class Camera {


    public final Vector position;

    public Camera(Vector position) {
        this.position = position;
    }

    public void moveXAsic(int pixels){
        position.x+=pixels;
    }

    public void moveYAsic(int pixels){
        position.y+=pixels;
    }
}
