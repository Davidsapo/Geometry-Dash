package geometry.dash.engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyDetector extends KeyAdapter {

    private boolean[] keyPressed = new boolean[128];

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed[e.getKeyCode()] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed[e.getKeyCode()] = true;
    }

    public boolean isKeyPressed(int keyCode) {
        return keyPressed[keyCode];
    }
}
