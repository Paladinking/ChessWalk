package Game.entities;


import Game.assetClasses.SoundManager;
import Game.entities.actions.Action;
import Game.entities.actions.Attack;
import Game.entities.actions.Stand;
import Game.assetClasses.Image;

import java.awt.image.BufferedImage;

public abstract class MovingEntity extends Entity {


    @Override
    public void tick() {

    }

    @Override
    public void selected() {

    }

    @Override
    public void damage(int dmg) {
        playSound(SoundManager.HURT);
        this.hp -= dmg;
    }

    @Override
    public Action assignAction() {
        return new Stand(this);
    }

    public int getHp() {
        return hp;
    }

    @Override
    public abstract BufferedImage[][] getAttackImages();

    @Override
    public void attackAnimation(int ticks, int dx, int dy) {
        Image.getImage(getId()).setImg(getAttackImages()[Attack.getDirection(dx, dy, this)][(ticks - 1) / 4]);
    }

    @Override
    public void afterAttack(int dx, int dy) {
        Image.getImage(getId()).setImg(Image.PLAYER_STILL[Attack.getDirection(dx, dy, this)]);
    }

    @Override
    public java.awt.Image getBlood() {
        return Image.BLOOD;
    }

    @Override
    public void playSound(int w) {

    }
}
