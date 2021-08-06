package game2.data;

import game2.data.generator.Chunk;
import game2.entities.Entity;
import game2.enums.Direction;
import game2.enums.TextureState;
import game2.enums.TileType;
import game2.essentials.Entities;
import game2.essentials.Range;
import game2.essentials.TileMap;
import game2.levels.Level;
import game2.data.generator.MapGenerator;
import game2.tiles.EmptyTile;
import game2.tiles.Tile;
import game2.tiles.WallTile;
import game2.visuals.ImageData;
import game2.visuals.texture.ImageTexture;
import game2.visuals.texture.MultiTexture;

import java.awt.*;
import java.util.Map;

import static game2.Dungeon.THE_RANDOM;
import static game2.data.generator.Chunk.CHUNK_DIMENSION;

public class LevelTemplate {
    private final int width, height, rooms, enemyCount;

    private final Range roomSize;

    private final String[] enemies;

    private final Map<TileType, Map<TextureState, ImageData>> images;

    public LevelTemplate(int width, int height, int rooms, int enemyCount, Range roomSize, String[] enemies, Map<TileType, Map<TextureState, ImageData>> images) {
        this.width = width;
        this.height = height;
        this.rooms = rooms;
        this.enemyCount = enemyCount;
        this.roomSize = roomSize;
        this.enemies = enemies;
        this.images = images;
    }

    public Level generate(int tileSize, Map<String, EntityTemplate> enemies, Entities entities) {
        CHUNK_DIMENSION = 49;
        Chunk a = new Chunk(null, Direction.LEFT);
        Chunk b = new Chunk(a, a.next.opposite().randomOther(THE_RANDOM));
        int[][]  intMapA = a.generate();
        int[][]  intMapB = b.generate();
        TileMap tileMapA = new TileMap(intMapA.length, intMapA[0].length, new Point(0, 0));
        TileMap tileMapB = new TileMap(intMapB.length, intMapB[0].length, a.next.add(0, 0, CHUNK_DIMENSION));
        fillTileMap(intMapA, tileMapA,tileSize, entities, enemies);
        fillTileMap(intMapB, tileMapB,tileSize, entities, enemies);
        a.setTileMap(tileMapA);
        b.setTileMap(tileMapB);
        return new Level(a, b, this.enemies);
    }

    private void fillTileMap(int[][] intMap, TileMap tileMap, int tileSize, Entities entities, Map<String, EntityTemplate> enemies) {
        Point offset = tileMap.getOffset();
        for (int x = 0; x < intMap.length; x++) {
            for (int y = 0; y < intMap[x].length; y++) {
                Tile tile;
                TileType tileType;
                int z = 0;
                int tileInt = intMap[x][y];
                if (tileInt == MapGenerator.HOLE) {
                    tile = new WallTile();
                    tileType = TileType.HOLE;
                } else if (tileInt == MapGenerator.WALL) {
                    tile = new WallTile();
                    tileType = TileType.WALL;
                    z = 1;
                } else {
                    tile = new EmptyTile();
                    tileType = TileType.EMPTY;
                    if (tileInt == MapGenerator.PLAYER_START) {
                        tileMap.setStart(x, y);
                    } else if (tileInt == MapGenerator.ENEMY){
                        Entity e = enemies.get(this.enemies[THE_RANDOM.nextInt(this.enemies.length)]).generate(x, y, tileSize);
                        tile.setEntity(e);
                        entities.addEntity(e);
                    }
                }
                Map<TextureState, ImageData> imageData = this.images.get(tileType);
                MultiTexture<ImageTexture> texture = new MultiTexture<>(new ImageTexture(null,0,0,0,0,0, tileSize), tileSize);
                for (TextureState key : imageData.keySet()){
                    ImageData data = imageData.get(key);
                    int tx = tileSize * offset.x + x * tileSize - (data.width - tileSize) / 2;
                    int ty = tileSize * offset.y + y * tileSize - (data.height - tileSize);
                    texture.addState(key, new ImageTexture(data.getImage(), z, tx, ty, data.width, data.height, tileSize));
                }
                texture.setState(TextureState.HIDDEN);
                tile.setTexture(texture);
                tileMap.setTile(x, y, tile);
            }
        }
    }
}
