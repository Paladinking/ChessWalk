package Game.entities;

import Game.assets.Image;
import Game.assets.ImageID;
import Game.entities.actions.Movement;
import Game.levels.Tilemap;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends MovingEntity {
    public static final int SLIME = 0;
    public static final int KNIGHT = 1;

    private static List<Enemy> enemies;

    int hp;
    Movement currentMovement;


    Enemy(Image i){
        int id = ImageID.getEnemyId();
        this.imgId = id;
        Image.put(id,i);
        currentMovement = new Movement(0,0);
    }
    public Enemy() {
    }
    private void getEnemyId(){

    }

     @Override
     public void tick() {
        move();
     }

    @Override
    public void selected() {

    }

    protected void move() {
        if(currentMovement.getDirection()==Movement.STILL) {
            List<Movement> m = new ArrayList<>();
            if (Tilemap.getTile(x + 1, y).selected(this)) m.add(new Movement(50, 0));
            if (Tilemap.getTile(x - 1, y).selected(this)) m.add(new Movement(-50, 0));
            if (Tilemap.getTile(x, y + 1).selected(this)) m.add(new Movement(0, 50));
            if (Tilemap.getTile(x, y - 1).selected(this)) m.add(new Movement(0, -50));
            currentMovement = m.get((int) (Math.random() * m.size()));
        }
        currentMovement = super.move(currentMovement);
     }
     public static void load(){
         enemies = new ArrayList<>();
     }
     public static boolean tickAll(){
         if(enemies.size() ==0){
             return false;
         }
         for (Enemy e:enemies) {
             e.tick();
         }
         return true;
     }
     public static void add(Enemy e){
         enemies.add(e);
     }
}
