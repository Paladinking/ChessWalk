package game2.visuals;

import game2.entities.EntityTemplate;
import game2.enums.ImageType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Images {

    private final Map<Object, Map<ImageType, BufferedImage[]>> templateStateImages;

    public Images() {
        this.templateStateImages = new HashMap<>();
    }

    public void createMap(Object key){
        templateStateImages.put(key, new HashMap<>());
    }

    public void loadImage(Object key, String path, String name, ImageType state) throws IOException {
        BufferedImage[] image = new BufferedImage[1];
        image[0] = ImageIO.read(ClassLoader.getSystemResource(path + name));
        templateStateImages.get(key).put(state, image);
    }

    public void loadImage(EntityTemplate template, String path, String name, ImageType state, int count, int width, int height) throws IOException {
        BufferedImage[] images = new BufferedImage[count];
        BufferedImage b = ImageIO.read(ClassLoader.getSystemResource(path + name));
        for (int i = 0; i < count; i++){
            images[i] = b.getSubimage(0, i * height, width, height);
        }
        templateStateImages.get(template).put(state, images);
    }

    public BufferedImage[] getImages(Object template, ImageType state){
        return templateStateImages.get(template).get(state);
    }

    public BufferedImage getImage(Object template, ImageType state){
        return templateStateImages.get(template).get(state)[0];
    }


    public BufferedImage getImage(String name) throws IOException {
        return ImageIO.read(ClassLoader.getSystemResource(name));
    }
}
