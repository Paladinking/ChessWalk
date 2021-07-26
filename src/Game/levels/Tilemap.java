package Game.levels;

import Game.GameState;
import Game.assets.Image;
import Game.entities.Entity;
import Game.levels.Tiles.EmptyTile;
import Game.levels.Tiles.Tile;
import Game.levels.Tiles.WallTile;

import java.awt.*;
import java.util.ArrayList;


public abstract class Tilemap {
    private static ArrayList<Tile> visibleTiles = new ArrayList<>();
    private static Tile[][] tiles;

    public static void loadTiles(Level level) {
        update();
        tiles = LevelImageReader.generateLevel(level);

    }

    private static void update() {
        //Image.put(ImageID.BOARD_ID,new BoardImage(0,0,Image.BOARD));
    }

    public static Tile getTile(int x, int y) {
        if (x > 99 || x < 0 || y > 99 || y < 0) return new WallTile();
        return tiles[y][x];

    }

    public static Tile[][] getTiles() {
        return tiles;
    }

    public static void addEntity(int x, int y, Entity e) {
        tiles[y][x].setEntity(e);
    }

    public static void draw(Graphics g) {
        for (int i = GameState.globalY - 1; i < GameState.globalY + 16 *  GameState.initialSize / GameState.tileSize + 1; i++) {
            for (int j = GameState.globalX - 1; j < GameState.globalX + 16 *  GameState.initialSize / GameState.tileSize + 1; j++) {
                if(i<0||j<0||i>99||j>99) continue;
                if (tiles[i][j] instanceof EmptyTile) {
                    if(tiles[i][j].state== Tile.VISIBLE)
                    g.drawImage(Image.TILE, (j - GameState.globalX) * GameState.tileSize + GameState.offsetX + GameState.globalAdjX, (i - GameState.globalY) * GameState.tileSize + GameState.offsetY + GameState.globalAdjY, GameState.tileSize, GameState.tileSize, null);
                    else if(tiles[i][j].state==Tile.HIDDEN){
                        g.setColor(Color.darkGray);
                        g.fillRect((j - GameState.globalX) * GameState.tileSize + GameState.offsetX + GameState.globalAdjX,(i - GameState.globalY) * GameState.tileSize + GameState.offsetY + GameState.globalAdjY, GameState.tileSize, GameState.tileSize);
                    } else {
                        g.setColor(Color.BLACK);
                        g.fillRect((j - GameState.globalX) * GameState.tileSize + GameState.offsetX + GameState.globalAdjX,(i - GameState.globalY) * GameState.tileSize + GameState.offsetY + GameState.globalAdjY, GameState.tileSize, GameState.tileSize);
                    }
                }
            }
        }
        for (int i = GameState.globalY - 1; i < GameState.globalY + 16 *  GameState.initialSize / GameState.tileSize + 1; i++) {
            for (int j = GameState.globalX - 1; j < GameState.globalX + 16 *  GameState.initialSize / GameState.tileSize + 1; j++) {
                if(i<0||j<0||i>99||j>99) continue;
                if(tiles[i][j].state==Tile.VISIBLE)tiles[i][j].draw(g, j - GameState.globalX, i - GameState.globalY);
                //else g.fillRect((j - GameState.globalX) * GameState.tileSize + GameState.offsetX + GameState.globalAdjX,(i - GameState.globalY) * GameState.tileSize + GameState.offsetY + GameState.globalAdjY, GameState.tileSize, GameState.tileSize);
            }
        }
    }

    public static void removeEntity(int x, int y) {
        tiles[y][x].removeEntity();
    }

    public static void updateVision() {
        int x =GameState.playerX;
        int y =GameState.playerY;
        //for(Tile t:visibleTiles)t.state=Tile.HIDDEN;
        new Vision(GameState.playerX,GameState.playerY).calculateVision();

    }
    static void show(int y,int x){
        tiles[x][y].state = Tile.VISIBLE;
        visibleTiles.add(tiles[x][y]);
    }
    public static void showAll(){
        for(Tile[] ta: tiles){
            for(Tile t: ta){
                t.state = Tile.VISIBLE;
            }
        }
    }
    public static void hideAll(){
        for(Tile[] ta: tiles){
            for(Tile t: ta){
                t.state = Tile.HIDDEN;
            }
        }
    }

    public static Tile vGetTile(int x, int y) {
        return getTile(x,y);
    }
}
