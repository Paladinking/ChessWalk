package Game.assets;

public class ImageID {
    public static final int BOARD_ID = 0,PLAYER_ID = 1;
    public static final int SELECTED_SQUARE_ID = 3;
    //EnemyIDs 10-29 WallId 255-509
    public static int getWallId(){
        int i = 255;
        while (Image.imageQue.containsKey(i)&&i<510){
            i++;
        }
        return i;

    }
    public static int getEnemyId(){
        int i = 10;
        while (Image.imageQue.containsKey(i)&&i<30){
            i++;
        }
        return i;

    }

}
