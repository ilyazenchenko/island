import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(3, 3, 20);
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            game.run(100);
            sc.nextLine();
        }
    }
}
