package Game.entities.actions;

import Game.entities.Entity;

public abstract class Action {
    int ticks;
    Entity entity;

    public abstract void preformAction();

    public abstract void beforeAction();

    public abstract void afterAction();

    public void addEntity(Entity entity){
        this.entity = entity;
        beforeAction();
    }
}
