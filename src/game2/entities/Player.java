package game2.entities;

import game2.actions.EmptyAction;
import game2.actions.EntityAction;
import game2.levels.Level;

import java.awt.*;
import java.util.*;

public class Player extends Entity {

    private int dmg, hp;

    private final EntityAction wait = new EmptyAction(this);

    private final Queue<EntityAction> actionQue;

    private int visionDistance;

    public Player(int x, int y, int hp, int dmg) {
        super(x, y, hp, dmg);
        this.actionQue = new LinkedList<>();
    }

    public void queAttack(Point location){
        //Attack attack = new Attack(this,location, TextureState.ATTACK, dmg)
    }

    @Override
    protected void pickAction(Level level) {
        if (actionQue.isEmpty()){
            this.action = wait;
        }
        else {
            action = actionQue.remove();
        }
    }

    public void queAction(EntityAction action, Level level) {
        actionQue.add(action);
        if (this.action == wait) this.action = null;
        initTurn(level);

    }

    public void clearActions() {
        actionQue.clear();
    }

    public void setVisionDistance(int visionDistance) {
        this.visionDistance = visionDistance;
    }

    public int getVisionDistance(){
        return visionDistance;
    }
}
