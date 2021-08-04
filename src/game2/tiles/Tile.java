package game2.tiles;

import game2.entities.Entity;
import game2.enums.TextureState;
import game2.visuals.texture.ImageTexture;
import game2.visuals.texture.MultiTexture;

public abstract class Tile {

    protected Tile(){

    }

    private MultiTexture<ImageTexture> texture;

    public abstract Entity getEntity();

    public MultiTexture<ImageTexture> getTexture() {
        return texture;
    }

    public void setTexture(MultiTexture<ImageTexture> texture){
        this.texture = texture;
    }

    public abstract void setEntity(Entity entity);

    public abstract boolean isOpen();

    public void hide() {
        getTexture().setState(TextureState.FOW);
    }

    public void show() {
        getTexture().setState(TextureState.SHOWN);
    }

    @Override
    public String toString() {
        return super.toString() + "Entity= " + getEntity();
    }
}
