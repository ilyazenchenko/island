package game.model.animals.herbivore;

import game.model.GameEntity;

public class Mouse extends HerbivoreAnimal{

    public Mouse() {
        super(0.05, 500, 1, 0.01);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        if (gameEntity instanceof Caterpillar) return 90;
        return super.getProbabilityToEat(gameEntity);
    }
}
