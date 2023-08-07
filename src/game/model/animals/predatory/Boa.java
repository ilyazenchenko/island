package game.model.animals.predatory;

import game.model.GameEntity;

public class Boa extends PredatoryAnimal{

    public Boa() {
        super(15, 30, 1, 3);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        return switch (gameEntity.getClass().getSimpleName()) {
            case "Fox" -> 15;
            case "Rabbit" -> 20;
            case "Mouse" -> 40;
            case "Duck" -> 10;
            default -> 0;
        };
    }
}
