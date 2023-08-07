package game.map;

import game.model.GameEntity;
import game.model.Plant;
import game.model.animals.herbivore.*;
import game.model.animals.predatory.*;

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
                    int probability = ThreadLocalRandom.current().nextInt(2545); // 2545
                    if (probability < 30)
                        gameEntity = new Wolf();
                    else if (probability < 60)
                        gameEntity = new Fox();
                    else if (probability < 110)
                        gameEntity = new Boar();
                    else if (probability < 260)
                        gameEntity = new Rabbit();
                    else if (probability < 460)
                        gameEntity = new Duck();
                    else if (probability < 660)
                        gameEntity = new Plant();
                    else if (probability < 1660)
                        gameEntity = new Caterpillar();
                    else if (probability < 1690)
                        gameEntity = new Boa();
                    else if (probability < 1695)
                        gameEntity = new Bear();
                    else if (probability < 1715)
                        gameEntity = new Eagle();
                    else if (probability < 1735)
                        gameEntity = new Horse();
                    else if (probability < 1755)
                        gameEntity = new Deer();
                    else if (probability < 2255)
                        gameEntity = new Mouse();
                    else if (probability < 2395)
                        gameEntity = new Goat();
                    else if (probability < 2535)
                        gameEntity = new Sheep();
                    else
                        gameEntity = new Buffalo();
                    gmLst.add(gameEntity);
                }
            }
        }
    }

    public void printStatistics() {
        int predatoryCnt = 0, herbivoreCnt = 0, plantCnt = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print("Клетка [" + (i + 1) + ", " + (j + 1) + "]: ");
                for (GameEntity gameEntity : map.get(i).get(j)) {
                    System.out.print(gameEntity + ", ");
                    if (gameEntity instanceof PredatoryAnimal) predatoryCnt++;
                    else if (gameEntity instanceof HerbivoreAnimal) herbivoreCnt++;
                    else if (gameEntity instanceof Plant) plantCnt++;
                }
                System.out.println();
            }
        }
        System.out.println("Хищников: " + predatoryCnt + ", травоядных: " + herbivoreCnt + ", растений: " + plantCnt);
    }

    public List<List<List<GameEntity>>> getMap() {
        return map;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getAnimalsInField() {
        return animalsInField;
    }

    public void setAnimalsInField(int animalsInField) {
        this.animalsInField = animalsInField;
    }
}
