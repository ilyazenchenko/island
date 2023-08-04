import map.GameMap;
import model.Animal;
import model.GameEntity;
import model.Plant;
import model.animals.predatory.PredatoryAnimal;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    GameMap gameMap;

    public Game(int mapHeight, int mapWidth, int animalsInField) {
        gameMap = new GameMap(mapHeight, mapWidth, animalsInField);
    }

    public void start(int iterations) {
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
                    while (iterator.hasNext()) {
                        GameEntity secondGameEntity = iterator.next();
                        if (animal == secondGameEntity)
                            continue;
                        if (animal.getHealth() < 50) {
                            if (animal.tryEat(secondGameEntity)) {
                                iterator.remove();
                                printAnimalEatsSecond(i, j, animal, secondGameEntity);
                                if (iterator.nextIndex() - 1 < k)
                                    k--;
                                if (animal.getHealth() > 80) break point;
                            }
                            if(animal instanceof PredatoryAnimal && ((PredatoryAnimal)animal).isTired())
                                break point;
                        } else {
                            if (animal.canEat(secondGameEntity)) {
                                while (iterator.hasNext()) {
                                    secondGameEntity = iterator.next();
                                    if (animal == secondGameEntity || secondGameEntity.isSkipsAMoveNow())
                                        continue;
                                    if (animal.getClass().equals(secondGameEntity.getClass())) {
                                        if (Math.abs(ThreadLocalRandom.current().nextInt(100)) < 20) {
                                            lst.add(animal.multiply(secondGameEntity));
                                            System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] животное "
                                                    + animal.getClass().getSimpleName() + animal + " размножилось с животным "
                                                    + secondGameEntity.getClass().getSimpleName() + secondGameEntity);
                                        }
                                        break point;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        lst.forEach(x -> x.setSkipsAMoveNow(false));
        lst.stream().filter(x-> x instanceof PredatoryAnimal).forEach(x -> ((PredatoryAnimal)x).setTired(0));
    }

    private void tryAddPlant(int i, int j, List<GameEntity> lst) {
        if (Math.abs(ThreadLocalRandom.current().nextInt(100)) < 20) {
            lst.add(new Plant());
            System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] выросло растение");
        }
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
                + secondEntity.getClass().getSimpleName() + secondEntity + " hp: " +animal.getHealth());
    }

}
