package game;

import game.map.GameMap;
import game.map.MoveDirection;
import game.model.Animal;
import game.model.GameEntity;
import game.model.Plant;
import game.model.animals.herbivore.Caterpillar;
import game.model.animals.predatory.PredatoryAnimal;

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

    private void doMovesInField(int line, int column) {
        List<GameEntity> fieldLst = gameMap.getMap().get(line).get(column);
        tryAddPlant(line, column, fieldLst);
        for (int k = 0; k < fieldLst.size(); k++) {
            k = fieldLst.get(k).tryAMove(fieldLst, gameMap, line, column);
        }
        fieldLst.forEach(x -> x.setSkipsAMoveNow(false));
        fieldLst.stream().filter(x -> x instanceof PredatoryAnimal).forEach(x -> ((PredatoryAnimal) x).setTired(0));
    }

//    private void tryToMultiply(int i, int j, List<GameEntity> lst, Animal animal, ListIterator<GameEntity> iterator) {
//        while (iterator.hasNext()) {
//            GameEntity secondGameEntity = iterator.next();
//            if (animal == secondGameEntity || secondGameEntity.isSkipsAMoveNow())
//                continue;
//            if (animal.getClass().equals(secondGameEntity.getClass())) {
//                int whatToDo = Math.abs(ThreadLocalRandom.current().nextInt(100));
//                if (whatToDo < 20) {
//                    multiply(i, j, lst, animal, secondGameEntity);
//                }
//                return;
//            }
//        }
//    }

    public static void printMove(int line, int column, int newLine, int newColumn, Animal animal) {
        System.out.println(animal.getClass().getSimpleName() + animal + " перешел из клетки ["
                + line + ", " + column +
                "] в клетку [" + newLine + ", " + newColumn + "]");
    }

    public static void printMultiply(int i, int j, Animal animal, GameEntity secondGameEntity) {
        System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] животное "
                + animal + " размножилось с животным "
                + secondGameEntity);
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
        long plantsQuantity = lst.stream().filter(x -> x.getClass().equals(Plant.class)).count();
        int k;
        for (k = 0; k < 100; k++, plantsQuantity++) {
            if (plantsQuantity <= 200) {
                lst.add(new Plant());
            } else break;
        }
        if (k != 0) System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] выросло " + k + " растений");
    }


    public static void printDeath(int line, int column, Animal animal) {
        System.out.println("В клетке [" + (line + 1) + ", " + (column + 1) + "] умерло животное " + animal);
    }

    public static void printAnimalEatsSecond(int i, int j, Animal animal, GameEntity secondEntity) {
        System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] животное " +
                animal + " съело " +
                (secondEntity.getClass().getSimpleName().equals("Plant") ? "растение " : "животное ")
                + secondEntity + " hp: " + animal.getHealth());
    }

}
