package game2.entities.enemies;

import game2.actions.Attack;
import game2.actions.EntityAction;
import game2.actions.Move;
import game2.actions.NoAction;
import game2.enums.TextureState;
import game2.essentials.AStar;
import game2.levels.Level;

import java.awt.*;
import java.util.List;

public class Knight extends Enemy {

    public Knight(int x, int y, int hp, int dmg) {
        super(x, y, hp, dmg, 2);
    }

    @Override
    protected EntityAction getAttack(Level level) {
        return new Attack(this, level.getPlayerPos(), TextureState.ATTACK, dmg, ATTACK_DURATION);
    }

    @Override
    protected EntityAction getMove(Level level) {
        List<Point> points = AStar.getPath(level, gridPos, level.getPlayerPos());
        if (points == null || points.size() > 5) return new NoAction(this);
        Point target = points.get(points.size() - 1);
        return new Move(this, MOVEMENT_SPEED, target);
    }
}
