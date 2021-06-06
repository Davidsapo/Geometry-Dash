package geometry.dash.utils;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;

import java.io.File;

public class MediaPlayer {

    public static javafx.scene.media.MediaPlayer player;

    public static void init(String track){
        Media hit = new Media(new File(track).toURI().toString());
        player = new javafx.scene.media.MediaPlayer(hit);
    }

}
