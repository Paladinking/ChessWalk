package game2.essentials;

import game2.entities.Entity;
import game2.entities.Player;
import game2.enums.TextureState;
import game2.tiles.Tile;
import game2.tiles.WallTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TileMap {

    private final Tile[] tiles;

    private final List<Tile> visibleTiles;

    private final ShadowCaster shadowCaster;

    private final int width, height, tileSize;

    private Point playerPos;

    public TileMap(int width, int height, int tileSize){
        this.tiles = new Tile[width * height];
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.visibleTiles = new ArrayList<>();
        this.shadowCaster = new ShadowCaster(this);
    }

    public void setTile(int x, int y, Tile tile){
        tiles[x + width * y] = tile;
    }

    public Tile getTile(int x, int y){
        return tiles[x +width * y];
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
    public void place(Entity entity) {
        Point pos = entity.getPos();
        getTile(pos.x, pos.y).setEntity(entity);
    }

    public void placePlayer(Player player) {
        Point oldPlayerPos = player.getPos();
        player.getTexture().move((playerPos.x - oldPlayerPos.x) * tileSize, (playerPos.y - oldPlayerPos.y) * tileSize);
        oldPlayerPos.setLocation(playerPos.x, playerPos.y);
        this.playerPos = oldPlayerPos;
        place(player);
        updateLighting(player);
    }

    public void moveEntity(Point oldPos, Point newPos) {
        Tile old = getTile(oldPos.x, oldPos.y);
        Entity e = old.getEntity();
        old.setEntity(null);
        getTile(newPos.x, newPos.y).setEntity(e);
    }

    public void updateLighting(Player player){
        for (Tile tile : visibleTiles) tile.hide();
        visibleTiles.clear();
        Point playerPos = getPlayerPos();
        shadowCaster.castShadow( playerPos.x, playerPos.y, player.getVisionDistance());
        for (Tile tile : visibleTiles){
            tile.show();
        }
    }

    public void showVisibleTiles(int visionLength) {

    }

    public Tile getTile(Point pos) {
        return getTile(pos.x, pos.y);
    }

    /**
     * Returns <code>true</code> iff two points are next to one another.
     * @param a The first Point.
     * @param b The second Point
     * @return true iff <code>a</code> and <code>b</code> are next to each other.
     */
    public static boolean neighbors(Point a, Point b){
        return Math.abs(a.x - b.x) <= 1 && Math.abs(a.y - b.y) <=1;
    }

    public Point getPlayerPos() {
        return new Point(playerPos.x, playerPos.y);
    }

    public List<Point> getOpenDirections(Point pos) {
        List<Point> openPoints = new ArrayList<>();
        for (int x = -1; x<=1; x++){
            for (int y =-1; y<=1; y++){
                if (x == y && y == 0) continue;
                if(getTile(pos.x + x, pos.y + y).isOpen()) openPoints.add(new Point(x, y));
            }
        }
        return openPoints;
    }

    public void setStart(int x, int y) {
        this.playerPos = new Point(x, y);
    }

    public boolean isWall(int x, int y) {
        return getTile(x, y) instanceof WallTile;
    }

    public void show(int x, int y) {
        visibleTiles.add(getTile(x, y));
    }
}
