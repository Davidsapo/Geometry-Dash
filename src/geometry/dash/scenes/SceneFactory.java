package geometry.dash.scenes;

import geometry.dash.engine.Scene;

public class SceneFactory {

    public static Scene createScene(int scene) {
        switch (scene) {
            case 0:
                return new LevelRunScene("Level scene");
            case 1:
                return LevelEditorScene.getInstance();

            case 3:
                return new StartScene("");
            default:
                return null;
        }
    }
}
