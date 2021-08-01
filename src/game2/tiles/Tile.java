package game2.tiles;

import game2.entities.Entity;
import game2.levels.Level;
import game2.visuals.texture.Texture;

public abstract class Tile {

    private Texture texture;

    public abstract Entity getEntity();

    public Texture getTexture(){
        return texture;
    }

    protected abstract Texture getTexture(int x, int y, int tileSize, Level level);

    public void createTexture(int x, int y, int tileSize, Level level){
        texture = getTexture(x, y, tileSize, level);
    }

    public abstract boolean setEntity(Entity entity);
}