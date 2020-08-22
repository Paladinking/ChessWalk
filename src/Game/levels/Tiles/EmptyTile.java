package Game.levels.Tiles;

import Game.GameState;
import Game.assetClasses.Image;
import Game.entities.Enemy;
import Game.entities.Entity;

import java.awt.*;
import java.lang.reflect.GenericArrayType;

public class EmptyTile extends Tile {

    @Override
    public void pressed(Entity e) {
        setEntity(e);
    }

    @Override
    public boolean selected(Entity e) {
        if(entity!=null){
            entity.selected();
            return entity == e;
        }
        return true;
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
        if(entity==null)  return;
        entity.damage(dmg);
    }

    @Override
    public void draw(Graphics g,int x,int y) {
        if(entity!=null) entity.draw(g,x,y);

    }
}
