package Game.levels;

import java.io.InputStream;
import java.util.Properties;

public class LevelReader {



    public void init(String Level) throws Exception{

        InputStream in = getClass().getResourceAsStream(".idea/Game.imageclasses/Levels/Level1");
        Properties properties = new Properties();
        properties.load(in);
        String walls = properties.getProperty("Walls");
        String[] wallCoords = walls.split(";");

    }



}
