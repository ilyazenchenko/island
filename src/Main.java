import map.GameMap;

public class Main {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap(3, 3, 10);

        for (int i = 0; i < 10; i++) {
            gameMap.doMoves();
            gameMap.printStatistics();
        }

    }
}
