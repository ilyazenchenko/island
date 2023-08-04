package model.animals.predatory;

import model.GameEntity;
import model.animals.herbivore.Caterpillar;

public class Fox extends PredatoryAnimal {

    public Fox() {
        super(8, 30, 2, 2, 100);
    }

    @Override
    public int getProbabilityToEat(GameEntity gameEntity) {
        switch (gameEntity.getClass().getSimpleName()){
            case "Rabbit" -> {
                return 70;
            }
            case "Duck" -> {
                return 60;
            }
            case "Caterpillar" -> {
                return 40;
            }
        }
        return 0;
    }

    @Override
    public boolean canEat(GameEntity gameEntity) {
        if(gameEntity instanceof Caterpillar) return true;
        return super.canEat(gameEntity);
    }

    @Override
    public void increaseTired() {
        super.increaseTired();
    }
}
