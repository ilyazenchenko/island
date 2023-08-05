package model.animals.herbivore;

import model.Animal;
import model.GameEntity;
import model.Plant;

import java.util.concurrent.ThreadLocalRandom;

public abstract class HerbivoreAnimal extends Animal {

    public HerbivoreAnimal(double weight, int maxPerField, int fieldsPerMove, double kgsForEatUp) {
        super(weight, maxPerField, fieldsPerMove, kgsForEatUp);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        if(gameEntity instanceof Plant)
            return 100;
        return 0;
    }

}
