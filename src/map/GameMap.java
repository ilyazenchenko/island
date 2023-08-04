package map;

import model.Animal;
import model.GameEntity;
import model.Plant;
import model.animals.herbivore.*;
import model.animals.predatory.Fox;
import model.animals.predatory.Wolf;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameMap {

    private final List<List<List<GameEntity>>> map;

    int height;
    int width;
    int animalsInField;

    public GameMap(int height, int width, int animalsInField) {
        this.height = height;
        this.width = width;
        this.animalsInField = animalsInField;

        map = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            map.add(new ArrayList<>());
        }

        for (List<List<GameEntity>> lst : map) {
            for (int i = 0; i < width; i++) {
                lst.add(new ArrayList<>(width));
            }

            for (List<GameEntity> gmLst : lst) {
                for (int i = 0; i < animalsInField; i++) {
                    GameEntity gameEntity;
                    int probability = ThreadLocalRandom.current().nextInt(100);
                    if (probability < 11)
                        gameEntity = new Boar();
                    else if (probability < 21)
                        gameEntity = new Caterpillar();
                    else if (probability < 31)
                        gameEntity = new Duck();
                    else if (probability < 41)
                        gameEntity = new Rabbit();
                    else if (probability < 51)
                        gameEntity = new Fox();
                    else if (probability < 61)
                        gameEntity = new Wolf();
                    else
                        gameEntity = new Plant();
                    gmLst.add(gameEntity);
                }
            }
        }
    }

    public void printStatistics() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print("Клетка [" + (i + 1) + ", " + (j + 1) + "]: ");
                for (GameEntity gameEntity : map.get(i).get(j)) {
                    System.out.print(gameEntity.getClass().getSimpleName() + gameEntity + ", ");
                }
                System.out.println();
            }
        }
    }

    public void doMoves() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                doMovesInField(i, j);
            }
        }
    }

    private void doMovesInField(int i, int j) {
        List<GameEntity> lst = map.get(i).get(j);
        for (int k = 0; k < lst.size(); k++) {
            point:
            {
                GameEntity gameEntity = lst.get(k);
                if(gameEntity.isPreviousMoveAdded()){
                    gameEntity.setPreviousMoveAdded(false);
                    continue;
                }
                if (gameEntity instanceof Plant) {
                    if (Math.abs(ThreadLocalRandom.current().nextInt(100)) < 20) {
                        lst.add(new Plant());
                        System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] размножилось растение" + gameEntity);
                    }
                    continue;
                }
                Animal animal = (Animal) gameEntity;
                animal.setHealth(animal.getHealth() - 10);
                if (lst.size() != 1) {
                    var iterator = lst.listIterator();
                    while (iterator.hasNext()) {
                        GameEntity secondEntity = iterator.next();
                        if (animal == secondEntity)
                            continue;
                        if (animal.tryEat(secondEntity)) {
                            iterator.remove();
                            printAnimalEatsSecond(i, j, animal, secondEntity);
                            if(iterator.nextIndex()-1 < k){
                                k--;
                            }
                            break point;
                        } else {
                            if (animal.getHealth() <= 0) {
                                handleDeath(i, j, lst, k, animal);
                                k--;
                                break point;
                            }
                            if (animal.canEat(secondEntity))
                                break point;
                        }
                    }
                } else {
                    if (animal.getHealth() <= 0) {
                        handleDeath(i, j, lst, k, animal);
                        break;
                    }
                }
            }
        }
    }

    private static void handleDeath(int i, int j, List<GameEntity> lst, int k, Animal animal) {
        System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] умерло животное " +
                animal.getClass().getSimpleName());
        lst.remove(k);
    }

    private static void printAnimalEatsSecond(int i, int j, Animal animal, GameEntity secondEntity) {
        System.out.println("В клетке [" + (i + 1) + ", " + (j + 1) + "] животное " +
                animal.getClass().getSimpleName() + animal + " съело " +
                (secondEntity.getClass().getSimpleName().equals("Plant") ? "растение " : "животное ")
                + secondEntity.getClass().getSimpleName() + secondEntity);
    }

}
