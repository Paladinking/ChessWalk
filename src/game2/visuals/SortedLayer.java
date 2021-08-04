package game2.visuals;

import game2.visuals.texture.AbstractTexture;

import java.awt.*;
import java.util.Comparator;

public class SortedLayer extends Layer {

    private final Comparator<AbstractTexture> comparator;

    public SortedLayer(Comparator<AbstractTexture> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void draw(Graphics2D g){
        textures.sort(comparator);
        super.draw(g);
    }
}
