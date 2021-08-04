package game2.entities;

import game2.actions.Attack;
import game2.actions.EmptyAction;
import game2.actions.EntityAction;
import game2.enums.TextureState;
import game2.essentials.TileMap;

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
    protected void pickAction(TileMap tileMap) {
        if (actionQue.isEmpty()){
            this.action = wait;
        }
        else {
            action = actionQue.remove();
        }
    }

    public void queAction(EntityAction action, TileMap tileMap) {
        actionQue.add(action);
        if (this.action == wait) this.action = null;
        initTurn(tileMap);

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
