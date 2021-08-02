package game2.entities;

import game2.actions.EmptyAction;
import game2.actions.EntityAction;
import game2.essentials.TileMap;

import java.util.*;

public class Player extends Entity {

    private final EntityAction wait = new EmptyAction(this);

    private final Queue<EntityAction> actionQue;

    public Player(int x, int y, int hp, int dmg) {
        super(x, y);
        this.actionQue = new LinkedList<>();
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
}
