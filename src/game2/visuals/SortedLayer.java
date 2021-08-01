package game2.visuals;

import game2.visuals.texture.Texture;

import java.awt.*;
import java.util.Comparator;

public class SortedLayer extends Layer {

    private final Comparator<Texture> comparator;

    public SortedLayer(Comparator<Texture> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void draw(Graphics2D g){
        textures.sort(comparator);
        super.draw(g);
    }
}
