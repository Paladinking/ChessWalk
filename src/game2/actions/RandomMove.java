package game2.actions;

import game2.Dungeon;
import game2.entities.Entity;
import game2.enums.TextureState;
import game2.essentials.TileMap;

import java.awt.*;
import java.util.List;

public class RandomMove extends Move {

    public RandomMove(Entity entity, int speed, int tileSize) {
        super(entity, speed, tileSize, null);
    }

    @Override
    public ActionStatus init(TileMap tileMap) {
        Point pos = entity.getPos();
        List<Point> openTiles = tileMap.getOpenDirections(pos);
        if (openTiles.size() == 0) return ActionStatus.FINISHED;
        target = openTiles.get(Dungeon.THE_RANDOM.nextInt(openTiles.size()));
        entity.getTexture().setState(TextureState.MOVE);
        entity.moveTo(new Point(target.x + pos.x,target.y + pos.y));
        return ActionStatus.WORKING;
    }
}
