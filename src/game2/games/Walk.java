package game2.games;

import game2.actions.Attack;
import game2.actions.Move;
import game2.data.DataLoader;
import game2.entities.EntitiesListener;
import game2.enums.TextureState;
import game2.essentials.AStar;
import game2.essentials.Entities;
import game2.entities.Entity;
import game2.entities.Player;
import game2.essentials.TileMap;
import game2.levels.Level;
import game2.visuals.GameVisuals;
import game2.tiles.Tile;
import game2.visuals.ImageData;
import game2.visuals.texture.ImageTexture;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.List;

public class Walk extends Game implements EntitiesListener {

    private final Entities entities;
    private Level currentLevel;
    private final GameVisuals visuals;
    private final DataLoader dataLoader;

    private Point mousePress = null;
    private boolean dragging;
    private final Point draggingSource = new Point(0, 0);
    private int tileSize;

    public Walk(int width, int height) {
        super();
        this.entities = new Entities(this);
        this.visuals = new GameVisuals(width, height);
        this.dataLoader = new DataLoader();
    }

    @Override
    public void draw(Graphics2D g) {
        visuals.draw(g);
    }

    @Override
    public void init() {
        try {
            dataLoader.readAllData();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        entities.clear();
        currentLevel = dataLoader.getLevel();
        entities.setPlayer(dataLoader.getPlayer(), currentLevel);
        tileSize = dataLoader.getTileSize();
        currentLevel.placePlayer(entities.getPlayer());
        visuals.init(currentLevel, tileSize);

        generateEnemy();
    }

    public void generateEnemy() {
        String enemyName = currentLevel.getEnemy();
        if (enemyName == null) return;
        Point pos = currentLevel.getPlayerPos();
        Entity e = dataLoader.getEntityTemplate("Slime").create(pos.x + 2, pos.y, tileSize);
        entities.addEntity(e);
        currentLevel.place(e);
    }

    @Override
    public void entityMoved(Point oldPos, Point newPos) {
        currentLevel.moveEntity(oldPos, newPos);
    }

    @Override
    public void createTexture(ImageData data, Point pos) {
        visuals.addTexture(new ImageTexture(data.getImage(), 2, pos.x * tileSize, pos.y * tileSize, data.width, data.height, tileSize), 20);
    }

    @Override
    public void entityDied(Point pos) {
        currentLevel.getTile(pos).setEntity(null);
    }

    @Override
    public void tick() {
        handleMousePress();
        visuals.tick();
        entities.tickAll(currentLevel);
        for (GameListener l : listeners) l.tickHappened();
    }

    private void handleMousePress() {
        if (mousePress == null) return;
        Point location = new Point(mousePress.x, mousePress.y);
        mousePress = null;
        Player player = entities.getPlayer();
        player.clearActions();
        Point playerPos = player.getPos();
        if (location.equals(playerPos)) {
            return;
        }
        Tile tile = currentLevel.getTile(location.x, location.y);
        boolean open = tile.isOpen();
        if (TileMap.neighbors(playerPos, location)) {
            if (open) {
                Move move = new Move(player, 2, new Point(location.x - playerPos.x, location.y - playerPos.y));
                player.queAction(move, currentLevel);
                return;
            } else if (tile.getEntity() != null) {
                Attack attack = new Attack(player, location, TextureState.ATTACK, 4, 20);
                player.queAction(attack, currentLevel);
                player.queAttack(location);
            }
        }
        if (!open) return;
        List<Point> path = AStar.getPath(currentLevel.getTilesMap(), player.getPos(), location);
        if (path != null) {
            for (int i = path.size() - 1; i >= 0; i--) {
                Move move = new Move(player, 2, path.get(i));
                entities.quePlayerAction(move, currentLevel);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragging = true;
        visuals.panCamera(draggingSource.x - e.getX(), draggingSource.y - e.getY());
        mouseMoved(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && !dragging) {
            mousePress = visuals.getTile(e.getX(), e.getY());
        }
        dragging = false;
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
