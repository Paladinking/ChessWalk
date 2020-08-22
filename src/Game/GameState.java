package Game;

import java.awt.*;
import java.util.HashMap;

/**
 * @noinspection ALL
 */
public abstract class GameState {
    public static int mouseX = -1, mouseY = -1;
    public static int selected;
    public static int t,turns;
    public static HashMap<Integer, Boolean> keyStates;
    public static boolean playerTurn, clearMovement, mouseRelease;
    public static int currentLevel;
    public static int globalX=40,globalY=40;

    public static boolean scroling = false;
    public static int inMouseX,inMouseY;
    public static int globalAdjX,globalAdjY;

    public static int playerX=42, playerY=42;

    public static final int offsetX = 0, offsetY = 0;//Changing these for now makes the game break
    public static final int boardWidth = 800, boardHeight = 800;
    public static int tileSize = 50;
    public static final int startX = globalX+1,startY=globalY+8;
    public static final int maxSize = 100, minSize =20,initialSize = 50,mapWidth=100,mapHeight=100;
}
