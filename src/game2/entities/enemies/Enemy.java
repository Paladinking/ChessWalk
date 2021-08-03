package game2.entities.enemies;

import game2.actions.EntityAction;
import game2.entities.Entity;
import game2.essentials.TileMap;

import java.awt.*;

public abstract class Enemy extends Entity {

    protected int dmg, hp, speed;

    protected Enemy(int x, int y, int hp, int dmg, int speed) {
        super(x, y);
        this.hp = hp;
        this.dmg = dmg;
        this.speed = speed;
    }

    protected abstract EntityAction getAttack(TileMap tileMap);

    protected abstract EntityAction getMove(TileMap tileMap);

    @Override
    protected void pickAction(TileMap tileMap) {
        Point playerPos = tileMap.getPlayerPos();
        if (TileMap.neighbors(gridPos, playerPos)){
            this.action = getAttack(tileMap);
        } else {
            this.action = getMove(tileMap);
        }
    }


}


