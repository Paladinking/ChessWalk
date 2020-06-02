package Game.Items;

import Game.entities.Entity;

import java.awt.*;

public class Empty extends Item {
    @Override
    public void use(int t) {

    }



    @Override
    public void beforeUse() {

    }

    @Override
    public void afterUse() {

    }

    @Override
    public int getUseTime() {
        return 0;
    }

    @Override
    public void draw(Graphics g,int x,int y){

    }
    @Override
    public  boolean reUse(int dx, int dy, Entity e){
       return false;
    }
}
