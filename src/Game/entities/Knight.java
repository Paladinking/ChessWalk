package Game.entities;

import Game.GameState;
import Game.entities.actions.Action;
import Game.entities.actions.Attack;
import Game.entities.actions.Stand;
import Game.assetClasses.Image;
import Game.levels.Tilemap;

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
        for(int i=x-1;i<=x+1;i++){
            for(int j=y-1;j<=y+1;j++){
                if(Tilemap.getTile(i,j).getEntity()instanceof Player) return new Attack(i-x,j-y,dmg,this);
            }
        }

         return new Stand(this);
    }

    @Override
    public BufferedImage[][] getAttackImages() {
        return new BufferedImage[0][];
    }

}
