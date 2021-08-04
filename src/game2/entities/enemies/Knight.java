package game2.entities.enemies;

import game2.actions.EntityAction;
import game2.actions.Move;
import game2.actions.NoAction;
import game2.essentials.AStar;
import game2.essentials.TileMap;

import java.awt.*;
import java.util.List;

public class Knight extends Enemy {

    public Knight(int x, int y, int hp, int dmg) {
        super(x, y, hp, dmg, 2);
    }

    @Override
    protected EntityAction getAttack(TileMap tileMap) {
        return new NoAction(this);
    }

    @Override
    protected EntityAction getMove(TileMap tileMap) {
        List<Point> points = AStar.getPath(tileMap, gridPos, tileMap.getPlayerPos());
        if (points == null || points.size() > 5) return new NoAction(this);
        Point target = points.get(points.size() - 1);
        return new Move(this, speed, tileMap.getTileSize(), target);
    }
}
