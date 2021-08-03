package game2.levels;


import game2.Dungeon;
import game2.enums.TileType;
import game2.essentials.Range;
import game2.essentials.TileMap;
import game2.levels.generator.MapGenerator;
import game2.tiles.EmptyTile;
import game2.tiles.HoleTile;
import game2.tiles.Tile;
import game2.tiles.WallTile;
import game2.visuals.ImageData;
import game2.visuals.Images;
import game2.visuals.texture.StaticTexture;
import game2.visuals.texture.Texture;
import helper.json.JsonList;
import helper.json.JsonObject;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level {

    private Range roomSize;
    private int width, height, rooms;

    private ImageData<TileType> wall, empty, hole;

    private String[] enemies;

    private Level(){

    }

    public static List<Level> fromJson(JsonObject l, Images img) throws IOException {
        List<Level> levels = new ArrayList<>();
        try {
            for (String levelName : l) {
                JsonObject lvl = l.getObject(levelName);
                Level level = new Level();
                level.width = lvl.getInt("width");
                level.height = lvl.getInt("height");
                level.rooms = lvl.getInt("rooms");
                level.roomSize = new Range(lvl.getInt("minRoomSize"), lvl.getInt("maxRoomSize"));
                img.createMap(level);
                String path = lvl.getString("ImagePath");
                JsonObject images = lvl.getObject("Images");
                level.wall = loadImage(img, level, images, path, TileType.WALL);
                level.empty = loadImage(img, level, images, path, TileType.EMPTY);
                level.hole = loadImage(img, level, images, path, TileType.HOLE);

                JsonList enemies = lvl.getList("Enemies");
                //noinspection SuspiciousToArrayCall
                level.enemies = enemies.toList().toArray(new String[]{});
                levels.add(level);
            }
        } catch (NullPointerException | ClassCastException e){
            e.printStackTrace();
        }
        return levels;
    }

    private static ImageData<TileType> loadImage(Images img, Level level, JsonObject images, String path, TileType key) throws IOException {
        JsonObject image = images.getObject(key.name());
        String name = image.getString("name");
        int width = image.getInt("width");
        int height = image.getInt("height");
        int count = image.getInt("count");
        img.loadImage(level, path, name, key, count, width, height);
        return new ImageData<>(width, height, count, 0, key);
    }

    public TileMap createTiles(int tileSize, Images images) {
        TileMap tileMap = new TileMap(width, height, tileSize);
        MapGenerator generator = new MapGenerator(rooms, width, height, roomSize);
        int[][] map = generator.generate();
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                Tile tile;
                ImageData<TileType> imageData;
                int z = 0;
                int t = map[x][y];
                if (t == MapGenerator.HOLE){
                    tile = new HoleTile();
                    imageData = hole;
                } else if (t == MapGenerator.WALL) {
                    tile = new WallTile();
                    imageData = wall;
                    z = 1;
                } else {
                    tile = new EmptyTile();
                    imageData = empty;
                    if (t == MapGenerator.PLAYER_START){
                        tileMap.setStart(x, y);
                    }
                }
                BufferedImage image = images.getImage(this, imageData.key);
                int tx = x * tileSize - (imageData.width -tileSize) / 2, ty = y * tileSize - (imageData.height - tileSize);
                Texture  texture = new StaticTexture(image, z, tx, ty, imageData.width, imageData.height);
                tile.setTexture(texture);
                tileMap.setTile(x, y, tile);
            }
        }
        return tileMap;
    }


    public String getEnemy(){
        return enemies[Dungeon.THE_RANDOM.nextInt(enemies.length)];
    }


}
