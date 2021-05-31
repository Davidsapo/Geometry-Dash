package geometry.dash;

import geometry.dash.engine.KeyDetector;
import geometry.dash.engine.MouseDetector;
import geometry.dash.engine.Scene;

import javax.swing.*;
import java.awt.*;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

import static geometry.dash.utils.Constants.*;

public class Window extends JFrame implements Runnable {

    private static Window window = null;

    private MouseDetector mouseDetector;
    private KeyDetector keyDetector;

    private Image bufferingImage;
    private Graphics2D bufferingGraphics;

    private Scene scene;
    private boolean isRunning = true;

    private Window() {
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setTitle(WINDOW_TITLE);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static Window getWindow() {
        if (window == null)
            window = new Window();
        return window;
    }

    public void init() {
        mouseDetector = new MouseDetector();
        keyDetector = new KeyDetector();
        addMouseListener(mouseDetector);
        addKeyListener(keyDetector);
        addMouseMotionListener(mouseDetector);
        scene = SceneFactory.createScene(1);
        scene.init();

        bufferingImage = createImage(getWidth(), getHeight());
        bufferingGraphics = (Graphics2D) bufferingImage.getGraphics();
    }


    public void update() {
        scene.update();
        draw(getGraphics());
    }

    public void draw(Graphics graphics) {
        render(bufferingGraphics);
        graphics.drawImage(bufferingImage, 0, 0, null);
    }

    public void render(Graphics2D graphics) {
        graphics.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.draw(graphics);
    }

    @Override
    public void run() {
        long lastTime;
        long time;
        long dt;
        long sleep;
        while (isRunning) {
            lastTime = System.nanoTime();
            update();
            time = System.nanoTime();
            dt = TimeUnit.MILLISECONDS.convert(time - lastTime, TimeUnit.NANOSECONDS);

            sleep = SLEEP_TIME - dt;
            if (sleep < 0)
                sleep = 0;
            sleep(sleep);
            //System.out.println(dt);

        }
    }


    private void sleep(long sec) {
        try {
            Thread.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public Scene getCurrentScene() {
        return scene;
    }

    public KeyDetector getKeyDetector() {
        return keyDetector;
    }

    public MouseDetector getMouseDetector() {
        return mouseDetector;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
