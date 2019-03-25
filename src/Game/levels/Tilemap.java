package Game.levels;

import Game.assets.Image;
import Game.assets.ImageID;
import Game.entities.Entity;
import Game.levels.Tiles.Tile;
import Game.levels.Tiles.WallTile;


public class Tilemap {

    private static Tile[][] tiles;

    public static void loadTiles(Level level){
        update();
        tiles = LevelImageReader.readLevelImage(level);

    }
    private static void update(){
        Image.put(ImageID.BOARD_ID,new Image(0,-50,Image.BOARD));
    }
    public static Tile getTile(int x,int y){
        if(x>15||x<0||y>15||y<0) return new WallTile();
        return tiles[y][x];

    }
    public static Tile[][] getTiles(){
        return tiles;
    }

    public static void addEntity(int x, int y, Entity e){
        tiles[y][x].setEntity(e);
    }

    public static void removeEntity(int x,int y){
        tiles[y][x].removeEntity();
    }
}
