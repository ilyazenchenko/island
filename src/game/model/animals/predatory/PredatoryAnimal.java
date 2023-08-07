package game.model.animals.predatory;

import game.model.Animal;

public abstract class PredatoryAnimal extends Animal{

    protected int tired = 0;

    public PredatoryAnimal(double weight, int maxPerField, int fieldsPerMove, int kgsForEatUp) {
        super(weight, maxPerField, fieldsPerMove, kgsForEatUp);
    }

    public void increaseTired(){
        tired ++;
    }
    public boolean isTired(){
        return tired > 2;
    }

    public void setTired(int tired) {
        this.tired = tired;
    }
}
