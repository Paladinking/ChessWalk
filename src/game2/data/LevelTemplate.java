package game2.data;

import game2.enums.TextureState;
import game2.enums.TileType;
import game2.essentials.Range;
import game2.essentials.TileMap;
import game2.levels.Level;
import game2.levels.generator.MapGenerator;
import game2.tiles.EmptyTile;
import game2.tiles.Tile;
import game2.tiles.WallTile;
import game2.visuals.ImageData;
import game2.visuals.texture.ImageTexture;
import game2.visuals.texture.MultiTexture;

import java.util.Map;

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

    public Level generate(int tileSize) {
        TileMap tileMap = new TileMap(width, height);
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
                Map<TextureState, ImageData> imageData = this.images.get(tileType);
                MultiTexture<ImageTexture> texture = new MultiTexture<>(new ImageTexture(null,0,0,0,0,0, tileSize), tileSize);
                for (TextureState key : imageData.keySet()){
                    ImageData data = imageData.get(key);
                    int tx = x * tileSize - (data.width - tileSize) / 2, ty = y * tileSize - (data.height - tileSize);
                    texture.addState(key, new ImageTexture(data.getImage(), z, tx, ty, data.width, data.height, tileSize));
                }
                texture.setState(TextureState.HIDDEN);
                tile.setTexture(texture);
                tileMap.setTile(x, y, tile);
            }
        }
        return new Level(tileMap, enemies);
    }
}
