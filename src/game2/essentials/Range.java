package game2.essentials;

import java.util.Random;

public class Range {

    public int min, max;

    public Range(int min, int max){
        this.min = min;
        this.max = max;
    }

    public int getRandom(Random random){
        if (min == max) return min;
        return random.nextInt(max - min) + min;
    }
}
