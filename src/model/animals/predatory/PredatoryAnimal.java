package model.animals.predatory;

import model.Animal;
import model.GameEntity;
import model.animals.herbivore.Caterpillar;
import model.animals.herbivore.HerbivoreAnimal;

public abstract class PredatoryAnimal extends Animal{

    public PredatoryAnimal(double weight, int maxPerField, int fieldsPerMove, int kgsForEatUp, int health) {
        super(weight, maxPerField, fieldsPerMove, kgsForEatUp, health);
    }

    @Override
    public boolean canEat(GameEntity gameEntity) {
        if (gameEntity instanceof Caterpillar) return false;
        return gameEntity instanceof HerbivoreAnimal;
    }


}
