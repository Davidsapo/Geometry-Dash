package geometry.dash.utils;

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

    public static final int CAMERA_BUTTON_LIMIT = 0;
    public static final int CAMERA_TOP_LIMIT = -GROUND_HEIGHT;
    public static final int CAMERA_LEFT_LIMIT = 0;

    public static final int BUTTONS_PER_ROW = 7;
    public static final int BUTTONS_PER_COLUMN = 1;
    public static final int TOTAL_BUTTONS = BUTTONS_PER_ROW * BUTTONS_PER_COLUMN;
    public static final int BUTTON_SIZE = 70;
    public static final int BUTTON_SPACING = 10;
    public static final int FIRST_BUTTON_POSITION_X = (SCREEN_WIDTH - BUTTONS_PER_ROW * (BUTTON_SIZE + BUTTON_SPACING)) / 2;
    public static final int FIRST_BUTTON_POSITION_Y = SCREEN_HEIGHT - (BUTTONS_PER_COLUMN * (BUTTON_SIZE + BUTTON_SPACING)) - 8;

    public static final int GRAVITY = 18;
    public static final int JUMP_FORCE = -13;
    public static final double ACCELERATION = 0.85;
    public static final int PLAYER_SPEED = 7;
    public static final double ROTATION = 0.094;
    public static final int SLEEP_TIME = 16;
    public static final int OVERLAP_POSSIBLE = 10;

    public static final double BACKGROUND_SPEED = 6.5;


    private Constants() {
    }
}
