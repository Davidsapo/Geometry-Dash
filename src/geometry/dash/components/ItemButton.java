package geometry.dash.components;

import geometry.dash.engine.Component;
import geometry.dash.engine.MouseDetector;
import geometry.dash.Window;
import geometry.dash.strucrures.AssetPool;

import static geometry.dash.utils.Constants.*;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ItemButton extends Component {

    private final EditorPane parent;
    private final MouseDetector mouseDetector;

    private final String element;
    private final String button;
    private final String hover;

    private final Bounds bounds;

    private final int xPosButton;
    private final int yPosButton;
    private final int xPosElement;
    private final int yPosElement;

    public boolean selected;

    public ItemButton(int buttonPos, String element, String button, String hover, Bounds bounds, EditorPane editorPane) {
        parent = editorPane;
        this.element = element;
        this.button = button;
        this.hover = hover;
        this.bounds = bounds;

        mouseDetector = Window.getWindow().getMouseDetector();

        xPosButton = FIRST_BUTTON_POSITION_X + (BUTTON_SIZE + BUTTON_SPACING) * ((buttonPos - 1) % BUTTONS_PER_ROW);
        yPosButton = FIRST_BUTTON_POSITION_Y + (BUTTON_SIZE + BUTTON_SPACING) * ((buttonPos - 1) / BUTTONS_PER_ROW);

        xPosElement = xPosButton + (BUTTON_SIZE - TILE_WIDTH) / 2;
        yPosElement = yPosButton + (BUTTON_SIZE - TILE_HEIGHT) / 2;
    }

    @Override
    public void update() {
        if (!selected && mouseDetector.pressed && mouseDetector.button == MouseEvent.BUTTON1) {
            int mouseX = mouseDetector.xPos;
            int mouseY = mouseDetector.yPos;
            if (mouseX >= xPosButton && mouseX <= xPosButton + BUTTON_SIZE &&
                    mouseY >= yPosButton && mouseY <= yPosButton + BUTTON_SIZE) {
                selected = true;
                for (ItemButton button : parent.getButtons()) {
                    if (button != this)
                        button.selected = false;
                }
                BlockBuilder blockBuilder = parent.getParentGameObject().getComponent(BlockBuilder.class);
                blockBuilder.setBlockImage(element);
                blockBuilder.setBounds(bounds);
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(AssetPool.getImage(button), xPosButton, yPosButton, null);
        graphics2D.drawImage(AssetPool.getImage(element), xPosElement, yPosElement, null);
        if (selected)
            graphics2D.drawImage(AssetPool.getImage(hover), xPosButton, yPosButton, null);
    }
}
