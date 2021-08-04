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
import game2.levels.Level;
import game2.essentials.TileMap;
import game2.essentials.GameVisuals;
import game2.tiles.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.List;

public class Walk extends Game implements EntitiesListener {

    public static final int TILE_SIZE = 50;

    private final Entities entities;
    private Level currentLevel;
    private final GameVisuals visuals;
    private final DataLoader dataLoader;

    private final int tileSize;
    private TileMap tileMap;

    private Point mousePress = null;
    private boolean dragging;
    private final Point draggingSource = new Point(0, 0);

    public Walk(int width, int height) {
        super();
        this.entities = new Entities(this);
        this.visuals = new GameVisuals(width, height);
        this.dataLoader = new DataLoader();
        this.tileSize = TILE_SIZE;
    }

    @Override
    public void draw(Graphics2D g) {
        visuals.draw(g);
    }

    @Override
    public void init() {
        try {
            dataLoader.readFirstData();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        entities.clear();
        Player player = (Player) dataLoader.getEntityTemplate("Player").create(-1, -1, tileSize);
        entities.setPlayer(player);
        currentLevel = dataLoader.getLevel();
        tileMap = currentLevel.createTiles(tileSize);
        visuals.setTileMap(tileMap);
        tileMap.placePlayer(entities.getPlayer());
        generateEnemy();
    }

    public void generateEnemy() {
        String enemyName = currentLevel.getEnemy();
        if (enemyName == null) return;
        Point pos = tileMap.getPlayerPos();
        Entity e = dataLoader.getEntityTemplate("Slime").create(pos.x + 2, pos.y, tileMap.getTileSize());
        entities.addEntity(e);
        tileMap.place(e);
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
        Point location = new Point(mousePress.x, mousePress.y);
        mousePress = null;
        Player player = entities.getPlayer();
        player.clearActions();
        Point playerPos = player.getPos();
        if (location.equals(playerPos)){
            return;
        }
        Tile tile = tileMap.getTile(location.x, location.y);
        boolean open = tile.isOpen();
        if(TileMap.neighbors(playerPos, location)){
            if (open){
                Move move = new Move(player, 2, tileMap.getTileSize(), new Point(location.x - playerPos.x, location.y - playerPos.y));
                player.queAction(move, tileMap);
                return;
            } else if (tile.getEntity() != null){
                Attack attack = new Attack(player, location, TextureState.ATTACK,4,20);
                player.queAction(attack, tileMap);
            }
        }
        if (!open) return;
        List<Point> path = new AStar(true).getPath(tileMap, player.getPos(), location);
        if (path != null) {
            for (int i = path.size() - 1; i >= 0; i--) {
                Move move = new Move(player, 2, tileMap.getTileSize(), path.get(i));
                entities.quePlayerAction(move, tileMap);
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
