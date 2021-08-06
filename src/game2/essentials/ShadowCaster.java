package game2.essentials;

import game2.levels.Level;

/**
 * Copied from https://github.com/watabou/pixel-dungeon/blob/ca458a28f053612973d5d6059dae5f6f2ca4fcb7/src/com/watabou/pixeldungeon/mechanics/ShadowCaster.java
 *
 */
public class ShadowCaster {

    private static final int MAX_DISTANCE = 10;

    private int[] limits;

    private final Level level;

    private final static int[][] rounding;
    static {
        rounding = new int[MAX_DISTANCE+1][];
        for (int i=1; i <= MAX_DISTANCE; i++) {
            rounding[i] = new int[i+1];
            for (int j=1; j <= i; j++) {
                rounding[i][j] = (int)Math.min(j, Math.round(i * Math.cos(Math.asin( j / (i + 0.5) ))));
            }
        }
    }

    private final Obstacles obs;

    public ShadowCaster(Level level) {
        this.obs = new Obstacles();
        this.level = level;
    }

    public void castShadow(int x, int y, int distance) {
        limits = rounding[distance];
        level.show(x, y);
        int width = level.getWidth(), height = level.getHeight();

        scanSector(x, y, +1, +1, 0, 0, width, height, distance);
        scanSector(x, y, -1, +1, 0, 0, width, height, distance);
        scanSector(x, y, +1, -1, 0, 0, width, height, distance);
        scanSector(x, y, -1, -1, 0, 0, width, height, distance);
        scanSector(x, y, 0, 0, +1, +1, width, height, distance);
        scanSector(x, y, 0, 0, -1, +1, width, height, distance);
        scanSector(x, y, 0, 0, +1, -1, width, height, distance);
        scanSector(x, y, 0, 0, -1, -1, width, height, distance);
    }

    private void scanSector(int cx, int cy, int m1, int m2, int m3, int m4, int width, int height, int distance) {

        obs.reset();

        for (int p=1; p <= distance; p++) {

            float dq2 = 0.5f / p;

            int pp = limits[p];
            for (int q=0; q <= pp; q++) {

                int x = cx + q * m1 + p * m3;
                int y = cy + p * m2 + q * m4;

                if (y >= 0 && y < height && x >= 0 && x < width) {

                    float a0 = (float)q / p;
                    float a1 = a0 - dq2;
                    float a2 = a0 + dq2;


                    if (obs.isFree(a0) || obs.isFree(a1) || obs.isFree(a2)) {
                        level.show(x, y);
                    }
                    if (level.isWall(x, y)) {
                        obs.add( a1, a2 );
                    }

                }
            }

            obs.nextRow();
        }
    }

    private static class Obstacles {

        private final static int SIZE = (MAX_DISTANCE+1) * (MAX_DISTANCE+1) / 2;
        private final static float[] a1 = new float[SIZE];
        private final static float[] a2 = new float[SIZE];

        private int length;
        private int limit;

        public void reset() {
            length = 0;
            limit = 0;
        }

        public void add( float o1, float o2 ) {

            if (length > limit && o1 <= a2[length-1]) {

                // Merging several blocking cells
                a2[length-1] = o2;

            } else {

                a1[length] = o1;
                a2[length++] = o2;

            }

        }

        public boolean isFree(float a ) {
            for (int i=0; i < limit; i++) {
                if (a >= a1[i] && a <= a2[i]) {
                    return false;
                }
            }
            return true;
        }

        public void nextRow() {
            limit = length;
        }
    }
}
