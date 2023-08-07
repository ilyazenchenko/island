import game.Game;
import game.model.Plant;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(2, 2, 5);
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            game.run(10);
            sc.nextLine();
        }
    }
}
