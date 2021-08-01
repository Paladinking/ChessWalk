package game2.entities.enemies;

import game2.games.Walk;
import game2.visuals.texture.EntityTexture;
import game2.visuals.Images;
import game2.visuals.texture.Texture;

public class Slime extends Enemy {

    private static final EnemyData SLIME = new EnemyData(Walk.TILE_SIZE, Walk.TILE_SIZE, 5, 1);

    public Slime(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick() {

    }

    @Override
    public Texture getTexture(Images images) {
        return new EntityTexture(this, SLIME.width, SLIME.height, images.getImage(Slime.class));
    }
}
