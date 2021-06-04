package geometry.dash.utils;

import java.awt.*;

public final class Constants {

    public static final String WINDOW_TITLE = "GEOMETRY DASH";
    public static final int TOP_BORDER_HEIGHT = 25;

    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 625;

    public static final int PLAYER_WIDTH = 50;
    public static final int PLAYER_HEIGHT = 50;
    public static final int PLAYER_SPAWN_X = 0;
    public static final int PLAYER_SPAWN_Y = SCREEN_HEIGHT - 150 - PLAYER_HEIGHT;

    public static final int GROUND_HEIGHT = 150;

    public static final int CAMERA_OFFSET_X = 300;
    public static final int CAMERA_OFFSET_Y = SCREEN_HEIGHT - GROUND_HEIGHT - PLAYER_HEIGHT;

    public static final int TILE_WIDTH = 50;
    public static final int TILE_HEIGHT = 50;

    public static final int CAMERA_BUTTON_LIMIT = 50;
    public static final int CAMERA_TOP_LIMIT = -(1024 - GROUND_HEIGHT);
    public static final int CAMERA_LEFT_LIMIT = 0;

    public static final int GRAVITY = 18;
    public static final int SHIP_GRAVITY = 5;
    public static final int SHIP_FORCE = -5;
    public static final double SHIP_ACCELERATION = 0.5;
    public static final int JUMP_FORCE = -13;
    public static final int JUMPER_FORCE = -18;
    public static final double ACCELERATION = 0.85;
    public static final int PLAYER_SPEED = 7;
    public static final double ROTATION = 0.094;
    public static final int SLEEP_TIME = 16;
    public static final int OVERLAP_POSSIBLE = 10;

    public static final double BACKGROUND_SPEED = 6.5;

    public static final Color BLUE_BACKGROUND = new Color(44, 61, 245);
    public static final Color BLUE_GROUND = new Color(3, 18, 192);

    public static final Color GREEN_BACKGROUND = new Color(0, 255, 0);
    public static final Color GREEN_GROUND = new Color(0, 205, 0);

    public static final Color YELLOW_BACKGROUND = new Color(255, 255, 0);
    public static final Color YELLOW_GROUND = new Color(205, 205, 0);

    public static final Color RED_BACKGROUND = new Color(255, 0, 0);
    public static final Color RED_GROUND = new Color(205, 0, 0);



    private Constants() {
    }
}
