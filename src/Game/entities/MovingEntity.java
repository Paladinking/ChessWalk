package Game.entities;


import Game.imageclasses.Image;

import java.awt.image.BufferedImage;

public class MovingEntity extends Entity {

    private static  final BufferedImage[][] attackImages = Image.PLAYER_ATTACK;
    @Override
    public void tick() {

    }

    @Override
    public void selected() {

    }

    @Override
    public void damage(int dmg) {

    }
    @Override
    public BufferedImage[][] getAttackImages(){
        return attackImages;
    }
}
