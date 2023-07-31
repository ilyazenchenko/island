package map;

public class MoveDirection {
    private int inHeight;
    private int inWidth;

    public void increaseHeight() {
        inHeight++;
    }

    public void decreaseHeight() {
        inHeight--;
    }

    public void increaseWidth() {
        inWidth++;
    }

    public void decreaseWidth() {
        inWidth--;
    }

    public int getInHeight() {
        return inHeight;
    }

    public int getInWidth() {
        return inWidth;
    }

}
