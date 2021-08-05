package game2.actions;

import game2.Dungeon;
import game2.entities.Entity;
import game2.enums.TextureState;
import game2.levels.Level;
import game2.visuals.texture.AnimationTexture;
import game2.visuals.texture.MultiTexture;

import java.awt.*;
import java.util.List;

public class RandomMove extends Move {

    public RandomMove(Entity entity, int speed) {
        super(entity, speed, null);
    }

    @Override
    public ActionStatus init(Level level) {
        Point pos = entity.getPos();
        List<Point> openTiles = level.getOpenDirections(pos);
        if (openTiles.size() == 0) return ActionStatus.FINISHED;
        target = openTiles.get(Dungeon.THE_RANDOM.nextInt(openTiles.size()));
        init(new Point(target.x + pos.x,target.y + pos.y));
        return ActionStatus.WORKING;
    }
}
