package game2.actions;

import game2.entities.Entity;
import game2.essentials.TileMap;

public class EmptyAction extends EntityAction {

    public EmptyAction(Entity entity) {
        super(entity);
    }

    @Override
    public ActionStatus init(TileMap tileMap) {
        return ActionStatus.WAITING;
    }

    @Override
    public ActionStatus preform() {
        return ActionStatus.WAITING;
    }

    @Override
    public void finish() {

    }

    @Override
    public void preformInstant() {

    }
}
