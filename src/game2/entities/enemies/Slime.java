package game2.entities.enemies;

import game2.actions.*;
import game2.enums.TextureState;
import game2.levels.Level;

public class Slime extends Enemy {

    public Slime(int x, int y, int hp, int dmg) {
        super(x, y, hp, dmg, 2);
    }

    @Override
    protected EntityAction getAttack(Level level) {
        return new SlimeAttack(this, level.getPlayerPos(), TextureState.ATTACK, dmg, 16);
    }

    @Override
    protected EntityAction getMove(Level level) {
        return new RandomMove(this, speed);
    }
}
