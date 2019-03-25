package Game;

public class World {
    private static int currentLevel;


    public static int getCurrentLevel() {
        return currentLevel;
    }

    static void setCurrentLevel(int currentLevel) {
        World.currentLevel = currentLevel;
    }
}
