package game2.actions;

import game2.entities.Entity;
import game2.levels.Level;

public abstract class EntityAction {

    protected final Entity entity;

    protected EntityAction(Entity entity){
        this.entity = entity;
    }

    public abstract ActionStatus init(Level level);

    public abstract ActionStatus preform();

    public abstract void finish(Level level);

    public abstract void preformInstant();

}
