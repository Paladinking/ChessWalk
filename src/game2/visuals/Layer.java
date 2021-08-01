package game2.visuals;

import game2.visuals.texture.Texture;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Layer {
    protected final List<Texture> textures;

    public Layer() {
        textures = new ArrayList<>();
    }

    public void addTexture(Texture texture){
        textures.add(texture);
    }

    public void removeTexture(Texture texture){
        textures.remove(texture);
    }

    public void draw(Graphics2D g, int cameraX, int cameraY, double v, double v1){
        for (Texture t: textures){
            t.draw(g);
        }
    }

    public void clear(){
        textures.clear();
    }
}
