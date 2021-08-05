package game2.entities.enemies;

import game2.actions.EntityAction;
import game2.actions.NoAction;
import game2.levels.Level;

public class Skeleton extends Enemy {

    public Skeleton(int x, int y, int hp, int dmg) {
        super(x, y, hp, dmg, 2);
    }

    @Override
    protected EntityAction getAttack(Level level) {
        return new NoAction(this);
    }

    @Override
    protected EntityAction getMove(Level level) {
        return new NoAction(this);
    }
}
