package geometry.dash;

import geometry.dash.utils.Launcher;

public class Main {

    public static void main(String[] args) {
        Launcher.downloadAssets();
        Window window = Window.getWindow();
        window.init();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(window);
        thread.start();

    }
}
