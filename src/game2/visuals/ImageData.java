package game2.visuals;

import game2.Dungeon;

import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageData {

    public final int width, height, swaps;

    public final boolean animation;

    private final BufferedImage[] images;

    public ImageData(int width, int height, BufferedImage image) {
        this(width, height, 0, new BufferedImage[]{image}, false);
    }

    public ImageData(int width, int height, int swaps, BufferedImage[] images){
        this(width, height, swaps, images, true);
    }

    private ImageData(int width, int height, int swaps, BufferedImage[] images, boolean animation){
        this.width = width;
        this.height = height;
        this.swaps = swaps;
        this.images = images;
        this.animation = animation;
    }

    public ImageData(int width, int height, BufferedImage[] images){
        this(width, height, 0, images, false);
    }

    public BufferedImage getImage() {
        return images[0];
    }

    public BufferedImage[] getImages(){
        return images;
    }

    public BufferedImage getImage(Random random){
        return images[random.nextInt(images.length)];
    }
}
