package geometry.dash;

import geometry.dash.engine.KeyDetector;
import geometry.dash.engine.MouseDetector;
import geometry.dash.engine.Scene;
import geometry.dash.scenes.SceneFactory;
import geometry.dash.strucrures.AssetPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(AssetPool.levels.size());
                AssetPool.writeLevelsToFile();
            }
        });
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
        scene = SceneFactory.createScene(3);
        scene.init();
        bufferingImage = createImage(getWidth(), getHeight());
        bufferingGraphics = (Graphics2D) bufferingImage.getGraphics();
    }


    public void update() {
        mouseDetector.layer = 1;
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

        long st = System.nanoTime();
        int c = 0;
        while (isRunning) {
            lastTime = System.nanoTime();
                update();
            time = System.nanoTime();
            dt = TimeUnit.MILLISECONDS.convert(time - lastTime, TimeUnit.NANOSECONDS);

            sleep = SLEEP_TIME - dt;
            if (sleep < 0)
                sleep = 0;
            int stop;
            if (c%2==0)
                stop = 0;
            else stop = 1;
            sleep(sleep+stop);


            long curr = System.nanoTime();
            c++;
            if (TimeUnit.MILLISECONDS.convert(curr - st, TimeUnit.NANOSECONDS) > 1000) {
                //System.out.println(c);
                c = 0;
                st = curr;
            }
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
