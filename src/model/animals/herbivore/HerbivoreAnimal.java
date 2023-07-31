package model.animals.herbivore;

import model.Animal;
import model.GameEntity;
import model.Plant;

import java.util.concurrent.ThreadLocalRandom;

public abstract class HerbivoreAnimal extends Animal {

    public HerbivoreAnimal(double weight, int maxPerField, int fieldsPerMove, double kgsForEatUp, int health) {
        super(weight, maxPerField, fieldsPerMove, kgsForEatUp, health);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        return 100;
    }

    @Override
    public boolean canEat(GameEntity gameEntity) {
        return gameEntity instanceof Plant;
    }

}
