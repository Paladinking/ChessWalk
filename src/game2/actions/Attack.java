package game2.actions;

import game2.entities.Entity;
import game2.essentials.TileMap;
import game2.enums.TextureState;

import java.awt.*;

public class Attack extends EntityAction {

    private final int dmg, duration;

    private final Point targetTile;

    private final TextureState attackState;

    private int ticks;

    public Attack(Entity entity, Point targetTile, TextureState attackState, int dmg, int duration) {
        super(entity);
        this.dmg = dmg;
        this.targetTile = targetTile;
        this.attackState = attackState;
        this.duration = duration;
        this.ticks = 0;
    }

    @Override
    public ActionStatus init(TileMap tileMap) {
        if (tileMap.getTile(targetTile.x, targetTile.y).getEntity() == null) return ActionStatus.FINISHED;
        entity.getTexture().setState(attackState);
        return ActionStatus.WORKING;
    }

    @Override
    public ActionStatus preform() {
        ticks++;
        if (ticks == duration) return ActionStatus.FINISHED;
        return ActionStatus.WAITING;
    }

    @Override
    public void finish(TileMap tileMap) {
        Entity target = tileMap.getTile(targetTile).getEntity();
        if (target != null) target.attack(dmg);
        entity.getTexture().setState(TextureState.IDLE);
    }

    @Override
    public void preformInstant() {

    }
}
