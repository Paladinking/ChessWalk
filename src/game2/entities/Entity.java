package game2.entities;

import game2.actions.ActionStatus;
import game2.actions.EntityAction;
import game2.enums.EntitySound;
import game2.levels.Level;
import game2.sound.Sound;
import game2.visuals.ImageData;
import game2.visuals.texture.*;

import java.awt.Point;
import java.util.EnumMap;
import java.util.Map;

public abstract class Entity {

    public static int MOVEMENT_SPEED, ATTACK_DURATION;

    protected int dmg, hp, maxHp;

    private ImageData blood;

    protected EntityListener listener;

    protected EntityAction action;

    private EntityTexture texture;

    protected boolean hidden = true;

    private final Map<EntitySound, Sound> sounds;

    protected final Point gridPos;

    protected Entity(int x, int y, int dmg, int hp) {
        this.dmg = dmg;
        this.hp = hp;
        this.maxHp = hp;
        this.gridPos = new Point(x, y);
        this.sounds = new EnumMap<>(EntitySound.class);
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
            ActionStatus status = action.init(level);
            if (status == ActionStatus.INTERRUPTED){
                interrupted();
                return;
            } else if (status.ordinal() >= ActionStatus.PASS_TURN.ordinal()){
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

    protected abstract void interrupted();

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
    public void setTexture(EntityTexture texture){
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
        playSound(EntitySound.MOVE);
    }

    public void playSound(EntitySound sound){
        if (!hidden) sounds.get(sound).play();
    }

    /**
     * This entity is attacked with <code>dmg</code> damage.
     * @param dmg The amount of damage to deal.
     */
    public void attack(int dmg) {
        this.hp -= dmg;
        int tileSize = texture.getTileSize();
        listener.createTexture(new ImageTexture(blood.getImage(), 2, gridPos.x * tileSize, gridPos.y * tileSize, blood.width, blood.height, tileSize), 20);
        if (hp <= 0) {
            if (action != null) passTurn();
            listener.died();
        }
        playSound(EntitySound.HURT);
    }

    public void hide(){
        this.hidden = true;
        texture.setVisible(false);
    }

    public void show(){
        this.hidden = false;
        texture.setVisible(true);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
               "dmg=" + dmg +
               ", hp=" + hp +
               ", hidden=" + hidden +
               ", gridPos=" + gridPos +
               '}';
    }

    public float getHpFraction(){
        return hp / (float) maxHp;
    }

    public void moveTexture(int dx, int dy){
        this.texture.move(dx, dy);
    }

    public void setSounds(Map<EntitySound, Sound> sounds) {
        this.sounds.clear();
        this.sounds.putAll(sounds);
    }
}
