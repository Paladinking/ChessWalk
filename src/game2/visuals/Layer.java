package game2.visuals;

import game2.visuals.texture.AbstractTexture;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Layer {
    protected final List<AbstractTexture> textures;

    public Layer() {
        textures = new ArrayList<>();
    }

    public void addTexture(AbstractTexture texture){
        textures.add(texture);
    }

    public void removeTexture(AbstractTexture texture){
        textures.remove(texture);
    }

    public void draw(Graphics2D g){
        for (AbstractTexture t: textures){
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
