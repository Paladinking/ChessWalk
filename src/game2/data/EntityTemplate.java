package game2.data;

import game2.entities.Entity;
import game2.entities.Player;
import game2.entities.enemies.Knight;
import game2.entities.enemies.Skeleton;
import game2.entities.enemies.Slime;
import game2.visuals.ImageData;
import game2.visuals.texture.AbstractTexture;
import game2.visuals.texture.AnimationTexture;
import game2.enums.TextureState;
import game2.visuals.texture.ImageTexture;
import game2.visuals.texture.MultiTexture;
import java.util.*;

public class EntityTemplate {

    private final String ai;
    private final int hp, dmg;

    private final int width, height;

    private final Map<TextureState, ImageData> textureStates;

    public EntityTemplate(String ai, int hp, int dmg, int width, int height, Map<TextureState, ImageData> textureStates) {
        this.ai = ai;
        this.hp = hp;
        this.dmg = dmg;
        this.width = width;
        this.height = height;
        this.textureStates = textureStates;
    }

    public Entity create(int x, int y, int tileSize) {
        Entity e = switch (ai) {
            case "Player" -> new Player(x, y, hp, dmg);
            case "Knight" -> new Knight(x, y, hp, dmg);
            case "Skeleton" -> new Skeleton(x, y, hp, dmg);
            default -> new Slime(x, y, hp, dmg);
        };
        int tx = x * tileSize - (width - tileSize) / 2, ty = y * tileSize - (height - tileSize);
        MultiTexture texture = new MultiTexture();
        for (TextureState state : textureStates.keySet()){
            ImageData data = textureStates.get(state);
            AbstractTexture t;
            if (data.animation) t = new AnimationTexture(data.getImages(), tx, ty, width, height, data.swaps);
            else t = new ImageTexture(data.getImage(), 1,  tx, ty, width, height);
            texture.addState(state, t);
        }
        texture.setState(TextureState.IDLE);
        e.setTexture(texture);
        return e;
    }

}
