package game.map;

import game.model.Animal;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

public class MoveDirection {
    private int toTop = 0;
    private int toRight = 0;

    private int toLeft = 0;

    private int toBottom = 0;

    public void increaseToTop() {
        toTop++;
    }

    public void increaseToBottom() {
        toBottom++;
    }

    public void increaseToRight() {
        toRight++;
    }

    public void increaseToLeft() {
        toLeft++;
    }

    public int getToTop() {
        return toTop;
    }

    public int getToRight() {
        return toRight;
    }

    public int getToLeft() {
        return toLeft;
    }

    public int getToBottom() {
        return toBottom;
    }

    @Override
    public String toString() {
        return "MoveDirection{" +
                "toTop=" + toTop +
                ", toRight=" + toRight +
                ", toLeft=" + toLeft +
                ", toBottom=" + toBottom +
                '}';
    }

    public static MoveDirection getMoveDirection(int leftToTop, int leftToBottom, int leftToRight, int leftToLeft, Animal animal) {
        MoveDirection moveDirection;
        do {
            moveDirection = new MoveDirection();
            for (int i = 0; i < animal.getFieldsPerMove(); i++) {
                int random = Math.abs(ThreadLocalRandom.current().nextInt(100));
                if (random <= 25 && moveDirection.getToTop() < leftToTop) {
                    moveDirection.increaseToTop();
                } else if (random <= 50 && moveDirection.getToBottom() < leftToBottom) {
                    moveDirection.increaseToBottom();
                } else if (random <= 75 && moveDirection.getToRight() < leftToRight) {
                    moveDirection.increaseToRight();
                } else if (moveDirection.getToLeft() < leftToLeft) {
                    moveDirection.increaseToLeft();
                }
            }
        } while (abs(moveDirection.getToLeft()) - abs(moveDirection.getToRight()) == 0 &&
                abs(moveDirection.getToTop()) - abs(moveDirection.getToBottom()) == 0);
        return moveDirection;
    }

}
