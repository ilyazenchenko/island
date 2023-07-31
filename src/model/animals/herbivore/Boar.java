package model.animals.herbivore;

import model.Animal;
import model.GameEntity;
import model.Plant;

public class Boar extends HerbivoreAnimal {

    public Boar() {
        super(400, 50, 2, 50, 100);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        if(gameEntity instanceof Caterpillar) return 90;
        return super.getProbabilityToEat(gameEntity);
    }

    @Override
    public boolean canEat(GameEntity gameEntity) {
        return super.canEat(gameEntity) || gameEntity instanceof Caterpillar; // TODO: добавить мыш
    }

}
