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

    public void draw(Graphics2D g){
        for (Texture t: textures){
            t.draw(g);
        }
    }

    public void clear(){
        textures.clear();
    }

    @Override
    public String toString() {
        return "Layer: " + textures.size();
    }
}
