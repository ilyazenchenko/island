import map.GameMap;
import map.MoveDirection;
import model.Animal;
import model.GameEntity;
import model.Plant;
import model.animals.herbivore.Caterpillar;
import model.animals.predatory.PredatoryAnimal;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    GameMap gameMap;

    public Game(int mapHeight, int mapWidth, int animalsInField) {
        gameMap = new GameMap(mapHeight, mapWidth, animalsInField);
    }

    public void run(int iterations) {
        for (int i = 0; i < iterations; i++) {
            doMoves();
            gameMap.printStatistics();
        }
    }

    public void doMoves() {
        for (int i = 0; i < gameMap.getHeight(); i++) {
            for (int j = 0; j < gameMap.getWidth(); j++) {
                doMovesInField(i, j);
            }
        }
    }

    private void doMovesInField(int i, int j) {
        List<GameEntity> lst = gameMap.getMap().get(i).get(j);
        tryAddPlant(i, j, lst);
        for (int k = 0; k < lst.size(); k++) {
            point:
            {
                GameEntity gameEntity = lst.get(k);
                if (gameEntity.isSkipsAMoveNow()) {
                    continue;
                }
                if (gameEntity instanceof Plant) {
                    tryAddPlant(i, j, lst);
                    continue;
                }
                Animal animal = (Animal) gameEntity;
                animal.setHealth(animal.getHealth() - 10);
                if (animal.getHealth() <= 0) {
                    handleDeath(i, j, lst, k, animal);
                    k--;
                    break point;
                }
                if (lst.size() != 1) {
                    var iterator = lst.listIterator();
                    if (animal.getHealth() < 50) {
                        k = tryToEatFull(i, j, k, animal, iterator);
                        if (animal.getHealth() < 50 && !(animal instanceof Caterpillar)) {
                            move(i, j, lst, animal);
                        }
                    } else {
                        tryToMultiply(i, j, lst, animal, iterator);
                    }
                }
            }
        }
        lst.forEach(x -> x.setSkipsAMoveNow(false));
        lst.stream().filter(x -> x instanceof PredatoryAnimal).forEach(x -> ((PredatoryAnimal) x).setTired(0));
    }

    private void tryToMultiply(int i, int j, List<GameEntity> lst, Animal animal, ListIterator<GameEntity> iterator) {
        while (iterator.hasNext()) {
            GameEntity secondGameEntity = iterator.next();
            if (animal == secondGameEntity || secondGameEntity.isSkipsAMoveNow())
                continue;
            if (animal.getClass().equals(secondGameEntity.getClass())) {
                int whatToDo = Math.abs(ThreadLocalRandom.current().nextInt(100));
                if (whatToDo < 20) {
                    multiply(i, j, lst, animal, secondGameEntity);
                }
                return;
            }
        }
    }

    private void move(int i, int j, List<GameEntity> lst, Animal animal) {
        for (int k = 0; k < 3; k++) {
            MoveDirection animalMoveDirection = animal.move(i, gameMap.getHeight() - i - 1,
                    gameMap.getWidth() - j - 1, j);
            int vertical = i + animalMoveDirection.getToBottom() - animalMoveDirection.getToTop();
            int horizontal = j + animalMoveDirection.getToRight() - animalMoveDirection.getToLeft();
            List<GameEntity> newLst = gameMap.getMap().get(vertical).get(horizontal);
            if (normalQuantity(newLst, animal)) {
                lst.remove(animal);
                newLst.add(animal);
                System.out.println(animal.getClass().getSimpleName() + animal + " перешел из клетки [" + (i + 1) + ", " + (j + 1) +
                        "] в клетку [" + (vertical + 1) + ", " + (horizontal + 1) + "]");
                return;
            }
        }
    }

    private void multiply(int i, int j, List<GameEntity> lst, Animal animal, GameEntity secondGameEntity) {
        if (normalQuantity(lst, animal)) {
            lst.add(animal.multiply(secondGameEntity));
            System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] животное "
                    + animal.getClass().getSimpleName() + animal + " размножилось с животным "
                    + secondGameEntity.getClass().getSimpleName() + secondGameEntity);
        }
    }

    private int tryToEatFull(int i, int j, int k, Animal animal, ListIterator<GameEntity> iterator) {
        GameEntity secondGameEntity;
        while (iterator.hasNext() && animal.getHealth() < 80) {
            secondGameEntity = iterator.next();
            if (animal == secondGameEntity)
                continue;
            if (animal.tryEat(secondGameEntity)) {
                iterator.remove();
                printAnimalEatsSecond(i, j, animal, secondGameEntity);
                if (iterator.nextIndex() - 1 < k)
                    k--;
                if (animal.getHealth() >= 80) return k;
            }
            if (animal instanceof PredatoryAnimal && ((PredatoryAnimal) animal).isTired())
                return k;
        }
        return k;
    }

    private void tryAddPlant(int i, int j, List<GameEntity> lst) {
        if (normalQuantity(lst, new Plant())) {
            lst.add(new Plant());
            System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] выросло растение");
        }
    }

    private static boolean normalQuantity(List<GameEntity> lst, GameEntity gameEntity) {
        long count = lst.stream().filter(x -> x.getClass().equals(gameEntity.getClass())).count();
        return count < gameEntity.getMaxPerField();
    }

    private void handleDeath(int i, int j, List<GameEntity> lst, int k, Animal animal) {
        System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] умерло животное " +
                animal.getClass().getSimpleName() + animal);
        lst.remove(k);
    }

    private void printAnimalEatsSecond(int i, int j, Animal animal, GameEntity secondEntity) {
        System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] животное " +
                animal.getClass().getSimpleName() + animal + " съело " +
                (secondEntity.getClass().getSimpleName().equals("Plant") ? "растение " : "животное ")
                + secondEntity.getClass().getSimpleName() + secondEntity + " hp: " + animal.getHealth());
    }

}
