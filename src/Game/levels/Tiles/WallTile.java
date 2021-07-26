package Game.levels.Tiles;

import Game.GameState;
import Game.assets.Image;
import Game.entities.Entity;

import java.awt.*;

public class WallTile extends Tile{


    public WallTile(){
    }
    @Override
    public void pressed(Entity e) {
        setEntity(e);
    }

    @Override
    public boolean selected(Entity e) {
        return false;
    }

    @Override
    public void stopped() {

    }

    @Override
    public void setEntity(Entity e) {
        entity = e;
    }

    @Override
    public void removeEntity() {
        entity = null;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public void Attack(int dmg) {

    }

    @Override
    public void draw(Graphics g,int x,int y) {
        g.drawImage(Image.WALL_FRONT,x*GameState.tileSize+GameState.offsetX+GameState.globalAdjX,-20*GameState.tileSize/ GameState.initialSize+y*GameState.tileSize+GameState.offsetY+GameState.globalAdjY,
                Image.WALL_FRONT.getWidth()*GameState.tileSize/ GameState.initialSize,Image.WALL_FRONT.getHeight()*GameState.tileSize/ GameState.initialSize,
                null);

    }
}
