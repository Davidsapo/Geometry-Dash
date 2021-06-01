package geometry.dash.scenes;

import geometry.dash.engine.Scene;

public class SceneFactory {

    public static Scene createScene(int scene) {
        switch (scene) {
            case 0:
                return new LevelRunScene("Level scene");
            case 1:
                return new LevelEditorScene("Level editor scene");
            default:
                return null;
        }
    }
}
