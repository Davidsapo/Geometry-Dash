package geometry.dash;

import geometry.dash.utils.Launcher;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Launcher.downloadAssets();
        Window window = Window.getWindow();
        window.init();
        Thread.sleep(1000);
        window.setVisible(true);
        Thread thread = new Thread(window);
        thread.start();

    }
}
