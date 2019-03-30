package Game.levels.Tiles;

import Game.imageclasses.Image;
import Game.imageclasses.ImageID;
import Game.entities.Entity;

import java.awt.image.BufferedImage;

public class WallTile extends Tile{

    public WallTile(int x, int y, BufferedImage b){
        Image.put(ImageID.getWallId(),new Image(x*50,y*50-20,b));
    }

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
}
