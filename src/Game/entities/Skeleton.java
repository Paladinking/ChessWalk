package Game.entities;

import Game.GameState;
import Game.assetClasses.Image;
import Game.entities.actions.Action;
import Game.entities.actions.Movement;

import java.awt.image.BufferedImage;

public class Skeleton extends Enemy {
    public Skeleton(int x,int y){
        super(new Image(x,y-20,Image.SKELETON[Image.DOWN]));
        this.x = x/ GameState.tileSize;
        this.y = y/GameState.tileSize;
        this.hp = 20 + 4 * GameState.currentLevel;
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public void tick() {
        if(currentAction instanceof Movement){
            Image.getImage(imgId).setImg(Image.SKELETON[((Movement) currentAction).getDirection()]);
        }
        super.tick();
    }
    @Override
    public void attackAnimation(int tick, int dx,int dy) {
        Image.getImage(imgId).setImg(Image.SKELETON[new Movement(dx,dy).getDirection()]);
       super.attackAnimation(tick,dx,dy);
    }
    @Override
    public Action assignAction(){
        return super.assignAction();
    }

    @Override
    public BufferedImage[][] getAttackImages() {
        return new BufferedImage[0][];
    }
}
