package game2.actions;

import game2.Dungeon;
import game2.entities.Entity;
import game2.enums.EntitySound;
import game2.enums.TextureState;
import game2.levels.Level;

import java.awt.*;

public class Attack extends EntityAction {

    protected final int dmg, duration;

    protected final Point targetTile;

    private final TextureState attackState;

    protected int ticks;

    public Attack(Entity entity, Point targetTile, TextureState attackState, int dmg, int duration) {
        super(entity);
        this.dmg = dmg;
        this.targetTile = targetTile;
        this.attackState = attackState;
        this.duration = duration;
        this.ticks = 0;
    }

    @Override
    public ActionStatus init(Level level) {
        entity.getTexture().setState(attackState);
        entity.playSound(EntitySound.ATTACK);
        return ActionStatus.WORKING;
    }

    @Override
    public ActionStatus preform() {
        ticks++;
        if (ticks == duration) return ActionStatus.FINISHED;
        return ActionStatus.WAITING;
    }

    @Override
    public void finish(Level level) {
        Entity target = level.getTile(targetTile).getEntity();
        if (target != null) {
            float dmgFactor = Dungeon.THE_RANDOM.nextFloat() + 0.5f;
            target.attack((int) (dmg * dmgFactor));
        }
        entity.getTexture().setState(TextureState.IDLE);
    }

    @Override
    public void preformInstant() {

    }
}
