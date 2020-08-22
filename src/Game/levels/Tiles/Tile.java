package Game.levels.Tiles;

import Game.entities.Entity;

import java.awt.*;

public abstract class Tile {
    public static final int DARK=0,HIDDEN=1,VISIBLE=2;
    public int state=DARK;

    Entity entity;

    public abstract void pressed(Entity e);

    public abstract boolean selected(Entity e);

    public abstract void stopped();

    public abstract void setEntity(Entity e);

    public abstract void removeEntity();

    public abstract Entity getEntity();

    public abstract void Attack(int dmg);

    public abstract void draw(Graphics g,int x,int y);

}
