package game.model;

import game.map.GameMap;

import java.util.List;

public abstract class GameEntity {

    protected double weight;

    protected int maxInField;

    protected boolean skipsAMoveNow = true;

    public GameEntity(double weight, int maxInField) {
        this.weight = weight;
        this.maxInField = maxInField;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxPerField() {
        return maxInField;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+hashCode()%100;
    }

    public boolean isSkipsAMoveNow() {
        return skipsAMoveNow;
    }

    public void setSkipsAMoveNow(boolean thisMoveAdded) {
        this.skipsAMoveNow = thisMoveAdded;
    }

    public int tryAMove(List<GameEntity> fieldLst, GameMap gameMap, int line, int column){
        if (this instanceof Plant || skipsAMoveNow)
            return fieldLst.indexOf(this);
        return ((Animal)this).makeAMove(fieldLst, gameMap, line, column);
    }

}
