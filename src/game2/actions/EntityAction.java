package game2.actions;

import game2.entities.Entity;
import game2.essentials.TileMap;

public abstract class EntityAction {

    protected final Entity entity;

    protected EntityAction(Entity entity){
        this.entity = entity;
    }

    public abstract ActionStatus init(TileMap tileMap);

    public abstract ActionStatus preform();

    public abstract void finish();

    public abstract void preformInstant();

}
