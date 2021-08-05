package game2.actions;

import game2.Dungeon;
import game2.entities.Entity;
import game2.levels.Level;

import java.awt.*;
import java.util.List;

public class RandomMove extends Move {

    public RandomMove(Entity entity, int speed) {
        super(entity, speed, null);
    }

    @Override
    public ActionStatus init(Level level) {
        Point pos = entity.getPos();
        List<Point> openTiles = level.getOpenTiles(pos);
        if (openTiles.size() == 0) return ActionStatus.INTERRUPTED;
        dest = openTiles.get(Dungeon.THE_RANDOM.nextInt(openTiles.size()));
        init();
        return ActionStatus.WORKING;
    }
}
