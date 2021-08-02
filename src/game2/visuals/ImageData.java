package game2.visuals;

import game2.enums.ImageType;

public class ImageData<T extends ImageType> {

    public int width, height, count, swaps;

    public T key;

    public ImageData(int width, int height, int count, int swaps, T key) {
        this.width = width;
        this.height = height;
        this.count = count;
        this.swaps = swaps;
        this.key = key;
    }
}
