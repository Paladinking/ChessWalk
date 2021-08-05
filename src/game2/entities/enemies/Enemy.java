package game2.entities.enemies;

import game2.actions.EntityAction;
import game2.entities.Entity;
import game2.essentials.TileMap;
import game2.levels.Level;
import game2.visuals.texture.HpBar;

import java.awt.*;

public abstract class Enemy extends Entity {

    protected int dmg, hp, speed;

    protected Enemy(int x, int y, int hp, int dmg, int speed) {
        super(x, y, dmg, hp);
        this.hp = hp;
        this.dmg = dmg;
        this.speed = speed;
    }

    protected abstract EntityAction getAttack(Level level);

    protected abstract EntityAction getMove(Level level);

    @Override
    protected void interrupted(){
        passTurn();
    }

    @Override
    protected void pickAction(Level level) {
        Point playerPos = level.getPlayerPos();
        if (TileMap.neighbors(gridPos, playerPos)){
            this.action = getAttack(level);
        } else {
            this.action = getMove(level);
        }
    }

    @Override
    public void attack(int dmg){
        super.attack(dmg);
        int tileSize = getTexture().getTileSize();
        listener.createTexture(new HpBar(getHpFraction(), tileSize, gridPos.x * tileSize, (gridPos.y + 1) * tileSize - 5), 30);
    }


}


