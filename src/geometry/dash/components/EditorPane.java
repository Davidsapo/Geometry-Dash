package geometry.dash.components;

import geometry.dash.engine.Component;
import geometry.dash.engine.GameObject;
import geometry.dash.strucrures.AssetPool;
import geometry.dash.utils.Constants;

import java.awt.*;
import java.util.ArrayList;

public class EditorPane extends Component {

    private static final SpriteSheet buttonsSheet = AssetPool.getSpriteSheet("assets\\buttons\\buttons2.png");

    private final ArrayList<ItemButton> buttons;

    public EditorPane(SpriteSheet elements) {
        buttons = new ArrayList<>();
        int items = Math.min(elements.getItems(), Constants.TOTAL_BUTTONS);
        BoxBounds boxBounds = new BoxBounds(Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
        for (int n = 1; n <= items; n++)
            buttons.add(new ItemButton(n, elements.getTilePath(n), buttonsSheet.getTilePath(2), buttonsSheet.getTilePath(1), boxBounds, this));
    }

    @Override
    public void update() {
        for (ItemButton button : buttons)
            button.update();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        for (ItemButton button : buttons)
            button.draw(graphics2D);
    }

    public ArrayList<ItemButton> getButtons() {
        return buttons;
    }

    public GameObject getParentGameObject(){
        return gameObject;
    }
}
