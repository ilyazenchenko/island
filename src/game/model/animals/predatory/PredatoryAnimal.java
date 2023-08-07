package game.model.animals.predatory;

import game.model.Animal;

public abstract class PredatoryAnimal extends Animal{

    public PredatoryAnimal(double weight, int maxPerField, int fieldsPerMove, int kgsForEatUp) {
        super(weight, maxPerField, fieldsPerMove, kgsForEatUp);
    }
}
