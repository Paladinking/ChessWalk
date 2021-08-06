package game2.levels;

import game2.Dungeon;
import game2.data.generator.Chunk;
import game2.entities.Entity;
import game2.entities.Player;
import game2.essentials.ShadowCaster;
import game2.essentials.TileMap;
import game2.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static game2.data.generator.Chunk.CHUNK_DIMENSION;

public class Level {

    private static final int MAX_WIDTH = 500;

    //private final TileMap tileMap;

    private final Map<Integer, Chunk> chunks;
    private final String[] enemies;

    private int width, height;

    private final List<Tile> visibleTiles;

    private Player player;
    private final ShadowCaster shadowCaster;

    private TileMap test;

    public Level(Chunk chunkA, Chunk chunkB, String[] enemies) {
        this.chunks = new HashMap<>();
        test = chunkA.getTileMap();
        chunks.put(0, chunkA);
        this.width = CHUNK_DIMENSION * 2;
        this.height = CHUNK_DIMENSION;
        Point next = chunkA.next.add(0, 0, 1);
        chunks.put(next.x + MAX_WIDTH * next.y, chunkB);
        this.enemies = enemies;
        this.visibleTiles = new ArrayList<>();
        this.shadowCaster = new ShadowCaster(this);
    }

    public void setTile(int x, int y, Tile tile) {
        getTileMap(x, y).setTile(x, y, tile);
    }

    private TileMap getTileMap(int x, int y) {
        return chunks.get(x / CHUNK_DIMENSION + MAX_WIDTH * (y / CHUNK_DIMENSION)).getTileMap();
    }

    public Tile getTile(int x, int y) {
        return getTileMap(x, y).getTile(x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void place(Entity entity) {
        getTileMap(entity.getPos().x, entity.getPos().y).place(entity);
    }

    public void placePlayer(Player player) {
        this.player = player;
        getTileMap(0, 0).placePlayer(player);
        updateLighting();
    }

    public void moveEntity(Point oldPos, Point newPos) {
        Tile old = getTile(oldPos.x, oldPos.y);
        Entity e = old.getEntity();
        old.setEntity(null);
        getTile(newPos.x, newPos.y).setEntity(e);
    }

    public void updateLighting() {
        for (Tile tile : visibleTiles) tile.hide();
        visibleTiles.clear();
        Point playerPos = player.getPos();
        shadowCaster.castShadow(playerPos.x, playerPos.y, player.getVisionDistance());
        for (Tile tile : visibleTiles){
            tile.show();
        }
    }

    public Tile getTile(Point pos) {
        return getTileMap(pos.x, pos.y).getTile(pos);
    }

    public static boolean neighbors(Point a, Point b) {
        return TileMap.neighbors(a, b);
    }

    public Point getPlayerPos() {
        return player.getPos();
    }

    public List<Point> getOpenTiles(Point pos) {
        return getTileMap(pos.x, pos.y).getOpenTiles(pos);
    }

    public String getEnemy() {
        if (enemies.length == 0) return null;
        return enemies[Dungeon.THE_RANDOM.nextInt(enemies.length)];
    }

    public void show(int x, int y) {
        TileMap tileMap = getTileMap(x, y);
        if (tileMap != test){
            System.out.println("!!!");
        }
        visibleTiles.add(getTile(x, y));
    }

    public boolean isWall(int x, int y) {
        return getTileMap(x, y).isWall(x, y);
    }
}
