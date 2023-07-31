import map.GameMap;

public class Main {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap(3, 3, 10);
        gameMap.printStatistics();

        for (int i = 0; i < 40; i++) {
            gameMap.doMoves();
            gameMap.printStatistics();
        }

    }
}
