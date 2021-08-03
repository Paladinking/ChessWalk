package game2.actions;

import game2.entities.Entity;
import game2.essentials.TileMap;

public class NoAction extends EmptyAction{
    public NoAction(Entity entity) {
        super(entity);
    }

    @Override
    public ActionStatus init(TileMap tileMap){
        return ActionStatus.FINISHED;
    }
}
