package game2.sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sound {

    private final static ExecutorService executorService = Executors.newCachedThreadPool();

    private final byte[] bytes;

    public Sound(byte[] soundData){
        this.bytes = soundData;
    }

    public void play() {
        if (bytes.length == 0) return;
        playSfx(new ByteArrayInputStream(bytes));
    }

    private void playSfx(InputStream soundData) {
        executorService.submit(()->{
            try {
                BufferedInputStream bufferedStream = new BufferedInputStream(soundData);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
                final int BUFFER_SIZE = 128000;
                SourceDataLine sourceLine;

                AudioFormat audioFormat = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

                sourceLine = (SourceDataLine) AudioSystem.getLine(info);
                sourceLine.open(audioFormat);




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
                System.out.println("BAD...");
            }

        });
    }

}
