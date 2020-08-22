package Game.entities;



import Game.GameState;
import Game.assetClasses.Image;
import Game.assetClasses.SoundManager;
import java.awt.image.BufferedImage;

public class Slime extends Enemy {
    public Slime(int x,int y){
        super(new Image(x,y,Image.SLIME));
        this.x = x/GameState.tileSize;
        this.y = y/GameState.tileSize;
        this.hp = 10 + 2 * GameState.currentLevel;
        this.mode = SLEEPING;
    }

    @Override
    protected void move() {
        super.move();
    }

    @Override
    public void tick() {
        super.tick();
    }
    @Override
    public void damage(int dmg){
        super.damage(dmg);

    }

    @Override
    public BufferedImage[][] getAttackImages() {
       return Image.PLAYER_ATTACK;
    }



    @Override
    public java.awt.Image getBlood(){
        return Image.SLIME_BLOOD;
    }

    @Override
    public void playSound(int w){
        switch (w) {
            case (SoundManager.MOVE):
                SoundManager.playSound("Slime.wav");
                return;
            case SoundManager.ATTACK:
                SoundManager.playSound("slimeAtk.wav");
                return;
            case SoundManager.HURT:
                SoundManager.playSound("slimeDmg.wav");
                return;
            case SoundManager.DIE:
                SoundManager.playSound(new String[]{""});
                return;
        }
    }
}
