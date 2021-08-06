package game2.entities;

import game2.actions.Attack;
import game2.actions.EmptyAction;
import game2.actions.EntityAction;
import game2.actions.Move;
import game2.enums.TextureState;
import game2.levels.Level;
import game2.visuals.texture.HpBar;
import game2.visuals.texture.Texture;

import java.awt.*;
import java.util.*;

public class Player extends Entity {

    private final EntityAction wait = new EmptyAction(this);

    private final Queue<EntityAction> actionQue;

    private HpBar hpBar;

    private int visionDistance;

    public Player(int x, int y, int hp, int dmg) {
        super(x, y, dmg, hp);
        this.actionQue = new LinkedList<>();
    }

    public void queAttack(Point target, Level level){
        Attack attack = new Attack(this, target, TextureState.ATTACK, dmg, ATTACK_DURATION);
        queAction(attack, level);
    }

    public void queMovement(Point target, Level level) {
        Move move = new Move(this, MOVEMENT_SPEED, target);
        queAction(move, level);
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

    @Override
    protected void interrupted() {
        actionQue.clear();
        action = wait;
    }

    public void queAction(EntityAction action, Level level) {
        actionQue.add(action);
        if (this.action == wait) {
            this.action = null;
            initTurn(level);
        }
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

    @Override
    public void moveTexture(int dx, int dy){
        if (hpBar != null) hpBar.move(dx, dy);
        super.moveTexture(dx, dy);
    }

    @Override
    protected void passTurn(){
        if (hp < maxHp) {
            hp++;
            if (hp == maxHp){
                listener.removeTexture(hpBar);
                hpBar = null;
            } else {
                hpBar.setHpFraction(getHpFraction());
            }
        }
        super.passTurn();
    }

    @Override
    public void attack(int dmg){
        super.attack(dmg);
        int tileSize = getTexture().getTileSize();
        if (hpBar == null) {
            hpBar = new HpBar(getHpFraction(), tileSize, gridPos.x * tileSize, (gridPos.y + 1) * tileSize - 5);
            listener.createTexture(hpBar, 0);
        } else {
            hpBar.setHpFraction(getHpFraction());
        }
    }

    public boolean isWaiting() {
        return action == wait;
    }
}
