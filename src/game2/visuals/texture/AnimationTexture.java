package game2.visuals.texture;

import java.awt.image.BufferedImage;

public class AnimationTexture extends ImageTexture {

    private final BufferedImage[] animation;

    private final int swapDelay;
    private int ticks, index;

    public AnimationTexture(BufferedImage[] images, int x, int y, int width, int height, int swapDelay) {
        super(images[0], 1, x, y, width, height);
        this.animation = images;
        this.swapDelay = swapDelay;
    }

    @Override
    public void tick() {
        ticks++;
        if (ticks == swapDelay){
            ticks = 0;
            index++;
            if (index == animation.length) index = 0;
            image = animation[index];
        }
    }
}
