package game2.visuals;

import game2.entities.Entity;
import game2.essentials.TileMap;
import game2.levels.Level;
import game2.tiles.Tile;
import game2.visuals.texture.Texture;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameVisuals {

    private final Layer[] layers;

    private int cameraX, cameraY;

    private final int width, height;

    private TileMap tileMap;

    private int tileSize;

    private boolean valid;

    private static final double MIN_ZOOM = 0.5, MAX_ZOOM = 2.0, DEFAULT_ZOOM = 1.0, ZOOM_FACTOR = 1.1;
    private static final int DEFAULT_CAMERA_X = 2000, DEFAULT_CAMERA_Y = 2000;

    private double zoomLevel, prevZoomLevel;

    private final List<LivingTexture> tempTextures;

    public GameVisuals(int width, int height) {
        this.width = width;
        this.height = height;
        this.cameraX = DEFAULT_CAMERA_X;
        this.cameraY = DEFAULT_CAMERA_Y;
        this.zoomLevel = DEFAULT_ZOOM;
        this.prevZoomLevel = DEFAULT_ZOOM;
        Layer middleLayer = new SortedLayer(Comparator.comparing((texture) -> texture.getBounds().y + texture.getBounds().height));
        this.layers = new Layer[]{new Layer(), middleLayer, new Layer()};
        this.tempTextures = new ArrayList<>();
    }

    public synchronized void init(Level level, int tileSize) {
        this.tileMap = level.getTilesMap();
        this.zoomLevel = DEFAULT_ZOOM;
        Point playerPos = level.getPlayerPos();
        this.tileSize = tileSize;
        panCameraTo(playerPos.x * tileSize - width / 2, playerPos.y * tileSize - height / 2);
    }

    private void addTexture(Texture texture) {
        layers[texture.getZ()].addTexture(texture);
    }

    public void tick() {
        for (int i = 0; i < tempTextures.size(); i++) {
            LivingTexture texture = tempTextures.get(i);
            texture.lifeTime--;
            if (texture.lifeTime == 0) {
                tempTextures.remove(i);
                i--;
                invalidate();
            }
        }
    }

    public synchronized void addTexture(Texture texture, int lifeTime) {
        tempTextures.add(new LivingTexture(texture, lifeTime));
        invalidate();
    }

    private void invalidate() {
        this.valid = false;
        for (Layer layer : layers) layer.clear();
    }

    private void validate() {
        int firstX = getFirstTile(cameraX, tileSize, zoomLevel), firstY = getFirstTile(cameraY, tileSize, zoomLevel);
        int lastX = getLastTile(firstX, tileSize, zoomLevel, width, tileMap.getWidth()), lastY = getLastTile(firstY, tileSize, zoomLevel, height, tileMap.getHeight());
        for (int y = firstY; y <= lastY; y++) {
            for (int x = firstX; x <= lastX; x++) {
                Tile tile = tileMap.getTile(x, y);
                Entity e = tile.getEntity();
                addTexture(tile.getTexture());
                if (e != null) addTexture(e.getTexture());

            }
        }
        for (LivingTexture t : tempTextures) {
            addTexture(t.texture);
        }
        this.valid = true;
    }

    public synchronized void draw(Graphics2D g) {
        g.translate(-cameraX, -cameraY);
        g.scale(zoomLevel, zoomLevel);
        invalidate(); // TODO Invalidate should be called on texture move, not here
        if (!valid) {
            validate();
        }
        for (Layer layer : layers) {
            layer.draw(g);
        }
    }

    private static int getFirstTile(int camera, int tileSize, double zoomLevel) {
        return (int) Math.max(0, (camera / (tileSize * zoomLevel)) - 1);
    }

    private static int getLastTile(int firstTile, int tileSize, double zoomLevel, int screenDimension, int tmDimension) {
        return (int) Math.min(tmDimension - 1, ((screenDimension / (double) tileSize)) / zoomLevel + firstTile + 2);
    }

    public synchronized void panCameraTo(int x, int y) {
        this.cameraX = 0;
        this.cameraY = 0;
        panCamera(x, y);
    }

    public synchronized void panCamera(int x, int y) {
        cameraX += x;
        cameraY += y;
        fixCamera();
    }

    /**
     * Zooms in or out on the game, showing more or less of the tileMap. Only the sign of <code>direction</code> matters.
     *
     * @param direction  The rotation of the mouseWheel.
     * @param zoomTarget The <code>Point</code> to zoom towards.
     */
    public synchronized void zoom(int direction, Point zoomTarget) {
        if (direction < 0) {
            zoomLevel *= ZOOM_FACTOR;
        } else {
            zoomLevel /= ZOOM_FACTOR;
        }
        zoomLevel = Math.max(MIN_ZOOM, Math.min(zoomLevel, MAX_ZOOM));
        double zoomDiv = zoomLevel / prevZoomLevel;
        cameraX = (int) ((zoomDiv) * (cameraX) - (1 - zoomDiv) * zoomTarget.x);
        cameraY = (int) ((zoomDiv) * (cameraY) - (1 - zoomDiv) * zoomTarget.y);
        prevZoomLevel = zoomLevel;
        fixCamera();
    }

    private void fixCamera() {
        cameraX = (int) Math.max(0, Math.min(cameraX, zoomLevel * tileSize * tileMap.getWidth() - width - 1));
        cameraY = (int) Math.max(0, Math.min(cameraY, zoomLevel * tileSize * tileMap.getHeight() - height - 1));
        invalidate();
    }

    public Point getTile(int x, int y) {
        x = (int) ((x + cameraX) / (tileSize * zoomLevel));
        y = (int) ((y + cameraY) / (tileSize * zoomLevel));
        return new Point(x, y);
    }

    public synchronized void removeTexture(Texture texture) {
        for (LivingTexture t : tempTextures) {
            if (t.texture == texture) {
                tempTextures.remove(t);
                break;
            }
        }
    }

    private static class LivingTexture {
        private final Texture texture;

        private int lifeTime;

        private LivingTexture(Texture texture, int lifeTime) {
            this.texture = texture;
            this.lifeTime = lifeTime;
        }
    }
}
