package Game.entities;

import Game.GameState;
import Game.assetClasses.SoundManager;
import Game.entities.actions.Action;
import Game.entities.actions.Attack;
import Game.assetClasses.Image;
import Game.assetClasses.ImageID;
import Game.entities.actions.Movement;
import Game.entities.actions.Stand;
import Game.levels.Tilemap;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends MovingEntity {
    public static final int SLIME = 0;
    public static final int KNIGHT = 1,SKELETON=2;
    private static int tickLength;
    static final int dmg = 3;

    private static List<Enemy> enemies;


    Enemy(Image i){
        image =i;
        currentAction = null;
        this.state = SLEEPING;
    }
    public Enemy() {
    }

    @Override
    public void attackAnimation(int tick, int dx,int dy) {
        int ft = Attack.tickLength;
        if(tick<=ft/2) image.adjImage(dx* GameState.initialSize/(ft/2f),dy* GameState.initialSize/(ft/2f));
        else image.adjImage(-dx* GameState.initialSize/(ft/2f),-dy* GameState.initialSize/(ft/2f));
    }
    @Override
    public void afterAttack(int dx, int dy){
        image.setAdjX(0);
        image.setAdjY(0);
    }

    @Override
     public void tick() {
        if(this.state!=SLEEPING) move();
     }


    @Override
    public void selected() {

    }
    @Override
    public void playSound(int w) {
        switch (w) {
            case (SoundManager.MOVE):
                SoundManager.playSound("step3.wav");
                break;
            case (SoundManager.ATTACK):
                SoundManager.playSound("punsh.wav");
                break;
            case(SoundManager.HURT):

                break;
            case (SoundManager.DIE):

                break;
        }
    }
    @Override
    public void damage(int dmg){
        super.damage(dmg);
        if(hp<0) die();
    }
    private void die(){
        Enemy.enemies.remove(this);
        Tilemap.getTile(x,y).removeEntity();
    }
    public Action assignAction(){
        if(state ==SLEEPING) {
            if (Math.abs(this.x - GameState.playerX) < 8 || Math.abs(this.y - GameState.offsetY) < 8) state = WANDERING;
            else return new Stand(this);
        }
        List<Movement> m = new ArrayList<>();
        for(int i=x-1;i<=x+1;i++){
            for(int j=y-1;j<=y+1;j++){
                   if(Tilemap.getTile(i,j).getEntity()instanceof Player) return new Attack(i-x,j-y,dmg,this);
            }
        }
        if (Tilemap.getTile(x + 1, y).selected(this)) m.add(new Movement(1, 0));
        if (Tilemap.getTile(x - 1, y).selected(this)) m.add(new Movement(-1, 0));
        if (Tilemap.getTile(x, y + 1).selected(this)) m.add(new Movement(0, 1));
        if (Tilemap.getTile(x, y - 1).selected(this)) m.add(new Movement(0, -1));
        if(m.size()>0) {
            return new Movement(m.get((int) (Math.random() * m.size())), this);
        }
        return  new Stand(this);

    }
    protected void move() {
        if(currentAction==null) return;
        currentAction.preformAction();

     }
     public static void load(){
         enemies = new ArrayList<>();
     }
     public static boolean tickAll(){
         if(enemies.size() ==0){
             return false;
         }

         int t1 = GameState.t==0 ? 0:getTickLength();
         for (Enemy e:enemies) {
             if(GameState.t==0) {
                 e.currentAction = e.assignAction();
                t1 = e.currentAction.tickLength()>t1? e.currentAction.tickLength():t1;
             }
             e.tick();
         }
         setTickLength(t1);
         return true;
     }
     private static void setTickLength(int i){
        tickLength = i;
     }
     public static int getTickLength(){
        return tickLength;
     }
     public static void add(Enemy e){
         enemies.add(e);
     }


}
