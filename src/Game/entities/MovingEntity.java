package Game.entities;


import Game.assets.Image;
import Game.entities.actions.Movement;
import Game.levels.Tilemap;

public class MovingEntity extends Entity {
    private int xMoved,yMoved;
    @Override
    public void tick() {

    }

    @Override
    public void selected() {

    }

     Movement move(Movement m){
        int dX = 0,dY = 0;
        switch (m.getDirection()){
            case Movement.UP:
                dY = -5;
                m.deltaY+=5;
                break;
            case Movement.DOWN:
                dY = 5;
                m.deltaY-=5;
                break;
            case Movement.LEFT:
                dX = -5;
                m.deltaX+=5;
                break;
            case Movement.RIGHT:
                dX = 5;
                m.deltaX-=5;
                break;
        }
        yMoved += dY;
        xMoved += dX;
        if(Math.abs(xMoved+yMoved)==50) Tilemap.removeEntity(x,y);
        if(yMoved==-50){
            y--;
            yMoved = 0;
            Tilemap.getTile(x,y).pressed(this);
        } else if(yMoved == 50){
            y ++;
            yMoved = 0;
            Tilemap.getTile(x,y).pressed(this);
        } else if(xMoved ==-50){
            x --;
            xMoved =0;
            Tilemap.getTile(x,y).pressed(this);
        } else if(xMoved == 50){
            x++;
            xMoved =0;
            Tilemap.getTile(x,y).pressed(this);
        }
         Image.getImage(imgId).moveImage(dX,dY);
        return m;
    }
}
