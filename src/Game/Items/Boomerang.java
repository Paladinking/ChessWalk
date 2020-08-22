package Game.Items;

import Game.GameState;
import Game.entities.Entity;
import Game.entities.Player;
import Game.assetClasses.Image;
import Game.assetClasses.ImageID;
import Game.assetClasses.RotatingImage;
import Game.levels.Tilemap;
import Game.levels.Tiles.Tile;

import java.lang.annotation.Target;

public class Boomerang extends Item {

    private boolean hasHit, turned;
    private int imgId,bloodId;

    @Override
    public void beforeUse() {
        int x = entity.getX()*GameState.tileSize;
        int y = entity.getY()*GameState.tileSize;
        imgId = ImageID.getId();
        Image.put(imgId, new RotatingImage(x , y, image));

    }

    @Override
    public void afterUse() {
        Image.remove(imgId);
        Image.remove(bloodId);
    }

    public int getUseTime() {
        return -1;
    }

    public Boomerang(int x, int y, Entity owner) {
        super(Image.BOOMERANG, owner);
    }


    @Override
    public void use(int t) {
        int ft = (int) (10 * Math.sqrt(dx * dx + dy * dy));
        ((RotatingImage) Image.getImage(imgId)).rotate(Math.PI / 16);
        Tile tile = Tilemap.getTile((int) ((Image.getImage(imgId).getX() + Image.getImage(imgId).getImg().getWidth(null) / 2) / GameState.tileSize),
                                    (int) ((Image.getImage(imgId).getY() + Image.getImage(imgId).getImg().getHeight(null) / 2) / GameState.tileSize));
        if (turned && tile.getEntity() instanceof Player) {
            Image.getImage(imgId).setHidden(true);
            return;
        }
        if (!tile.selected(owner)) {
            turned = true;
            if (!hasHit) {
                bloodId = ImageID.getId();
                Entity target = tile.getEntity();
                if (target!=null)Image.put(bloodId,new Image(target.getX()*GameState.tileSize,target.getY()*GameState.tileSize+1, target.getBlood()));
                tile.Attack(4);
                hasHit = true;
            }
            t += ft / 2;
        }
        if (t > ft / 2 && !turned) {
            tile.pressed(null);
            turned = true;
        }
        if (!turned) {
            Image.getImage(imgId).moveImage(dx *((float)GameState.tileSize) / ft * 2, dy * ((float)GameState.tileSize) / ft * 2);
        } else Image.getImage(imgId).moveImage(-dx * ((float)GameState.tileSize) / ft * 2, -dy * ((float)GameState.tileSize) / ft * 2);

    }

    @Override
    public boolean reUse(int dx, int dy, Entity e) {
        if (Math.abs(dx) > 5 || Math.abs(dy) > 5) return false;
        this.dx = dx;
        this.dy = dy;
        this.entity = e;
        hasHit = false;
        turned = false;
        return true;
    }


}
