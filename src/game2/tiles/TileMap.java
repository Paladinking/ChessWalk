package game2.tiles;

import game2.entities.Entity;
import game2.entities.Player;
import game2.levels.Level;

import java.awt.*;

public class TileMap {
    
    private final Tile[] tiles;

    private final int width, height, tileSize;
    
    public TileMap(int width, int height, int tileSize){
        this.tiles = new Tile[width * height];
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
    }

    public void setTile(int x, int y, Tile tile){
        tiles[x + width * y] = tile;
    }

    public Tile getTile(int x, int y){
        return tiles[x +width * y];
    }

    public void load(Level level) {
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                Tile tile;
                int hex = level.levelImage.getRGB(x, y);
                if (hex == 0xff000000) {
                    tile = new WallTile();
                } else if (hex == 0xffff0000) {
                    // ENEMY
                    tile = new EmptyTile();
                } else {
                    tile = new EmptyTile();
                }
                tile.createTexture(x, y, tileSize, level);
                setTile(x, y, tile);
            }
        }
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight(){
        return height;
    }

    /**
     * Places an <code>Entity</code> on the tile at the <code>Entity</code>:s position.
     * @param entity The <code>Entity</code> to place.
     */
    public boolean place(Entity entity) {
        Point pos = entity.getPos();
        return getTile(pos.x, pos.y).setEntity(entity);
    }
}