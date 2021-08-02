package game2.levels;


import game2.enums.LevelImage;
import game2.visuals.ImageData;
import game2.visuals.Images;
import helper.json.JsonList;
import helper.json.JsonObject;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {

    private static final Random RANDOM = new Random();

    public final BufferedImage levelImage;

    private ImageData<LevelImage> wall, tile;

    private String[] enemies;

    public static List<Level> fromJson(JsonObject l, Images img) throws IOException {
        List<Level> levels = new ArrayList<>();
        try {
            for (String levelName : l) {
                JsonObject lvl = l.getObject(levelName);
                BufferedImage levelImage = img.getImage(lvl.getString("LevelImage"));
                Level level = new Level(levelImage);
                img.createMap(level);
                String path = lvl.getString("ImagePath");
                JsonObject images = lvl.getObject("Images");
                {
                    JsonObject wall = images.getObject("WALL");
                    String name = wall.getString("name");
                    img.loadImage(level, path, name, LevelImage.WALL);
                    int width = wall.getInt("width");
                    int height = wall.getInt("height");
                    level.wall = new ImageData<>(width, height, 1, 0, LevelImage.WALL);
                }
                {
                    JsonObject tile = images.getObject("TILE");
                    String name = tile.getString("name");
                    img.loadImage(level, path, name, LevelImage.TILE);
                    int width = tile.getInt("width");
                    int height = tile.getInt("height");
                    level.tile = new ImageData<>(width, height, 1, 0, LevelImage.TILE);
                }
                JsonList enemies = lvl.getList("Enemies");
                level.enemies = enemies.toList().toArray(new String[]{});
                levels.add(level);
            }
        } catch (NullPointerException | ClassCastException e){
            e.printStackTrace();
        }
        return levels;
    }

    public ImageData<LevelImage> getWall(){
        return wall;
    }

    public ImageData<LevelImage> getTile(){
        return tile;
    }

    private Level(BufferedImage levelImage){
        this.levelImage = levelImage;
    }

    public String getEnemy(){
        return enemies[RANDOM.nextInt(enemies.length)];
    }

}
