package geometry.dash;

import geometry.dash.utils.Launcher;
import java.io.File;

import geometry.dash.utils.MediaPlayer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;


public class Main {


    public static void main(String[] args) throws InterruptedException {
        JFXPanel fxPanel = new JFXPanel();

        Launcher.downloadAssets();
        Window window = Window.getWindow();
        window.init();
        Thread.sleep(1000);
        window.setVisible(true);
        Thread thread = new Thread(window);
        thread.start();


        /*String path = "music/track1.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);*/

    }
}
