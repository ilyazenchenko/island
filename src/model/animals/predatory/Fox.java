package model.animals.predatory;

import model.GameEntity;
import model.animals.herbivore.Caterpillar;

public class Fox extends PredatoryAnimal {

    public Fox() {
        super(8, 30, 2, 2);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        return switch (gameEntity.getClass().getSimpleName()) {
            case "Rabbit" -> 70;
            case "Duck" -> 60;
            case "Caterpillar" -> 40;
            default -> 0;
        };
    }
}
