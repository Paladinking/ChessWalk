package Game.assets;

public abstract class ImageID {
    public static final int BOARD_ID = 0,PLAYER_ID = 1;
    public static final int SELECTED_SQUARE_ID = 3;
    public static int getId(){
        int i = 10;
        while (Image.imageQue.containsKey(i)&&i<1000){
            i++;
        }
        return i;
    }

}
