package game2.essentials;

import game2.entities.Entity;
import game2.entities.Player;
import game2.tiles.Tile;
import game2.tiles.WallTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TileMap {

    private final Tile[] tiles;

    private final int width, height;

    private Point startPos;

    private final Point offset;

    public TileMap(int width, int height, Point offset){
        this.tiles = new Tile[width * height];
        this.width = width;
        this.height = height;
        this.offset = offset;
    }

    public void setTile(int x, int y, Tile tile){
        tiles[x + width * y] = tile;
    }

    public Tile getTile(int x, int y) {
        x -= offset.x;
        y -= offset.y;
        return tiles[x +width * y];
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
        player.getTexture().moveTiles((startPos.x - oldPlayerPos.x), (startPos.y - oldPlayerPos.y));
        oldPlayerPos.setLocation(startPos.x, startPos.y);
        // Just to make sure it's only used once.
        startPos = null;
        place(player);
    }

    public void moveEntity(Point oldPos, Point newPos) {
        Tile old = getTile(oldPos.x, oldPos.y);
        Entity e = old.getEntity();
        old.setEntity(null);
        getTile(newPos.x, newPos.y).setEntity(e);
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

    public List<Point> getOpenTiles(Point pos) {
        List<Point> openPoints = new ArrayList<>();
        for (int x = pos.x -1; x<= pos.x + 1; x++){
            for (int y = pos.y -1; y<=pos.y + 1; y++){
                if (x == pos.x && y == pos.y) continue;
                if(getTile(x, y).isOpen()) openPoints.add(new Point(x, y));
            }
        }
        return openPoints;
    }

    public void setStart(int x, int y) {
        this.startPos = new Point(x, y);
    }

    public boolean isWall(int x, int y) {
        return getTile(x, y) instanceof WallTile;
    }

    public Point getOffset() {
        return offset;
    }
}
