package Game.entities;

import Game.World;
import Game.assets.Image;

public class Knight extends Enemy{
    public Knight(int x,int y){
        super(new Image(x,y-20,Image.KNIGHT));
        this.hp = 20 + 4 * World.getCurrentLevel();
    }

    @Override
    public void move() {

    }

    @Override
    public void tick() {

    }

}
