package Game.entities;

public abstract class Entity {
    int x, y;
    int imgId;

    public abstract void tick();

    public abstract void selected();
}

