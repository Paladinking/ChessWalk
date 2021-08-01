package game2.entities.enemies;

import game2.actions.ActionStatus;
import game2.actions.EmptyAction;
import game2.essentials.TileMap;
import game2.visuals.Images;
import game2.visuals.texture.EntityTexture;

public class Skeleton extends Enemy {

    public Skeleton(int x, int y) {
        super(x, y);
    }

    @Override
    protected void pickAction(TileMap tileMap) {
        this.action = new EmptyAction(this){
            @Override public ActionStatus preform(){
                return ActionStatus.FINISHED;
            }
        };
    }

    @Override
    protected EntityTexture getTexture(Images images, int tileSize) {
        return new EntityTexture(gridPos.x * tileSize, gridPos.y * tileSize - 20, 50, 70, images.getImage(Skeleton.class));
    }
}
