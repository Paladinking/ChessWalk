package game2.games;

import game2.actions.Attack;
import game2.actions.Move;
import game2.entities.EntitiesListener;
import game2.entities.EntityTemplate;
import game2.enums.TextureState;
import game2.essentials.AStar;
import game2.essentials.Entities;
import game2.entities.Entity;
import game2.entities.Player;
import game2.levels.Level;
import game2.essentials.TileMap;
import game2.essentials.GameVisuals;
import game2.tiles.Tile;
import game2.visuals.Images;
import helper.json.FancyJSonParser;
import helper.json.JsonObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Walk extends Game implements EntitiesListener {

    public static final int TILE_SIZE = 50;

    private final Entities entities;
    private Level currentLevel;
    private final GameVisuals visuals;
    private final Images images;

    private final Map<String, EntityTemplate> enemyTemplates;
    private List<Level> levels;

    private final int tileSize;
    private TileMap tileMap;

    private Point mousePress = null;
    private boolean dragging;
    private final Point draggingSource = new Point(0, 0);

    public Walk(int width, int height) {
        super();
        this.entities = new Entities(this);
        this.visuals = new GameVisuals(width, height);
        this.images = new Images();
        this.enemyTemplates = new HashMap<>();
        this.tileSize = TILE_SIZE;
    }

    @Override
    public void draw(Graphics2D g) {
        visuals.draw(g);
    }

    @Override
    public void init() {
        try {
            loadLevels();
            loadTemplates();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        entities.clear();
        currentLevel = levels.get(0);
        tileMap = currentLevel.createTiles(tileSize, images);
        visuals.setTileMap(tileMap);
        tileMap.placePlayer(entities.getPlayer());
        generateEnemy();
    }

    private void loadLevels() throws IOException{
        FancyJSonParser parser = new FancyJSonParser();
        JsonObject levels = parser.readResource("data/levels.json");
        this.levels = Level.fromJson(levels, images);
    }

    public void loadTemplates() throws IOException {
        FancyJSonParser parser = new FancyJSonParser();
        JsonObject enemies = parser.readResource("data/enemies.json");
        for (String name : enemies){
            EntityTemplate template = EntityTemplate.fromJsonObject(enemies.getObject(name), images);
            enemyTemplates.put(name, template);
        }
        EntityTemplate p = EntityTemplate.fromJsonObject(parser.readResource("data/player.json").getObject("Player"), images);
        if (p == null) throw new IOException("Could not load player.");
        Player player = (Player) p.create(-1, -1, images, tileSize);
        entities.setPlayer(player);
    }

    public void generateEnemy() {
        String enemyName = currentLevel.getEnemy();
        Entity e = enemyTemplates.get(enemyName).create(45, 44, images, tileMap.getTileSize());
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
