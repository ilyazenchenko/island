package model;

public abstract class GameEntity {

    protected double weight;
    protected int maxInField;

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
}
