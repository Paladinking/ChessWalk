package Game.levels.Tiles;

import Game.GameState;
import Game.assetClasses.Image;
import Game.assetClasses.ImageID;
import Game.entities.Entity;

import java.awt.image.BufferedImage;

public class WallTile extends Tile{

    public WallTile(int x, int y, BufferedImage b){
        Image.put(ImageID.getId(),new Image(x* GameState.tileSize,y*GameState.tileSize-20,b));
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

    @Override
    public void Attack(int dmg) {

    }
}
