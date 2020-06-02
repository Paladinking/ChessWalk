package Game.levels.Tiles;

import Game.entities.Entity;

public abstract class Tile {
    Entity entity;

    public abstract void pressed(Entity e);

    public abstract boolean selected(Entity e);

    public abstract void stopped();

    public abstract void setEntity(Entity e);

    public abstract void removeEntity();

    public abstract Entity getEntity();

    public abstract void Attack(int dmg);

}
