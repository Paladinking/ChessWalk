package game2.games;

import game2.entities.Entities;
import game2.entities.Player;
import game2.entities.enemies.Enemy;
import game2.levels.Level;
import game2.tiles.TileMap;
import game2.visuals.GameVisuals;
import game2.visuals.Images;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Walk extends Game {

    public static final int TILE_SIZE = 50;
    private static final int TILEMAP_WIDTH = 100, TILEMAP_HEIGHT = 100;

    private final Entities entities;
    private Level currentLevel;
    private final GameVisuals visuals;
    private final Images images;
    private final TileMap tileMap;

    private final Point draggingSource = new Point(0, 0);

    public Walk(int width, int height) {
        super();
        this.entities = new Entities();
        this.visuals = new GameVisuals(width, height);
        this.images = new Images();
        this.tileMap = new TileMap(TILEMAP_WIDTH, TILEMAP_HEIGHT, TILE_SIZE);
    }

    @Override
    public void draw(Graphics2D g) {
        visuals.draw(g, tileMap);
    }

    @Override
    public void init() {
        try {
            images.load();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        entities.clear();
        currentLevel = new Level(images.level1, new BufferedImage[]{images.tile1, images.wall1}, Level.LVL1_ENEMIES);
        tileMap.load(currentLevel);
        Player player = new Player(10, 10);
        player.createTexture(images, tileMap.getTileSize());
        tileMap.place(player);
        generateEnemy();
    }

    public void generateEnemy(){
        int tileSize = tileMap.getTileSize();
        Enemy e = entities.generateEnemy(currentLevel, 22, 22);
        e.createTexture(images, tileSize);
        tileMap.place(e);
    }

    @Override
    public void tick() {
        entities.tickAll();
        for (GameListener l : listeners) l.tickHappened();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        visuals.panCamera(draggingSource.x - e.getX(), draggingSource.y - e.getY(), tileMap);
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e){
        draggingSource.setLocation(e.getX(), e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        visuals.zoom(e.getWheelRotation(), e.getPoint(), tileMap);
    }
}
