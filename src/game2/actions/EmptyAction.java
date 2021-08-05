package game2.actions;

import game2.entities.Entity;
import game2.levels.Level;

public class EmptyAction extends EntityAction {

    public EmptyAction(Entity entity) {
        super(entity);
    }

    @Override
    public ActionStatus init(Level level) {
        return ActionStatus.WAITING;
    }

    @Override
    public ActionStatus preform() {
        return ActionStatus.WAITING;
    }

    @Override
    public void finish(Level level) {

    }

    @Override
    public void preformInstant() {

    }
}
