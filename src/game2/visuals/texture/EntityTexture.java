package game2.visuals.texture;

import java.awt.image.BufferedImage;

public class EntityTexture extends MultiTexture<AnimationTexture> {

    public EntityTexture(int tileSize) {
        super(new AnimationTexture(new BufferedImage[]{null}, 0, 0, 0, 0, 0, 0), tileSize);
    }
}
