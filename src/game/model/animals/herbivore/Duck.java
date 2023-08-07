package game.model.animals.herbivore;

import game.model.GameEntity;

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
