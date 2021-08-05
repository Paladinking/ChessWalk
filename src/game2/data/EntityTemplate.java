package game2.data;

import game2.entities.Entity;
import game2.entities.Player;
import game2.entities.enemies.Knight;
import game2.entities.enemies.Skeleton;
import game2.entities.enemies.Slime;
import game2.visuals.ImageData;
import game2.visuals.texture.AnimationTexture;
import game2.enums.TextureState;
import game2.visuals.texture.MultiTexture;

import java.awt.image.BufferedImage;
import java.util.*;

public class EntityTemplate {

    private final String ai;
    private final int hp, dmg;

    private final int width, height;

    private final Map<TextureState, ImageData> textureStates;
    private final ImageData blood;

    public EntityTemplate(String ai, int hp, int dmg, int width, int height, Map<TextureState, ImageData> textureStates, ImageData blood) {
        this.ai = ai;
        this.hp = hp;
        this.dmg = dmg;
        this.width = width;
        this.height = height;
        this.textureStates = textureStates;
        this.blood = blood;
    }

    public Entity create(int x, int y, int tileSize) {
        Entity e = switch (ai) {
            case "Player" -> new Player(x, y, hp, dmg);
            case "Knight" -> new Knight(x, y, hp, dmg);
            case "Skeleton" -> new Skeleton(x, y, hp, dmg);
            default -> new Slime(x, y, hp, dmg);
        };
        int tx = x * tileSize - (width - tileSize) / 2, ty = y * tileSize - (height - tileSize);
        MultiTexture<AnimationTexture> texture = new MultiTexture<>(new AnimationTexture(new BufferedImage[]{null}, 0, 0, 0, 0, 0, tileSize), tileSize);
        for (TextureState state : textureStates.keySet()) {
            ImageData data = textureStates.get(state);
            texture.addState(state, new AnimationTexture(data.getImages(), tx, ty, width, height, data.swaps, tileSize));
        }
        texture.setState(TextureState.IDLE);
        e.setTexture(texture);
        e.setBlood(blood);
        return e;
    }

}
