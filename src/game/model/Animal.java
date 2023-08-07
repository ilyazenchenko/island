package game.model;

import game.Game;
import game.map.GameMap;
import game.map.MoveDirection;
import game.model.animals.herbivore.Caterpillar;
import game.model.animals.predatory.PredatoryAnimal;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

public abstract class Animal extends GameEntity {

    protected int fieldsPerMove;
    protected double kgsForEatUp;
    protected int health = 100;


    public Animal(double weight, int maxPerField, int fieldsPerMove, double kgsForEatUp) {
        super(weight, maxPerField);
        this.fieldsPerMove = fieldsPerMove;
        this.kgsForEatUp = kgsForEatUp;
    }

    public boolean eat(GameEntity gameEntity) {
        if (ThreadLocalRandom.current().nextInt(100) < getProbabilityToEat(gameEntity)) {
            int newHealth = (int) (gameEntity.getWeight() / kgsForEatUp * 100);
            health = Math.min(health + newHealth, 100);
            return true;
        }
        return false;
    }

    public Animal multiply(GameEntity gameEntity) {
        setSkipsAMoveNow(true);
        gameEntity.setSkipsAMoveNow(true);
        try {
            return (Animal) gameEntity.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract int getProbabilityToEat(GameEntity gameEntity);

    public boolean tryEat(GameEntity gameEntity) {
        if (getProbabilityToEat(gameEntity) == 0)
            return false;
        return eat(gameEntity);
    }


    public int getFieldsPerMove() {
        return fieldsPerMove;
    }

    public double getKgsForEatUp() {
        return kgsForEatUp;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(health - 10, 0);
    }

    public int makeAMove(List<GameEntity> fieldLst, GameMap gameMap, int line, int column) {
        setHealth(health - 10);
        if (getHealth() == 0) {
            Game.printDeath(line, column, this);
            int index = fieldLst.indexOf(this);
            fieldLst.remove(this);
            return index - 1;
        }
        if (fieldLst.size() != 1) {
            if (health < 50) {
                boolean full = tryToEatFull(fieldLst, line, column);
                if (!full)
                    return move(line, column, fieldLst, gameMap);
            } else {
                tryToMultiply(fieldLst, line, column);
            }
        }
        return fieldLst.indexOf(this);
    }

    private boolean tryToEatFull(List<GameEntity> fieldLst, int line, int column) {
        Iterator<GameEntity> iterator = fieldLst.iterator();
        while (iterator.hasNext()) {
            GameEntity secondGameEntity = iterator.next();
            if (this == secondGameEntity) continue;
            if (tryEat(secondGameEntity)) {
                iterator.remove();
                Game.printAnimalEatsSecond(line, column, this, secondGameEntity);
                if (health >= 80) return true;
            }
        }
        return false;
    }

    public void tryToMultiply(List<GameEntity> fieldLst, int line, int column){
        for (GameEntity secondGameEntity : fieldLst) {
            if (this == secondGameEntity || secondGameEntity.isSkipsAMoveNow())
                continue;
            if (getClass().equals(secondGameEntity.getClass())) {
                int whatToDo = Math.abs(ThreadLocalRandom.current().nextInt(100));
                if (whatToDo < 20 && normalQuantity(fieldLst)) {
                    fieldLst.add(multiply(secondGameEntity));
                    Game.printMultiply(line, column, this, secondGameEntity);
                }
                return;
            }
        }
    }
    private boolean normalQuantity(List<GameEntity> lst) {
        long count = lst.stream().filter(x -> x.getClass().equals(getClass())).count();
        return count < getMaxPerField();
    }

    protected int move(int line, int column, List<GameEntity> lst, GameMap gameMap) {
        for (int k = 0; k < 3; k++) {
            MoveDirection animalMoveDirection = MoveDirection.getMoveDirection(line, gameMap.getHeight()- line - 1,
                    gameMap.getWidth() - column - 1, column, this);
            int newLine = line + animalMoveDirection.getToBottom() - animalMoveDirection.getToTop();
            int newColumn = column + animalMoveDirection.getToRight() - animalMoveDirection.getToLeft();
            List<GameEntity> newLst = gameMap.getMap().get(newLine).get(newColumn);
            if (normalQuantity(newLst)) {
                int index = lst.indexOf(this);
                lst.remove(this);
                newLst.add(this);
                setSkipsAMoveNow(true);
                Game.printMove(line, column, newLine, newColumn, this);
                return index - 1;
            }
        }
        return lst.indexOf(this);
    }
}