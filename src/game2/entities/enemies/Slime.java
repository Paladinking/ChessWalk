package game2.entities.enemies;

import game2.actions.Move;
import game2.essentials.TileMap;
import game2.games.Walk;
import game2.visuals.texture.EntityTexture;
import game2.visuals.Images;

import java.awt.*;

public class Slime extends Enemy {

    private static final EnemyData SLIME = new EnemyData(Walk.TILE_SIZE, Walk.TILE_SIZE, 5, 1);

    public Slime(int x, int y) {
        super(x, y);
    }

    @Override
    protected void pickAction(TileMap tileMap) {
        this.action = new Move(this, 2, tileMap.getTileSize(), new Point(-1, 0));
    }

    @Override
    protected EntityTexture getTexture(Images images, int tileSize) {
        return new EntityTexture(gridPos.x * tileSize, gridPos.y * tileSize, SLIME.width, SLIME.height, images.getImage(Slime.class));
    }
}
