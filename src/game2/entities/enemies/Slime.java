package game2.entities.enemies;

import game2.actions.EmptyAction;
import game2.actions.EntityAction;
import game2.actions.NoAction;
import game2.actions.RandomMove;
import game2.essentials.TileMap;

public class Slime extends Enemy {

    public Slime(int x, int y, int hp, int dmg) {
        super(x, y, hp, dmg, 2);
    }

    @Override
    protected EntityAction getAttack(TileMap tileMap) {
        return new NoAction(this);
    }

    @Override
    protected EntityAction getMove(TileMap tileMap) {
        return new RandomMove(this, speed, tileMap.getTileSize());
    }
}
