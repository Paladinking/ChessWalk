package game2.entities;

import game2.actions.ActionStatus;
import game2.actions.EntityAction;
import game2.levels.Level;
import game2.visuals.ImageData;
import game2.visuals.texture.AnimationTexture;
import game2.visuals.texture.MultiTexture;

import java.awt.Point;

public abstract class Entity {

    protected int dmg, hp;

    private ImageData blood;

    protected EntityListener listener;

    protected EntityAction action;

    private MultiTexture<AnimationTexture> texture;

    private boolean hidden = true;

    protected final Point gridPos;

    protected Entity(int x, int y, int dmg, int hp) {
        this.dmg = dmg;
        this.hp = hp;
        this.gridPos = new Point(x, y);
    }

    public void setBlood(ImageData blood){
        this.blood = blood;
    }

    public Point getPos(){
        return gridPos;
    }

    protected void passTurn() {
        // TODO make Sure no prints happen
        if (this.action == null){
            System.out.println("This should not happen: " + this);
            return;
        }
        this.action = null;
        listener.passedTurn();
    }

    public void setListener(EntityListener listener){
        this.listener = listener;
    }

    /**
     * Sets <code>action</code> to a non null value.
     * @param level The <code>TileMap</code> containing this entity.
     */
    protected abstract void pickAction(Level level);

    public void initTurn(Level level){
        if (this.action == null){
            pickAction(level);
            if (action.init(level).ordinal() >= ActionStatus.PASS_TURN.ordinal()){
                finishAction(level);
                return;
            }
        }
        if (hidden){
            action.preformInstant();
            finishAction(level);
        }
    }

    private void finishAction(Level level){
        action.finish(level);
        passTurn();
    }

    public void tick(Level tileMap){
        // TODO: tick should only be called before this turns action is completed
        texture.tick();
        if (this.action == null) return;
        ActionStatus status = action.preform();
        if (status.ordinal() >= ActionStatus.PASS_TURN.ordinal()){
            finishAction(tileMap);
        }
    }

    /**
     * Sets the texture of this <code>Entity</code>.
     * @param texture The new texture.
     */
    public void setTexture(MultiTexture<AnimationTexture> texture){
        this.texture = texture;
    }

    /**
     * Returns this <code>Entity</code>:s current texture
     * @return The <code>Texture</code> object of this <code>Entity</code>.
     */
    public MultiTexture<AnimationTexture> getTexture(){
        return texture;
    }

    public void moveTo(Point dest) {
        Point oldPos = new Point(gridPos.x, gridPos.y);
        gridPos.setLocation(dest.x, dest.y);
        listener.moved(oldPos);
    }

    /**
     * This entity is attacked with <code>dmg</code> damage.
     * @param dmg The amount of damage to deal.
     */
    public void attack(int dmg) {
        listener.createTexture(blood);
        this.hp -= dmg;
        if (hp <= 0) {
            if (action != null) passTurn();
            listener.died();
        }
    }

    public void hide(){
        this.hidden = true;
        texture.setVisible(false);
    }

    public void show(){
        this.hidden = false;
        texture.setVisible(true);
    }
}
