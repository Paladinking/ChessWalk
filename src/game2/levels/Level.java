package game2.levels;


import game2.Dungeon;
import game2.enums.TextureState;
import game2.enums.TileType;
import game2.essentials.Range;
import game2.essentials.TileMap;
import game2.levels.generator.MapGenerator;
import game2.tiles.EmptyTile;
import game2.tiles.Tile;
import game2.tiles.WallTile;
import game2.visuals.ImageData;
import game2.visuals.texture.ImageTexture;
import game2.visuals.texture.MultiTexture;

import java.util.Map;

public class Level {

    private final Range roomSize;
    private final int width, height, rooms;

    private final Map<TileType, Map<TextureState, ImageData>> imageData;

    private final String[] enemies;

    public Level(int width, int height, int rooms, Range roomSize, Map<TileType, Map<TextureState, ImageData>> imageData, String[] enemies) {
        this.imageData = imageData;
        this.rooms = rooms;
        this.roomSize = roomSize;
        this.width = width;
        this.height = height;
        this.enemies = enemies;
    }

    public TileMap createTiles(int tileSize) {
        TileMap tileMap = new TileMap(width, height, tileSize);
        MapGenerator generator = new MapGenerator(rooms, width, height, roomSize);
        int[][] map = generator.generate();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile;
                TileType tileType;
                int z = 0;
                int tileInt = map[x][y];
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
                    }
                }
                Map<TextureState, ImageData> imageData = this.imageData.get(tileType);
                MultiTexture texture = new MultiTexture();
                for (TextureState key : imageData.keySet()){
                    ImageData data = imageData.get(key);
                    int tx = x * tileSize - (data.width - tileSize) / 2, ty = y * tileSize - (data.height - tileSize);
                    texture.addState(key, new ImageTexture(data.getImage(), z, tx, ty, data.width, data.height));
                }
                texture.setState(TextureState.HIDDEN);
                tile.setTexture(texture);
                tileMap.setTile(x, y, tile);
            }
        }
        return tileMap;
    }


    public String getEnemy() {
        if (enemies.length == 0) return null;
        return enemies[Dungeon.THE_RANDOM.nextInt(enemies.length)];
    }


}
