package Game.assetClasses;

import Game.Board;

import javax.sound.sampled.*;
import java.io.IOException;

public abstract class SoundManager {
    public static final int MOVE=0,ATTACK=1,HURT=2,DIE=3;



    public static final Clip[] SLIME = new Clip[4];

    static {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(Board.class.getResource("assets/sound/test.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            SLIME[MOVE] = clip;
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

}
