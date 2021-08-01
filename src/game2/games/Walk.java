package game2.games;

import game2.actions.Move;
import game2.entities.EntitiesListener;
import game2.entities.enemies.Enemy;
import game2.essentials.AStar;
import game2.essentials.Entities;
import game2.entities.Entity;
import game2.entities.Player;
import game2.levels.Level;
import game2.essentials.TileMap;
import game2.essentials.GameVisuals;
import game2.tiles.Tile;
import game2.visuals.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class Walk extends Game implements EntitiesListener {

    public static final int TILE_SIZE = 50;
    private static final int TILEMAP_WIDTH = 100, TILEMAP_HEIGHT = 100;

    private final Entities entities;
    private Level currentLevel;
    private final GameVisuals visuals;
    private final Images images;
    private final TileMap tileMap;

    private Point mousePress = null;
    private final Point draggingSource = new Point(0, 0);

    public Walk(int width, int height) {
        super();
        this.entities = new Entities(this);
        this.tileMap = new TileMap(TILEMAP_WIDTH, TILEMAP_HEIGHT, TILE_SIZE);
        this.visuals = new GameVisuals(width, height, tileMap);
        this.images = new Images();
    }

    @Override
    public void draw(Graphics2D g) {
        visuals.draw(g);
    }

    @Override
    public void init() {
        try {
            images.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        entities.clear();
        currentLevel = new Level(images.level1, new BufferedImage[]{images.tile1, images.wall1}, Level.LVL1_ENEMIES);
        tileMap.load(currentLevel);
        Player player = new Player(49, 49);
        player.createTexture(images, tileMap.getTileSize());
        entities.setPlayer(player);
        tileMap.place(player);
        generateEnemy();
    }

    public void createEntity(Entity e) {
        entities.addEntity(e);
        e.createTexture(images, tileMap.getTileSize());
        tileMap.place(e);
    }

    public void generateEnemy() {
        createEntity(entities.generateEnemy(currentLevel, 45, 44));
    }

    @Override
    public void entityMoved(Point oldPos, Point newPos) {
        tileMap.moveEntity(oldPos, newPos);
    }

    @Override
    public void tick() {
        handleMousePress();
        entities.tickAll(tileMap);
        for (GameListener l : listeners) l.tickHappened();
    }

    private void handleMousePress() {
        if (mousePress == null) return;
        Player player = entities.getPlayer();
        player.clearActions();
        Point playerPos = player.getPos();
        if (mousePress.equals(playerPos)){
            return;
        }
        Tile tile = tileMap.getTile(mousePress.x, mousePress.y);
        if(Math.abs(playerPos.x - mousePress.x) <= 1 && Math.abs(playerPos.y - mousePress.y)<= 1){
            if (tile.isOpen()){
                Move move = new Move(player, 2, tileMap.getTileSize(), new Point(mousePress.x - playerPos.x, mousePress.y - playerPos.y));
                player.queAction(move, tileMap);
                return;
            } else if (tile.getEntity() != null){
                
            }
        }
        List<Point> path = AStar.getPath(tileMap, player.getPos(), mousePress);
        if (path != null) {
            for (int i = path.size() - 1; i >= 0; i--) {
                Move move = new Move(player, 2, tileMap.getTileSize(), path.get(i));
                entities.quePlayerAction(move, tileMap);
            }
        }
        mousePress = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        visuals.panCamera(draggingSource.x - e.getX(), draggingSource.y - e.getY());
        mouseMoved(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePress = visuals.getTile(e.getX(), e.getY());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        draggingSource.setLocation(e.getX(), e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        visuals.zoom(e.getWheelRotation(), e.getPoint());
    }

}
