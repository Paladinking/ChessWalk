package game2.entities.enemies;

import game2.actions.ActionStatus;
import game2.actions.EmptyAction;
import game2.essentials.TileMap;

public class Skeleton extends Enemy {

    public Skeleton(int x, int y, int hp, int dmg) {
        super(x, y);
    }

    @Override
    protected void pickAction(TileMap tileMap) {
        this.action = new EmptyAction(this){
            @Override public ActionStatus preform(){
                return ActionStatus.FINISHED;
            }
        };
    }
}
