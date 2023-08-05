package map;

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
}
