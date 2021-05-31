package geometry.dash.engine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseDetector extends MouseAdapter {

    public int xPos;
    public int yPos;

    public int xDist;
    public int yDist;

    public int button;

    public boolean pressed;
    public boolean dragged;

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
        button = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
        dragged = false;
        xDist = 0;
        yDist = 0;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragged = true;
        /*xDist = e.getX() - xPos;
        yDist = e.getY() - yPos;*/
        xPos = e.getX();
        yPos = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        xPos = e.getX();
        yPos = e.getY();
    }
}
