package game2.data;

import game2.entities.Entity;
import game2.entities.Player;
import game2.entities.enemies.Knight;
import game2.entities.enemies.Skeleton;
import game2.entities.enemies.Slime;
import game2.sound.Sound;
import game2.visuals.ImageData;
import game2.visuals.texture.AnimationTexture;
import game2.enums.TextureState;
import game2.visuals.texture.EntityTexture;

import java.util.*;

public class EntityTemplate {

    private final String ai;
    private final int hp, dmg;

    private final Sound walk, hurt;

    private final int width, height;

    private final Map<TextureState, ImageData> textureStates;
    private final ImageData blood;

    public EntityTemplate(String ai, int hp, int dmg, int width, int height, Map<TextureState, ImageData> textureStates, ImageData blood, Sound walk, Sound hurt) {
        this.ai = ai;
        this.hp = hp;
        this.dmg = dmg;
        this.width = width;
        this.height = height;
        this.textureStates = textureStates;
        this.blood = blood;
        this.walk = walk;
        this.hurt = hurt;
    }

    /**
     * Generates an <code>Entity</code> based on this template, on the given location.
     * All the entities Textures are also created.
     * @param x The x coordinate of the new <code>Entity</code>.
     * @param y The y coordinate of the new <code>Entity</code>.
     * @param tileSize The size of a tile on the tileMap.
     * @return The generated <code>Entity</code>
     */
    public Entity generate(int x, int y, int tileSize) {
        Entity e = switch (ai) {
            case "Player" -> new Player(x, y, hp, dmg);
            case "Knight" -> new Knight(x, y, hp, dmg);
            case "Skeleton" -> new Skeleton(x, y, hp, dmg);
            default -> new Slime(x, y, hp, dmg);
        };
        int tx = x * tileSize - (width - tileSize) / 2, ty = y * tileSize - (height - tileSize);
        EntityTexture texture = new EntityTexture(tileSize);
        for (TextureState state : textureStates.keySet()) {
            ImageData data = textureStates.get(state);
            texture.addState(state, new AnimationTexture(data.getImages(), tx, ty, width, height, data.swaps, tileSize));
        }
        texture.setState(TextureState.IDLE);
        e.setTexture(texture);
        e.setBlood(blood);
        e.setSounds(walk, hurt);
        return e;
    }

}
