package Game.entities.actions;


import Game.entities.Enemy;
import Game.entities.Entity;

public class Stand extends Action {


    public Stand(Entity e){
        this.addEntity(e);

    }
    @Override
    public void preformAction() {
        super.preformAction();
    }

    @Override
    public int tickLength() {
        return 2;
    }

    @Override
    public void beforeAction() {

    }

    @Override
    public void afterAction() {
        super.afterAction();
    }
}
