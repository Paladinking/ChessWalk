package game2.entities;

import game2.actions.ActionStatus;
import game2.actions.EntityAction;
import game2.essentials.TileMap;
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
                action.finish(tileMap);
                action = null;
                listener.passedTurn();
            }
        }
    }

    public void tick(TileMap tileMap){
        // TODO: tick should only be called before this turns action is completed
        texture.tick();
        if (this.action == null) return;
        ActionStatus status = action.preform();
        if (status.ordinal() >= ActionStatus.PASS_TURN.ordinal()){
            action.finish(tileMap);
            action = null;
            listener.passedTurn();
        }
    }

    /**
     * Sets the texture of this <code>Entity</code>.
     * @param texture The new texture.
     */
    public void setTexture(EntityTexture texture){
        this.texture = texture;
    }

    /**
     * Returns this <code>Entity</code>:s current texture
     * @return The <code>Texture</code> object of this <code>Entity</code>.
     */
    public EntityTexture getTexture(){
        return texture;
    }

    public void moveTo(Point dest) {
        Point oldPos = new Point(gridPos.x, gridPos.y);
        gridPos.setLocation(dest.x, dest.y);
        listener.moved(oldPos);
    }

    /**
     * This entity is attacked with <code>dmg</code> damgage.
     * @param dmg The amount of damage to deal.
     */
    public void attack(int dmg) {

    }
}
