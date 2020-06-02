package Game.entities;

import Game.GameState;
import Game.entities.actions.Action;
import Game.entities.actions.Stand;
import Game.assetClasses.Image;

import java.awt.image.BufferedImage;

public class Knight extends Enemy{
    public Knight(int x,int y){
        super(new Image(x,y-20,Image.KNIGHT));
        this.x = x/GameState.tileSize;
        this.y = y/GameState.tileSize;
        this.hp = 20 + 4 * GameState.currentLevel;
    }

    @Override
    public void move() {

    }

    @Override
    public void tick() {

    }
    @Override
    public Action assignAction(){
         return new Stand(this);
    }

    @Override
    public BufferedImage[][] getAttackImages() {
        return new BufferedImage[0][];
    }

}
