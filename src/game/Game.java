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
            GameEntity gameEntity = fieldLst.get(k);
            if (!(gameEntity instanceof Plant)) {
                k = gameEntity.tryAMove(fieldLst, gameMap, line, column);
            }
        }
        fieldLst.forEach(x -> x.setSkipsAMoveNow(false));
    }


    public static void printMove(int line, int column, int newLine, int newColumn, Animal animal) {
        System.out.println(animal + " перешел из клетки ["
                + (line + 1) + ", " + (column + 1) +
                "] в клетку [" + (newLine + 1) + ", " + (newColumn + 1) + "]");
    }

    public static void printMultiply(int i, int j, Animal animal, GameEntity secondGameEntity) {
        System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] животное "
                + animal + " размножилось с животным "
                + secondGameEntity);
    }

    private void tryAddPlant(int i, int j, List<GameEntity> lst) {
        long plantsQuantity = lst.stream().filter(x -> x.getClass().equals(Plant.class)).count();
        int k;
        for (k = 0; k < 100; k++, plantsQuantity++) {
            if (plantsQuantity < 200) {
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
