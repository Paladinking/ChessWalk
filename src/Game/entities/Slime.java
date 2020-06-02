package Game.entities;



import Game.GameState;
import Game.assetClasses.Image;
import Game.entities.actions.Attack;

import java.awt.image.BufferedImage;

public class Slime extends Enemy {
    public Slime(int x,int y){
        super(new Image(x,y,Image.SLIME));
        this.x = x/GameState.tileSize;
        this.y = y/GameState.tileSize;
        this.hp = 10 + 2 * GameState.currentLevel;
    }

    @Override
    protected void move() {
        super.move();
    }

    @Override
    public void tick() {
        super.tick();
    }
    @Override
    public void damage(int dmg){
        super.damage(dmg);
    }

    @Override
    public BufferedImage[][] getAttackImages() {
       return Image.PLAYER_ATTACK;
    }

    @Override
    public void attackAnimation(int tick, int dx,int dy) {
        int ft = Attack.tickLength;
        if(tick<ft/2) Image.getImage(imgId).moveImage(dx*GameState.tileSize/(ft/2f),dy*GameState.tileSize/(ft/2f));
        else Image.getImage(imgId).moveImage(-dx*GameState.tileSize/(ft/2f),-dy*GameState.tileSize/(ft/2f));

    }
    @Override
    public void afterAttack(int dx, int dy){
        Image.put(imgId,new Image(x*GameState.tileSize,y*GameState.tileSize,Image.SLIME));
    }
    @Override
    public java.awt.Image getBlood(){
        return Image.SLIME_BLOOD;
    }

}
