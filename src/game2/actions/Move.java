package game2.actions;

import game2.entities.Entity;
import game2.enums.TextureState;
import game2.levels.Level;
import game2.visuals.texture.AnimationTexture;
import game2.visuals.texture.MultiTexture;

import java.awt.*;

public class Move extends EntityAction {

    protected int speed, tileSize;

    protected final Point moved;
    protected Point target;

    public Move(Entity entity, int speed, Point direction) {
        super(entity);
        this.speed = speed;
        this.moved = new Point(0, 0);
        this.target = direction;
    }

    @Override
    public ActionStatus init(Level level) {
        Point dest = new Point(entity.getPos().x + target.x, entity.getPos().y + target.y);
        if (level.getTile(dest.x, dest.y).isOpen()) {
            init(dest);
            return ActionStatus.WORKING;
        } else {
            return ActionStatus.FINISHED;
        }
    }

    protected void init(Point dest){
        MultiTexture<AnimationTexture> texture = entity.getTexture();
        texture.setState(TextureState.MOVE);
        tileSize = texture.getTileSize();
        while (tileSize % speed != 0) speed--;
        entity.moveTo(dest);
    }

    @Override
    public ActionStatus preform() {
        int dx = speed * target.x, dy = speed * target.y;
        moved.x += dx;
        moved.y += dy;
        entity.getTexture().move(speed * target.x, speed * target.y);
        if (moved.x == tileSize * target.x && moved.y == tileSize * target.y){
            target = null;
            moved.setLocation(0, 0);
            return ActionStatus.PASS_TURN;
        }
        return ActionStatus.WORKING;
    }

    @Override
    public void finish(Level level) {
        entity.getTexture().setState(TextureState.IDLE);
    }

    @Override
    public void preformInstant() {
        entity.getTexture().move(tileSize * target.x, tileSize * target.y);
    }
}
