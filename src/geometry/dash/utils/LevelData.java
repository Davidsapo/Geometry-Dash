package geometry.dash.utils;

import geometry.dash.engine.GameObject;
import geometry.dash.engine.Vector;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class LevelData implements Serializable {

    public  ArrayList<GameObject> gameObjects;
    public ArrayList<Vector> positions;
    public String playerImage;
    public String shipImage;
    public String backgroundImage;
    public String groundImage;
    public Color backgroundColor;
    public Color groundColor;
    public String track;

}
