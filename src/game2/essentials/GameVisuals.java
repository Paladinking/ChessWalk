package game2.essentials;

import game2.entities.Entity;
import game2.tiles.Tile;
import game2.visuals.Layer;
import game2.visuals.SortedLayer;
import game2.visuals.texture.AbstractTexture;

import java.awt.*;
import java.util.Comparator;

public class GameVisuals {

    private final Layer[] layers;

    private int cameraX, cameraY;

    private final int width, height;

    private TileMap tileMap;

    private boolean valid;

    private static final double MIN_ZOOM = 0.5, MAX_ZOOM = 2.0, DEFAULT_ZOOM = 1.0, ZOOM_FACTOR = 1.1;
    private static final int DEFAULT_CAMERA_X = 2000, DEFAULT_CAMERA_Y = 2000;

    private double zoomLevel, prevZoomLevel;

    public GameVisuals(int width, int height) {
        this.width = width;
        this.height = height;
        this.cameraX = DEFAULT_CAMERA_X;
        this.cameraY = DEFAULT_CAMERA_Y;
        this.zoomLevel = DEFAULT_ZOOM;
        this.prevZoomLevel = DEFAULT_ZOOM;
        Layer middleLayer = new SortedLayer(Comparator.comparing((texture) -> texture.getBounds().y + texture.getBounds().height));
        this.layers = new Layer[]{new Layer(), middleLayer, new Layer()};
    }

    public synchronized void setTileMap(TileMap tileMap){
        this.tileMap = tileMap;
        this.zoomLevel = DEFAULT_ZOOM;
        Point playerPos = tileMap.getPlayerPos();
        panCameraTo(playerPos.x * tileMap.getTileSize() - width / 2, playerPos.y * tileMap.getTileSize() - height / 2);
    }

    private void addTexture(AbstractTexture texture) {
        layers[texture.getZ()].addTexture(texture);
    }

    private void invalidate() {
        this.valid = false;
        for (Layer layer : layers) layer.clear();
    }

    private void validate() {
        int tileSize = tileMap.getTileSize();
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
        this.valid = true;
    }

    public synchronized void draw(Graphics2D g) {
        g.translate(-cameraX, -cameraY);
        g.scale(zoomLevel, zoomLevel);
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

    public synchronized void panCameraTo(int x, int y){
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
        int tileSize = tileMap.getTileSize();
        cameraX = (int) Math.max(0, Math.min(cameraX, zoomLevel * tileSize * tileMap.getWidth() - width - 1));
        cameraY = (int) Math.max(0, Math.min(cameraY, zoomLevel * tileSize * tileMap.getHeight() - height - 1));
        invalidate();
    }

    public Point getTile(int x, int y) {
        int tileSize = tileMap.getTileSize();
        x = (int) ((x + cameraX) / (tileSize * zoomLevel));
        y = (int) ((y + cameraY) / (tileSize * zoomLevel));
        return new Point(x, y);
    }
}
