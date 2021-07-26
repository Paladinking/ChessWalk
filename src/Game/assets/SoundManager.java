package Game.assets;

import Game.Board;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class SoundManager {
    public static final int MOVE = 0, ATTACK = 1, HURT = 2,DIE = 3;


    public static void playSound(String path){
        if(path.equals("")) return;
        playSfx(ClassLoader.getSystemResourceAsStream("sound/" + path));
    }
    public static void playSound(String[] strings) {
        playSound(strings[(int)(Math.random()*strings.length)]);
    }

    //LunaticEdit
    private static void playSfx(final InputStream fileStream) {
        ActivityManager.getInstance().submit(() -> {
            try {
                BufferedInputStream bufferedStream = new BufferedInputStream(fileStream);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);

                final int BUFFER_SIZE = 128000;
                SourceDataLine sourceLine = null;

                AudioFormat audioFormat = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

                sourceLine = (SourceDataLine) AudioSystem.getLine(info);
                sourceLine.open(audioFormat);

                //noinspection ConstantConditions
                if (sourceLine == null) {
                    return;
                }


                int nBytesRead = 0;
                byte[] abData = new byte[BUFFER_SIZE];
                sourceLine.start();
                while (nBytesRead != -1) {
                    try {
                        nBytesRead = bufferedStream.read(abData, 0, abData.length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (nBytesRead >= 0) {
                        sourceLine.write(abData, 0, nBytesRead);
                    }
                }

                sourceLine.drain();
                sourceLine.close();
                bufferedStream.close();
                audioInputStream.close();

            } catch (IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
    }


}
