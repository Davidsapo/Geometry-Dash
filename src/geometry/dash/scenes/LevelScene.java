package geometry.dash.scenes;

import static geometry.dash.utils.Constants.*;

import geometry.dash.Window;
import geometry.dash.components.*;
import geometry.dash.engine.*;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.engine.Vector;

import java.awt.*;
import java.util.ArrayList;

public abstract class LevelScene implements Scene {

    protected String name;
    protected Camera camera;
    protected GameObject player;
    protected GameObject firstLayerComponents;
    protected Renderer secondLayerRender;
    protected GameObject thirdLayerComponents;
    protected ArrayList<GameObject> gameObjects;
    protected EditorPane editorPane;
    public String track;

    public LevelScene(String name) {
        this.name = name;
        camera = new Camera(new Vector());
        secondLayerRender = new Renderer(camera);
        gameObjects = new ArrayList<>();
        player = new GameObject("Player", new Transform(new Vector(PLAYER_SPAWN_X, PLAYER_SPAWN_Y), PLAYER_WIDTH, PLAYER_HEIGHT));
        firstLayerComponents = new GameObject("First layer", null);
        thirdLayerComponents = new GameObject("SecondLayer", null);
    }

    public void init() {
        player.addComponent(new Player("assets\\blue_mod\\cube.png", "assets/blocks/ship.png", Window.getWindow().getKeyDetector(), false));
        player.setNonSerializable();
        secondLayerRender.setPlayer(player);

        firstLayerComponents.addComponent(new ParallaxBackground("assets/background/background.png", camera, new Color(44, 61, 245), false).initLayer(1));
        firstLayerComponents.addComponent(new Ground("assets/ground/ground01.png", camera, new Color(3, 18, 192)).initLayer(3));
        initEditorPane();
    }

    protected void initEditorPane() {
        editorPane = (EditorPane) new EditorPane("assets/editor_pane.png").initLayer(1);
        SpriteSheet blocs = AssetPool.getSpriteSheet("assets\\blocks\\blocks.png");
        BoxBounds blockBounds = new BoxBounds(TILE_WIDTH, TILE_HEIGHT);
        for (String image : blocs)
            editorPane.new SmallItemButton(image, blockBounds);
        editorPane.new SmallItemButton("assets/blocks/platform2.png", new PlatformBounds(50, 50));
        editorPane.new SmallItemButton("assets/blocks/big_spike.png", new TriangleBounds(50, 50));
        editorPane.new SmallItemButton("assets/blocks/small_spike.png", new TriangleBounds(50, 23));
        editorPane.new SmallItemButton("assets/blocks/fly_jumper.png", new FlyJumperBounds(50, 50));
        editorPane.new SmallItemButton("assets/blocks/jumper.png", new JumpPanelBounds(50, 10));

        editorPane.new BigItemButton("assets/blocks/portal2.png", new PortalBounds(50, 100));
        editorPane.new BigItemButton("assets/blocks/die_floor.png", new SpikesBounds(150, 50));

        editorPane.new DellItemButton("assets\\buttons\\del_button.png");
        editorPane.new RotateItemButton("assets\\buttons\\rotate_image.png");
        editorPane.new BackButton("assets\\buttons\\back_button.png");
        editorPane.new SaveButton("assets\\buttons\\save_button.png");

        editorPane.new ModeButton("assets\\buttons\\blue_button.png", "assets\\blue_mod\\cube.png", "assets\\blue_mod\\ship.png", "assets\\blue_mod\\background.png", "assets\\blue_mod\\ground.png", BLUE_BACKGROUND, BLUE_GROUND, "music/track1.mp3");
        editorPane.new ModeButton("assets\\buttons\\green_button.png", "assets\\green_mod\\cube.png", "assets\\green_mod\\ship.png", "assets\\green_mod\\background.png", "assets\\green_mod\\ground.png", GREEN_BACKGROUND, GREEN_GROUND, "music/track2.mp3");
        editorPane.new ModeButton("assets\\buttons\\yellow_button.png", "assets\\yellow_mode\\cube.png", "assets\\yellow_mode\\ship.png", "assets\\yellow_mode\\background.png", "assets\\blue_mod\\ground.png", YELLOW_BACKGROUND, YELLOW_GROUND, "music/track3.mp3");
        editorPane.new ModeButton("assets\\buttons\\red_button.png", "assets\\red_mode\\cube.png", "assets\\red_mode\\ship.png", "assets\\red_mode\\background.png", "assets\\red_mode\\ground.png", RED_BACKGROUND, RED_GROUND, "music/track4.mp3");


    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        secondLayerRender.submit(gameObject);
    }

    public Camera getCamera() {
        return camera;
    }

    public GameObject getPlayer() {
        return player;
    }


    public void deleteObject(GameObject object) {
        gameObjects.remove(object);
        secondLayerRender.getGameObjects().remove(object);
    }

    public GameObject getFirstLayerComponents() {
        return firstLayerComponents;
    }

    public GameObject getThirdLayerComponents() {
        return thirdLayerComponents;
    }
}
