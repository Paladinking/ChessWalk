package Game.entities.actions;

import Game.entities.Entity;

public abstract class Action {
    int ticks;
    Entity entity;

    public void preformAction(){
        if(ticks++>tickLength()) {
            afterAction();
        }
    }
    public abstract int tickLength();

    public abstract void beforeAction();

    public void afterAction(){
        entity.clearAction();
    }

    public void addEntity(Entity entity){
        this.entity = entity;
        beforeAction();
    }
    public int getTicks(){
        return ticks;
    }

}
