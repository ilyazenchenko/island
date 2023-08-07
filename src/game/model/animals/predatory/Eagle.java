package game.model.animals.predatory;

import game.model.GameEntity;

public class Eagle extends PredatoryAnimal{

    public Eagle() {
        super(6, 20, 3, 1);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        return switch (gameEntity.getClass().getSimpleName()){
            case "Fox" -> 10;
            case "Rabbit", "Mouse" -> 90;
            case "Duck" -> 80;
            default -> 0;
        };
    }
}
