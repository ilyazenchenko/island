package model;

public abstract class GameEntity {

    protected double weight;

    protected int maxInField;

    protected boolean skipsAMoveNow = true;

    public GameEntity(double weight, int maxInField) {
        this.weight = weight;
        this.maxInField = maxInField;
    }

    public abstract boolean canEat(GameEntity gameEntity);

    public double getWeight() {
        return weight;
    }

    public int getMaxPerField() {
        return maxInField;
    }

    @Override
    public String toString() {
        return Integer.toString(hashCode()%100);
    }

    public boolean isSkipsAMoveNow() {
        return skipsAMoveNow;
    }

    public void setSkipsAMoveNow(boolean thisMoveAdded) {
        this.skipsAMoveNow = thisMoveAdded;
    }

}
