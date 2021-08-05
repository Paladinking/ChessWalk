package game2.levels;

import game2.Dungeon;
import game2.entities.Entity;
import game2.entities.Player;
import game2.essentials.TileMap;
import game2.tiles.Tile;

import java.awt.*;
import java.util.List;

public class Level {

    private final TileMap tileMap;

    private final String[] enemies;

    private Player player;

    public Level(TileMap tileMap, String[] enemies) {
        this.tileMap = tileMap;
        this.enemies = enemies;
    }

    public void setTile(int x, int y, Tile tile) {
        tileMap.setTile(x, y, tile);
    }

    public Tile getTile(int x, int y) {
        return tileMap.getTile(x, y);
    }

    public int getWidth() {
        return tileMap.getWidth();
    }

    public int getHeight() {
        return tileMap.getHeight();
    }

    public void place(Entity entity) {
        tileMap.place(entity);
    }

    public void placePlayer(Player player) {
        this.player = player;
        tileMap.placePlayer(player);
    }

    public void moveEntity(Point oldPos, Point newPos) {
        tileMap.moveEntity(oldPos, newPos);
    }

    public void updateLighting() {
        tileMap.updateLighting(player);
    }

    public Tile getTile(Point pos) {
        return tileMap.getTile(pos);
    }

    public static boolean neighbors(Point a, Point b) {
        return TileMap.neighbors(a, b);
    }

    public Point getPlayerPos() {
        return player.getPos();
    }

    public List<Point> getOpenDirections(Point pos) {
        return tileMap.getOpenDirections(pos);
    }

    public String getEnemy() {
        if (enemies.length == 0) return null;
        return enemies[Dungeon.THE_RANDOM.nextInt(enemies.length)];
    }

    public TileMap getTilesMap() {
        return tileMap;
    }
}
