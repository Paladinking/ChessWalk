package game2.visuals.texture;

import game2.enums.TextureState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public class EntityTexture extends ImageTexture {

    private final Rectangle bounds;

    private final Map<TextureState, Displayable> animations;

    private Displayable activeDisplay;

    public EntityTexture(int x, int y, int width, int height){
        this(x, y, width, height, null);
    }

    public EntityTexture(int x, int y, int width, int height, BufferedImage image) {
        super(image, 1);
        this.bounds = new Rectangle(x, y, width, height);
        this.animations = new EnumMap<>(TextureState.class);
        animations.put(TextureState.IDLE, new Image(image));
        this.activeDisplay = animations.get(TextureState.IDLE);
    }

    public void move(int dx, int dy) {
        bounds.x += dx;
        bounds.y += dy;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void addState(TextureState state, BufferedImage[] images, int swapDelay) {
        if (images.length == 1)  animations.put(state, new Image(images[0]));
        else animations.put(state, new Animation(images, swapDelay));
    }

    public void tick() {
        if (activeDisplay.tick()) {
            image = activeDisplay.getImage();
        }
    }

    @Override
    public void setState(TextureState state) {
        activeDisplay.reset();
        if (animations.containsKey(state)) this.activeDisplay = animations.get(state);
        this.image = activeDisplay.getImage();
    }

    private interface Displayable {
        BufferedImage getImage();

        boolean tick();

        void reset();
    }

    private static class Animation implements Displayable{
        private final BufferedImage[] images;
        private final int swapDelay;
        private int index, ticks;

        private Animation(BufferedImage[] images, int swapDelay) {
            this.images = images;
            this.swapDelay = swapDelay;
            this.index = 0;
            this.ticks = 0;
        }

        public boolean tick() {
            ticks++;
            if (ticks == swapDelay) {
                index++;
                if (index == images.length) index = 0;
                ticks = 0;
                return true;
            }
            return false;
        }

        public void reset() {
            ticks = 0;
            index = 0;
        }

        public BufferedImage getImage() {
            return images[index];
        }
    }

    private static class Image implements Displayable {

        private final BufferedImage image;

        private Image(BufferedImage image){
            this.image = image;
        }

        @Override
        public BufferedImage getImage() {
            return image;
        }

        @Override
        public boolean tick() {
            return false;
        }

        @Override
        public void reset() {

        }
    }
}
