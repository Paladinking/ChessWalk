package game2.entities;

import game2.entities.enemies.Knight;
import game2.entities.enemies.Skeleton;
import game2.entities.enemies.Slime;
import game2.visuals.ImageData;
import game2.visuals.Images;
import game2.visuals.texture.EntityTexture;
import game2.enums.TextureState;
import helper.json.JsonObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class EntityTemplate {

    private String ai;
    private int hp, dmg;

    private int width, height;

    private final List<ImageData<TextureState>> textureStates;

    private EntityTemplate(){
        this.textureStates = new ArrayList<>();
    }


    public static EntityTemplate fromJsonObject(JsonObject e, Images img) throws IOException {
        EntityTemplate template = new EntityTemplate();
        try {
            template.ai = e.getString("AI");

            JsonObject stats = e.getObject("Stats");
            template.hp = stats.getInt("HP");
            template.dmg = stats.getInt("DMG");
            template.width = stats.getInt("WIDTH");
            template.height = stats.getInt("HEIGHT");

            String path = e.getString("ImagePath");
            JsonObject images = e.getObject("Images");
            img.createMap(template);
            for (String key : images) {
                TextureState textureState = TextureState.valueOf(key);
                JsonObject image = images.getObject(key);
                String name = image.getString("name");
                int count = image.getInt("count");
                if (count == 1){
                    img.loadImage(template, path, name, textureState);
                    template.textureStates.add(new ImageData<>(template.width, template.height, count, 0, textureState));
                } else {
                    int width = image.getInt("width");
                    int height = image.getInt("height");
                    img.loadImage(template, path, name,textureState, count, width, height);
                    int swap = image.getInt("swap");
                    template.textureStates.add(new ImageData<>(width, height, swap, count, textureState));
                }
            }
        } catch (NullPointerException | ClassCastException exception){
            exception.printStackTrace();
            return null;
        }
        return template;

    }



    public Entity create(int x, int y, Images images, int tileSize) {
        Entity e = switch (ai) {
            case "Player" -> new Player(x, y, hp, dmg);
            case "Knight" -> new Knight(x, y, hp, dmg);
            case "Skeleton" -> new Skeleton(x, y, hp, dmg);
            default -> new Slime(x, y, hp, dmg);
        };
        int tx = x * tileSize - (width - tileSize) / 2, ty = y * tileSize - (height - tileSize);
        EntityTexture texture = new EntityTexture(tx, ty, width, height);
        for (ImageData<TextureState> img : textureStates){
            BufferedImage[] i = images.getImages(this, img.key);
            texture.addState(img.key, i, img.swaps);
        }
        texture.setState(TextureState.IDLE);
        e.setTexture(texture);
        return e;
    }

}
