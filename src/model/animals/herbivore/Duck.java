package model.animals.herbivore;

import model.GameEntity;
import model.Plant;

public class Duck extends HerbivoreAnimal {

    public Duck() {
        super(1, 200, 4, 0.15);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        if(gameEntity instanceof Caterpillar) return 90;
        return super.getProbabilityToEat(gameEntity);
    }

}
