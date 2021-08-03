package game2.actions;

import game2.entities.Entity;
import game2.essentials.TileMap;
import game2.enums.TextureState;

import java.awt.*;

public class Move extends EntityAction {

    protected int tileSize, speed;

    protected final Point moved;
    protected Point target;

    public Move(Entity entity, int speed, int tileSize, Point direction) {
        super(entity);
        this.tileSize = tileSize;
        this.speed = speed;
        this.moved = new Point(0, 0);
        this.target = direction;
    }

    @Override
    public ActionStatus init(TileMap tileMap) {
        Point dest = new Point(entity.getPos().x + target.x, entity.getPos().y + target.y);
        if (tileMap.getTile(dest.x, dest.y).isOpen()) {
            entity.getTexture().setState(TextureState.MOVE);
            entity.moveTo(dest);
            return ActionStatus.WORKING;
        } else {
            return ActionStatus.FINISHED;
        }
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
    public void finish(TileMap tileMap) {
        entity.getTexture().setState(TextureState.IDLE);
    }

    @Override
    public void preformInstant() {
        entity.getTexture().move(tileSize * target.x, tileSize * target.y);
    }
}
