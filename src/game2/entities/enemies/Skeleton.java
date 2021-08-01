package game2.entities.enemies;

import game2.entities.enemies.Enemy;
import game2.visuals.Images;
import game2.visuals.texture.EntityTexture;
import game2.visuals.texture.Texture;

public class Skeleton extends Enemy {

    public Skeleton(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick() {

    }

    @Override
    public Texture getTexture(Images images) {
        return new EntityTexture(this, 50, 70, images.getImage(Skeleton.class));
    }
}
