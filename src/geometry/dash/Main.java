package geometry.dash;

import geometry.dash.engine.Window;
import geometry.dash.utils.Launcher;

public class Main {

    public static void main(String[] args) {
        Launcher.downloadAssets();
        Window window = Window.getWindow();
        window.init();

        Thread thread = new Thread(window);
        thread.start();

    }
}
