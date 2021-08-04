package game2.data;

import game2.entities.Player;
import game2.enums.TextureState;
import game2.enums.TileType;
import game2.essentials.Range;
import game2.levels.Level;
import game2.visuals.ImageData;
import helper.json.FancyJSonParser;
import helper.json.JsonList;
import helper.json.JsonObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class DataLoader {

    private static final String DATA_PATH = "data/";

    private final JsonObject data;

    private final FancyJSonParser parser;

    private int tileSize, visionDistance;

    private String firstLevel, playerName;

    private final Map<String, Level> levels;
    private final Map<String, EntityTemplate> entityTemplates;
    private final Map<JsonObject, Map<String, ImageData>> images;

    public DataLoader() {
        this.data = new JsonObject();
        this.parser = new FancyJSonParser();
        this.levels = new HashMap<>();
        this.entityTemplates = new HashMap<>();
        this.images = new HashMap<>();
    }

    public void readAllData() throws IOException {
        JsonObject data = parser.readResource(DATA_PATH + "data.json");
        firstLevel = data.getString("StartLevel");
        tileSize = data.getInt("TileSize");
        visionDistance = data.getInt("VisionDistance");
        playerName = data.getString("PlayerName");
        readDataFiles(data.getList("DataFiles"));
        images.clear();
        data.clear();
        System.gc();
    }

    public Level getLevel() {
        return levels.get(firstLevel);
    }

    public EntityTemplate getEntityTemplate(String name) {
        return entityTemplates.get(name);
    }

    private void readObject(JsonObject object) throws IOException {
        for (String key : object) {
            JsonObject obj = object.getObject(key);
            readData(key, obj);
            switch (key) {
                case "Entities" -> readEntities(object.getObject(key));
                case "Levels" -> readLevels(object.getObject(key));
            }
        }
    }

    private void readData(String key, JsonObject object) throws IOException {
        addParents(object);
        if (object.containsKey("Images")) {
            Map<String, ImageData> localImages = new HashMap<>();
            String imagePath = object.getString("ImagePath");
            readImages(object.getObject("Images"), imagePath, localImages);
            object.remove("Images");
            images.put(object, localImages);
        }
        this.data.put(key, object);
    }

    private void readLevels(JsonObject object) throws IOException {
        for (String key : object) {
            JsonObject level = object.getObject(key);
            readData(key, level);
            levels.put(key, readLevel(level));
        }
    }

    private void addImages(JsonObject object, Map<String, ImageData> localImages) {
        if (object.containsKey("parents")) {
            JsonList list = object.getList("parents");
            for (int i = 0; i < list.size(); i++) {
                String name = list.getString(i);
                addImages(data.getObject(name), localImages);
            }
        }
        if (images.containsKey(object)) localImages.putAll(images.get(object));
    }

    private Level readLevel(JsonObject object) {
        int width = object.getInt("width");
        int height = object.getInt("height");
        int rooms = object.getInt("rooms");
        Range roomSize = new Range(object.getInt("minRoomSize"), object.getInt("maxRoomSize"));
        Map<String, ImageData> images = new HashMap<>();
        addImages(object, images);
        Map<TileType, Map<TextureState, ImageData>> imageData = new EnumMap<>(TileType.class);
        JsonObject tiles = object.getObject("Tiles");
        for (String key : tiles) {
            Map<TextureState, ImageData> tileImages = new EnumMap<>(TextureState.class);
            JsonObject tile = tiles.getObject(key);
            for (String visibility : tile) {
                String imageName = tile.getString(visibility);
                tileImages.put(TextureState.valueOf(visibility), images.get(imageName));
            }
            imageData.put(TileType.valueOf(key), tileImages);
        }
        //noinspection SuspiciousToArrayCall
        String[] enemies = object.getList("Enemies").toList().toArray(new String[]{});
        return new Level(width, height, rooms, roomSize, imageData, enemies);
    }

    private void readImages(JsonObject object, String imagePath, Map<String, ImageData> destination) throws IOException {
        for (String key : object) {
            destination.put(key, readImage(object.getObject(key), imagePath));
        }
    }

    private void readEntities(JsonObject object) throws IOException {
        for (String key : object) {
            JsonObject entity = object.getObject(key);
            readData(key, entity);
            entityTemplates.put(key, readEntity(entity));
        }
    }

    private EntityTemplate readEntity(JsonObject object) {

        String ai = object.getString("AI");

        JsonObject stats = object.getObject("Stats");
        int hp = stats.getInt("HP");
        int dmg = stats.getInt("DMG");
        int width = stats.getInt("WIDTH");
        int height = stats.getInt("HEIGHT");

        Map<String, ImageData> images = new HashMap<>();
        addImages(object, images);
        JsonObject states = object.getObject("States");
        Map<TextureState, ImageData> textureStates = new EnumMap<>(TextureState.class);
        for (String key : states) {
            TextureState textureState = TextureState.valueOf(key);
            ImageData data = images.get(states.getString(key));
            textureStates.put(textureState, data);
        }
        return new EntityTemplate(ai, hp, dmg, width, height, textureStates);
    }

    private void addParents(JsonObject object) {
        if (!object.containsKey("parents")) return;
        JsonList parents = object.getList("parents");
        for (Object o : parents) {
            String key = (String) o;
            if (data.containsKey(key)) object.merge(data.getObject(key));
        }
    }

    private void readDataFiles(JsonList list) throws IOException {
        for (int i = 0; i < list.size(); i++) {
            String s = list.getString(i);
            JsonObject object = parser.readResource(DATA_PATH + s);
            readObject(object);
        }
    }

    private ImageData readImage(JsonObject object, String imagePath) throws IOException {
        String name = object.getString("name");
        int width = object.getInt("width"), height = object.getInt("height");
        int count = object.getInt("count");
        System.out.println(imagePath + name);
        BufferedImage image = ImageIO.read(ClassLoader.getSystemResource(imagePath + name));
        if (count == 1) {
            return new ImageData(width, height, image);
        }
        BufferedImage[] images = new BufferedImage[count];
        for (int i = 0; i < count; i++) {
            images[i] = image.getSubimage(0, i * height, width, height);
        }
        if (object.containsKey("swap")) {
            int swaps = object.getInt("swap");
            return new ImageData(width, height, swaps, images);
        }
        return new ImageData(width, height, images);
    }

    public int getTileSize() {
        return tileSize;
    }

    public Player getPlayer() {
        Player player = (Player) entityTemplates.get(playerName).create(-1, -1, tileSize);
        player.setVisionDistance(visionDistance);
        return player;
    }
}
