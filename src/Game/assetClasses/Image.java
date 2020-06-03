package Game.assetClasses;

import Game.Board;
import Game.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Image {
    private static final String path = "assets/images/";
    private static final String playerPath = path+"Player/";

    public static BufferedImage BOARD;
    public static BufferedImage SELECTED_TILE;
    public static BufferedImage BLOOD;
    public static BufferedImage SLIME_BLOOD;

    public static BufferedImage PLAYER_FRONT;
    public static BufferedImage PLAYER_BACK;
    public static BufferedImage PLAYER_LEFT;
    public static BufferedImage PLAYER_RIGHT;

    public static BufferedImage SLIME;
    public static BufferedImage KNIGHT;
    public static BufferedImage[] SKELETON;

    public static BufferedImage WALL_FRONT;

    public static BufferedImage BOOMERANG;
    public static BufferedImage SWORD;

    public static BufferedImage[] PLAYER_STILL;
    public static BufferedImage[][] PLAYER_ATTACK;

    static HashMap<Integer,Image> imageQue;

    public static final int UP = 0, DOWN = 1,LEFT = 2,RIGHT = 3, STILL = 4;
    java.awt.Image img;
    float xCoor, yCoor;
    int adjX,adjY;

    public void setAdjX(int adjX) {
        this.adjX = adjX;
    }

    public void setAdjY(int adjY) {
        this.adjY = adjY;
    }

    boolean hidden;

    public Image(int xCoor, int yCoor,java.awt.Image i){
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.img = i;

    }

    public static void loadImages(){

        imageQue = new HashMap<>();
        try {
            BOARD = ImageIO.read((Board.class.getResource(path+"Board1.png")));
            PLAYER_FRONT = ImageIO.read((Board.class.getResource(playerPath+"PlayerFront.png")));
            PLAYER_BACK = ImageIO.read((Board.class.getResource(playerPath+"PlayerBack.png")));
            PLAYER_LEFT = ImageIO.read((Board.class.getResource(playerPath+"PlayerLeft.png")));
            PLAYER_RIGHT = ImageIO.read((Board.class.getResource(playerPath+"PlayerRight.png")));
            SELECTED_TILE = ImageIO.read(Board.class.getResource(path+"SelectedTile.png"));
            WALL_FRONT = ImageIO.read((Board.class.getResource(path+"Walls/WallFront.png")));
            SLIME = ImageIO.read((Board.class.getResource(path+"Enemies/Slime.png")));
            KNIGHT = ImageIO.read((Board.class.getResource(path+"Enemies/Knight.png")));
            BOOMERANG = ImageIO.read(Board.class.getResource(path+"Items/Boomerang.png"));
            SWORD = ImageIO.read(Board.class.getResource(path+"Items/Sword.png"));
            BLOOD = ImageIO.read(Board.class.getResource(path+"Blood.png"));
            SLIME_BLOOD = ImageIO.read(Board.class.getResource(path+"slimeBlood.png"));
            SKELETON = new BufferedImage[4];
            SKELETON[UP] = ImageIO.read(Board.class.getResource(path+"Enemies/skeletonBack.png"));
            SKELETON[DOWN] = ImageIO.read(Board.class.getResource(path+"Enemies/skeletonFront.png"));
            SKELETON[LEFT] = ImageIO.read(Board.class.getResource(path+"Enemies/skeletonLeft.png"));
            SKELETON[RIGHT] = ImageIO.read(Board.class.getResource(path+"Enemies/skeletonRight.png"));

            PLAYER_ATTACK = new BufferedImage[4][5];
            for (int i=0;i<5;i++){
                PLAYER_ATTACK[UP][i] = ImageIO.read(Board.class.getResource(playerPath+"AttackAnimationUp.png")).getSubimage(0,70*i,50,70);
                PLAYER_ATTACK[RIGHT][i] = ImageIO.read((Board.class.getResource(playerPath+"AttackAnimationRight.png"))).getSubimage(0,70*i,80,70);
                PLAYER_ATTACK[LEFT][i] = ImageIO.read(Board.class.getResource(playerPath +"AttackAnimationLeft.png")).getSubimage(0,70*i,80,70);
                PLAYER_ATTACK[DOWN][i] = ImageIO.read(Board.class.getResource(playerPath+"AttackAnimationFront.png")).getSubimage(0,70*i,50,70);
            }
            PLAYER_STILL = new BufferedImage[]{PLAYER_BACK,PLAYER_FRONT,PLAYER_LEFT,PLAYER_RIGHT};
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void put(int id,Image i){
        imageQue.put(id,i);
    }
    public java.awt.Image getImg(){
        return this.img;
    }
    public static Image getImage(int id){
        return imageQue.get(id);
    }
    public static void remove(int id){
        imageQue.remove(id);
    }
    public void setHidden(boolean b){
        hidden=b;
    }
    public static void clear(){
        imageQue = new HashMap<>();
    }


    public static void drawImages(Graphics g){
        ArrayList<ImagePosition> imageOrder = new ArrayList<>();
        for (int i:imageQue.keySet()) {
            imageOrder.add(new ImagePosition(imageQue.get(i).yCoor,i));
        }
        imageOrder.sort(new Compare());
        for (ImagePosition i:imageOrder) {
            Image img = imageQue.get(i.id);
            img.draw(g);
        }

    }
    void draw(Graphics g){
        if(hidden) return;
        g.drawImage(img, GameState.offsetX +((int)xCoor)+adjX*GameState.tileSize/50,GameState.offsetY+((int)yCoor)+adjY*GameState.tileSize/50,img.getWidth(null)*GameState.tileSize/50,img.getHeight(null)*GameState.tileSize/50,null);
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public void moveImage(float x,float y){
        xCoor += x;
        yCoor += y;
    }

    public float getX() {
        return this.xCoor;
    }
    public float getY() {
        return this.yCoor;
    }


    static class Compare implements Comparator<ImagePosition> {
        @Override
        public int compare(ImagePosition a, ImagePosition b) {
            return Float.compare(a.p, b.p);
        }
    }
    static class ImagePosition {
        float p;
        int id;
        ImagePosition(float p,int id){
            this.p = p;
            this.id = id;
        }
    }

}
