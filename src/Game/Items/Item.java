package Game.Items;

import Game.entities.Entity;

import java.awt.*;

public abstract class Item  {
    int dx,dy;
    Entity entity,owner;
     Image image;
    Item(){

    }

    public abstract void beforeUse();

    public abstract void afterUse();

    public abstract int getUseTime();

    Item(Image i,Entity owner){
        this.image  =i;
        this.owner = owner;
    }
    public void draw(Graphics g,int x,int y){
        g.drawImage(image,x,y,null);
    }

    public abstract void use(int t);

    public  boolean reUse(int dx, int dy, Entity e){
        this.dx = dx;
        this.dy = dy;
        this.entity = e;
        return true;
    }
}
