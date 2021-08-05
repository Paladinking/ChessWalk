package game2.entities.enemies;

import game2.actions.EntityAction;
import game2.actions.Move;
import game2.actions.NoAction;
import game2.actions.SlimeAttack;
import game2.enums.TextureState;
import game2.essentials.AStar;
import game2.levels.Level;

import java.awt.*;
import java.util.List;

public class Skeleton extends Enemy {

    public Skeleton(int x, int y, int hp, int dmg) {
        super(x, y, hp, dmg, 2);
    }

    @Override
    protected EntityAction getAttack(Level level) {
        return new SlimeAttack(this, level.getPlayerPos(), TextureState.ATTACK, dmg, ATTACK_DURATION);
    }

    @Override
    protected EntityAction getMove(Level level) {
        if (hidden){
            return new NoAction(this);
        }
        List<Point> path = AStar.getPath(level.getTilesMap(), gridPos, level.getPlayerPos());
        if (path != null) return new Move(this,MOVEMENT_SPEED, path.get(path.size() - 1));
        return new NoAction(this);
    }
}
