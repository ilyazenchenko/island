package game.model.animals.herbivore;

import game.model.Animal;
import game.model.GameEntity;
import game.model.Plant;

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
