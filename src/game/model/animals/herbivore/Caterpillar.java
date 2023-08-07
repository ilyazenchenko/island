package game.model.animals.herbivore;

import game.map.GameMap;
import game.model.GameEntity;

import java.util.List;

public class Caterpillar extends HerbivoreAnimal{

    public Caterpillar() {
        super(0.01, 1000, 0, 0.001);
    }

    @Override
    protected int move(int line, int column, List<GameEntity> lst, GameMap gameMap) {
        return lst.indexOf(this);
    }
}
