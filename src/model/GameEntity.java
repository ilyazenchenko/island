package model;

public abstract class GameEntity {

    protected double weight;

    protected int maxInField;

    protected boolean previousMoveAdded = true;

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

    public boolean isPreviousMoveAdded() {
        return previousMoveAdded;
    }

    public void setPreviousMoveAdded(boolean thisMoveAdded) {
        this.previousMoveAdded = thisMoveAdded;
    }

}
