package model.animals.predatory;

import model.GameEntity;

public class Wolf extends PredatoryAnimal {

    public Wolf() {
        super(50, 30, 3, 8, 100);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        switch (gameEntity.getClass().getSimpleName()){
            case "Rabbit" -> {
                return 60;
            }
            case "Duck" -> {
                return 40;
            }
            case "Boar" -> {
                return 15;
            }
        }
        return 0;
    }
}
