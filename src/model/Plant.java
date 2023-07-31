package model;

public class Plant extends GameEntity{

    public Plant() {
        super(1, 200);
    }

    @Override
    public boolean canEat(GameEntity gameEntity) {
        return false;
    }

    public double getWeight() {
        return weight;
    }


    public int getMaxInField() {
        return maxInField;
    }
}
