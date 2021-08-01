package game2.visuals;

import game2.entities.Entity;
import game2.tiles.Tile;
import game2.tiles.TileMap;
import game2.visuals.texture.Texture;

import java.awt.*;

public class GameVisuals {
    private final Layer bottomLayer, topLayer;

    private int cameraX, cameraY;

    private final int width, height;

    private final TileMap tileMap;

    private static final double MIN_ZOOM = 0.5, MAX_ZOOM = 2.0, DEFAULT_ZOOM = 1.0, ZOOM_FACTOR = 1.1;
    private static final int DEFAULT_CAMERA_X = 2000, DEFAULT_CAMERA_Y = 2000;

    private double zoomLevel, prevZoomLevel;

    public GameVisuals(int width, int height, TileMap tileMap) {
        this.tileMap = tileMap;
        this.width = width;
        this.height = height;
        this.cameraX = DEFAULT_CAMERA_X;
        this.cameraY = DEFAULT_CAMERA_Y;
        this.zoomLevel = DEFAULT_ZOOM;
        this.prevZoomLevel = DEFAULT_ZOOM;
        this.bottomLayer = new Layer();
        this.topLayer = new Layer();
    }

    public void addBackgroundTexture(Texture texture) {
        bottomLayer.addTexture(texture);
    }

    public void addOverlayTexture(Texture texture) {
        topLayer.addTexture(texture);
    }


    public void draw(Graphics2D g) {
        g.translate(-cameraX, -cameraY);
        g.scale(zoomLevel, zoomLevel);
        int tileSize = tileMap.getTileSize();
        bottomLayer.draw(g, cameraX, cameraY, width * zoomLevel, height * zoomLevel);
        int firstX = getFirstTile(cameraX, tileSize, zoomLevel), firstY = getFirstTile(cameraY, tileSize, zoomLevel);
        int lastX = getLastTile(firstX, tileSize, zoomLevel, width, tileMap.getWidth()), lastY = getLastTile(firstY, tileSize, zoomLevel, height, tileMap.getHeight());
        for (int x = firstX; x <= lastX; x++) {
            for (int y = firstY; y <= lastY; y++) {
                Tile tile = tileMap.getTile(x, y);
                tile.getTexture().draw(g);
                Entity e = tile.getEntity();
                if (e != null) e.getTexture().draw(g);
            }
        }
        topLayer.draw(g, cameraX, cameraY, width * zoomLevel, height * zoomLevel);
    }

    private static int getFirstTile(int camera, int tileSize, double zoomLevel) {
        return (int) Math.max(0, (camera / (tileSize * zoomLevel)) - 1);
    }

    private static int getLastTile(int firstTile, int tileSize, double zoomLevel, int screenDimension, int tmDimension) {
        return (int) Math.min(tmDimension - 1, ((screenDimension / (double) tileSize)) / zoomLevel + firstTile + 2);
    }

    public void panCamera(int x, int y) {
        cameraX += x;
        cameraY += y;
        fixCamera();
    }

    /**
     * Zooms in or out on the game, showing more or less of the tileMap. Only the sign of <code>direction</code> matters.
     * @param direction The rotation of the mouseWheel.
     * @param zoomTarget The <code>Point</code> to zoom towards.
     */
    public void zoom(int direction, Point zoomTarget) {
        if (direction < 0){
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

    public void fixCamera(){
        int tileSize = tileMap.getTileSize();
        cameraX = (int) Math.max(0, Math.min(cameraX, zoomLevel * tileSize * tileMap.getWidth() - width - 1));
        cameraY = (int) Math.max(0, Math.min(cameraY, zoomLevel * tileSize * tileMap.getHeight() - height - 1));
    }
}
