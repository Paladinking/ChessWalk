package game2.data;

import game2.entities.Entity;
import game2.entities.Player;
import game2.enums.TextureState;
import game2.enums.TileType;
import game2.essentials.Entities;
import game2.essentials.Range;
import game2.levels.Level;
import game2.sound.Sound;
import game2.visuals.ImageData;
import helper.json.FancyJSonParser;
import helper.json.JsonList;
import helper.json.JsonObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class DataLoader {

    private final String dataPath;

    private final JsonObject data;

    private final FancyJSonParser parser;

    private int tileSize, visionDistance;

    private String firstLevel, playerName;

    private final Map<String, LevelTemplate> levels;
    private final Map<String, EntityTemplate> entityTemplates;
    private final Map<JsonObject, Map<String, ImageData>> images;
    private final Map<JsonObject, Map<String, Sound>> sounds;

    public DataLoader(String dataPath) {
        this.data = new JsonObject();
        this.parser = new FancyJSonParser();
        this.levels = new HashMap<>();
        this.entityTemplates = new HashMap<>();
        this.images = new HashMap<>();
        this.sounds = new HashMap<>();
        this.dataPath = dataPath;
    }

    public void readAllData() throws IOException {
        JsonObject data = parser.readResource(dataPath + "data.json");
        firstLevel = data.getString("StartLevel");
        tileSize = data.getInt("TileSize");
        visionDistance = data.getInt("VisionDistance");
        playerName = data.getString("PlayerName");
        readDataFiles(data.getList("DataFiles"));
        int turnDuration = data.getInt("TurnDuration");
        Entity.ATTACK_DURATION = turnDuration;
        Entity.MOVEMENT_SPEED = tileSize / turnDuration;
        images.clear();
        data.clear();
        System.gc();
    }

    public Level getLevel(Entities entities) {
        return levels.get(firstLevel).generate(tileSize, entityTemplates, entities);
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
        if (object.containsKey("Sounds")) {
            Map<String, Sound> localSounds = new HashMap<>();
            String soundPath = object.getString("SoundPath");
            readSounds(object.getObject("Sounds"), soundPath, localSounds);
            object.remove("Sounds");
            sounds.put(object, localSounds);
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
        add(object, localImages, images);
    }

    private void addSounds(JsonObject object, Map<String, Sound> localSounds) {
        add(object, localSounds, sounds);
    }

    private <T> void add(JsonObject object, Map<String, T> local, Map<JsonObject, Map<String, T>> global){
        if (object.containsKey("parents")) {
            JsonList list = object.getList("parents");
            for (int i = 0; i < list.size(); i++) {
                String name = list.getString(i);
                add(data.getObject(name), local, global);
            }
        }
        if (global.containsKey(object)) local.putAll(global.get(object));
    }

    private LevelTemplate readLevel(JsonObject object) {
        int width = object.getInt("width");
        int height = object.getInt("height");
        int rooms = object.getInt("rooms");
        int enemyCount = object.getInt("enemyCount");
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
        return new LevelTemplate(width, height, rooms, enemyCount, roomSize, enemies, imageData);
    }

    private void readImages(JsonObject object, String imagePath, Map<String, ImageData> destination) throws IOException {
        for (String key : object) {
            destination.put(key, readImage(object.getObject(key), imagePath));
        }
    }

    private void readSounds(JsonObject object, String soundPath, Map<String, Sound> destination) throws IOException {
        for (String key : object) {
            destination.put(key, readSound(object.getObject(key), soundPath));
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
        ImageData blood = images.get(object.getObject("Misc").getString("Blood"));

        Map<String, Sound> sounds = new HashMap<>();
        addSounds(object, sounds);
        return new EntityTemplate(ai, hp, dmg, width, height, textureStates, blood, sounds.get("Walk"), sounds.get("Hurt"));
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
            JsonObject object = parser.readResource(dataPath + s);
            readObject(object);
        }
    }

    private ImageData readImage(JsonObject object, String imagePath) throws IOException {
        String name = object.getString("name");
        int width = object.getInt("width"), height = object.getInt("height");
        int count = object.getInt("count");
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

    private Sound readSound(JsonObject object, String soundPath) throws IOException {
        String name = object.getString("name");
        InputStream stream = ClassLoader.getSystemResourceAsStream(soundPath + name);
        if (stream == null) return new Sound(new byte[]{});
        byte[] soundData = stream.readAllBytes();
        return new Sound(soundData);
    }

    public int getTileSize() {
        return tileSize;
    }

    public Player getPlayer() {
        Player player = (Player) entityTemplates.get(playerName).generate(-1, -1, tileSize);
        player.setVisionDistance(visionDistance);
        return player;
    }
}
