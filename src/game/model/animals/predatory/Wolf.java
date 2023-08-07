package game.model.animals.predatory;

import game.model.GameEntity;

public class Wolf extends PredatoryAnimal {

    public Wolf() {
        super(50, 30, 3, 8);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        return switch (gameEntity.getClass().getSimpleName()) {
            case "Rabbit" -> 60;
            case "Duck" -> 40;
            case "Boar" -> 15;
            default -> 0;
        };
    }
}
