package game2.actions;

import game2.entities.Entity;
import game2.levels.Level;

public class NoAction extends EmptyAction{
    public NoAction(Entity entity) {
        super(entity);
    }

    @Override
    public ActionStatus init(Level level){
        return ActionStatus.FINISHED;
    }
}
