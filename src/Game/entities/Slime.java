package Game.entities;


import Game.World;
import Game.imageclasses.Image;

public class Slime extends Enemy {
    public Slime(int x,int y){
        super(new Image(x,y,Image.SLIME));
        this.x = x/50;
        this.y = y/50;
        this.hp = 10 + 2 * World.getCurrentLevel();
    }

    @Override
    protected void move() {
        super.move();
    }

    @Override
    public void tick() {
        super.tick();
    }
}
