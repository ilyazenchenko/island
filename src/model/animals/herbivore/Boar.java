package model.animals.herbivore;

import model.Animal;
import model.GameEntity;
import model.Plant;

public class Boar extends HerbivoreAnimal {

    public Boar() {
        super(400, 50, 2, 50);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        if(gameEntity instanceof Caterpillar) return 90;
        return super.getProbabilityToEat(gameEntity);
    }

}
