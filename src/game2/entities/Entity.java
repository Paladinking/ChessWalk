package game2.entities;

import game2.actions.ActionStatus;
import game2.actions.EntityAction;
import game2.essentials.TileMap;
import game2.visuals.Images;
import game2.visuals.texture.EntityTexture;

import java.awt.*;

public abstract class Entity {

    protected EntityListener listener;

    protected EntityAction action;

    private EntityTexture texture;

    protected final Point gridPos;

    protected Entity(int x, int y) {
        this.gridPos = new Point(x, y);
    }

    public Point getPos(){
        return gridPos;
    }

    public void setListener(EntityListener listener){
        this.listener = listener;
    }

    /**
     * Sets <code>action</code> to a non null value.
     * @param tileMap The <code>TileMap</code> containing this entity.
     */
    protected abstract void pickAction(TileMap tileMap);

    public void initTurn(TileMap tileMap){
        if (this.action == null){
            pickAction(tileMap);
            if (action.init(tileMap).ordinal() >= ActionStatus.PASS_TURN.ordinal()){
                action.finish();
                action = null;
                listener.passedTurn();
            }
        }
    }

    public void tick(){
        // TODO: tick should only be called before this turns action is completed
        if (this.action == null) return;
        ActionStatus status = action.preform();
        if (status.ordinal() >= ActionStatus.PASS_TURN.ordinal()){
            action.finish();
            action = null;
            listener.passedTurn();
        }
    }

    /**
     * Creates and returns a new <code>Texture</code> object for this <code>Entity</code>.
     * @param images The <code>Images</code> object containing the texture image.
     * @param tileSize The size of a tile in the <code>TileMap</code>.
     * @return The texture for this <code>Entity</code>.
     */
    protected abstract EntityTexture getTexture(Images images, int tileSize);

    /**
     * Creates a texture for this <code>Entity</code>.
     * @param images The <code>Images</code> object containing the texture image.
     * @param tileSize The size of a tile in the <code>TileMap</code>.
     */
    public void createTexture(Images images, int tileSize){
        this.texture = getTexture(images, tileSize);
    }

    /**
     * Returns this <code>Entity</code>:s current texture
     * @return the <code>Texture</code> object of this <code>Entity</code>.
     */
    public EntityTexture getTexture(){
        return texture;
    }

    public void moveTo(Point dest) {
        Point oldPos = new Point(gridPos.x, gridPos.y);
        gridPos.setLocation(dest.x, dest.y);
        listener.moved(oldPos);
    }
}
