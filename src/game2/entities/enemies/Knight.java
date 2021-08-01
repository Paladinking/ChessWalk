package game2.entities.enemies;

import game2.visuals.Images;
import game2.visuals.texture.EntityTexture;

public class Knight extends Enemy {

    public Knight(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick() {

    }

    @Override
    protected EntityTexture getTexture(Images images, int tileSize) {
        return new EntityTexture(gridPos.x * tileSize, gridPos.y * tileSize - 20, 50, 70, images.getImage(Knight.class));
    }
}
