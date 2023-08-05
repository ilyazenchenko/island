package model.animals.predatory;

import model.GameEntity;

public class Bear extends PredatoryAnimal{

    public Bear() {
        super(500, 5, 2, 80);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        return switch (gameEntity.getClass().getSimpleName()){
            case "Boa", "Deer", "Rabbit" -> 80;
            case "Horse" -> 40;
            case "Mouse" -> 90;
            case "Sheep", "Goat" -> 70;
            case "Boar" -> 50;
            case "Buffalo" -> 20;
            case "Duck" -> 10;
            default -> 0;
        };
    }
}
