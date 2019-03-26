package Game.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Image {
    private static final String path = ".idea/assets/images/";
    private static final String playerPath = path+"Player/";

    public static BufferedImage BOARD;
    public static  BufferedImage SELECTED_TILE;

    public static BufferedImage PLAYER_FRONT;
    public static BufferedImage PLAYER_BACK;
    public static BufferedImage PLAYER_LEFT;
    public static BufferedImage PLAYER_RIGHT;

    public static BufferedImage SLIME;
    public static BufferedImage KNIGHT;

    public static BufferedImage WALL_FRONT;

    public static BufferedImage[] PLAYER_STILL;
    public static BufferedImage[][] PLAYER_ATTACK;

    static HashMap<Integer,Image> imageQue;

    public static final int UP = 0, DOWN = 1,LEFT = 2,RIGHT = 3, STILL = 4;

    private BufferedImage img;
    private int xCoor, yCoor;
    private int adjX;

    public void setAdjX(int adjX) {
        this.adjX = adjX;
    }

    public void setAdjY(int adjY) {
        this.adjY = adjY;
    }

    private int adjY;

    public Image(int xCoor, int yCoor,BufferedImage i){
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.img = i;
    }

    public static void loadImages(){
        imageQue = new HashMap<>();
        try {
            BOARD = ImageIO.read(new File(path+"Board1.png"));
            PLAYER_FRONT = ImageIO.read(new File(playerPath+"PlayerFront.png"));
            PLAYER_BACK = ImageIO.read(new File(playerPath+"PlayerBack.png"));
            PLAYER_LEFT = ImageIO.read(new File(playerPath+"PlayerLeft.png"));
            PLAYER_RIGHT = ImageIO.read(new File(playerPath+"PlayerRight.png"));
            SELECTED_TILE = ImageIO.read(new File(path+"SelectedTile.png"));
            WALL_FRONT = ImageIO.read(new File(path+"Walls/WallFront.png"));
            SLIME = ImageIO.read(new File(path+"Enemies/Slime.png"));
            KNIGHT = ImageIO.read(new File(path+"Enemies/Knight.png"));

            PLAYER_ATTACK = new BufferedImage[4][5];
            for (int i=0;i<5;i++){
                PLAYER_ATTACK[RIGHT][i] = ImageIO.read(new File(playerPath+"AttackAnimationRight.png")).getSubimage(0,70*i,80,70);
                PLAYER_ATTACK[LEFT][i] = ImageIO.read(new File(playerPath+"AttackAnimationLeft.png")).getSubimage(0,70*i,80,70);
            }
            PLAYER_STILL = new BufferedImage[]{PLAYER_BACK,PLAYER_FRONT,PLAYER_LEFT,PLAYER_RIGHT};
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void put(int id,Image i){
        imageQue.put(id,i);
    }
    public static Image getImage(int id){
        return imageQue.get(id);
    }
    public static void remove(int id){
        imageQue.remove(id);

    }
    public static void drawImages(Graphics g){
        ArrayList<ImagePosition> imageOrder = new ArrayList<>();
        for (int i:imageQue.keySet()) {
            imageOrder.add(new ImagePosition(imageQue.get(i).yCoor,i));
        }
        imageOrder.sort(new Compare());
        for (ImagePosition i:imageOrder) {
            Image img = imageQue.get(i.id);
            g.drawImage(img.img,img.xCoor+img.adjX,img.yCoor+img.adjY,null);
        }

    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public void moveImage(int x,int y){
        xCoor += x;
        yCoor += y;
    }
    static class Compare implements Comparator<ImagePosition> {
        @Override
        public int compare(ImagePosition a, ImagePosition b) {
            return Integer.compare(a.p, b.p);
        }
    }
    static class ImagePosition {
        int p,id;
        ImagePosition(int p,int id){
            this.p = p;
            this.id = id;
        }
    }

}
