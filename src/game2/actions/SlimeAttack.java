package game2.actions;

import game2.entities.Entity;
import game2.enums.TextureState;

import java.awt.*;

public class SlimeAttack extends Attack {

    private final int dx, dy;

    public SlimeAttack(Entity entity, Point targetTile, TextureState attackState, int dmg, int duration) {
        super(entity, targetTile, attackState, dmg, 2 * (duration / 2));
        int tileSize = entity.getTexture().getTileSize();
        this.dx = 2 * (tileSize / this.duration) * (targetTile.x - entity.getPos().x);
        this.dy = 2 * (tileSize / this.duration) * (targetTile.y - entity.getPos().y);
    }

    @Override
    public ActionStatus preform() {
        if (ticks < duration / 2) entity.getTexture().move(dx, dy);
        else entity.getTexture().move(-dx, -dy);
        return super.preform();
    }
}
