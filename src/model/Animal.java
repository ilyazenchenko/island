package model;

import map.MoveDirection;
import model.animals.herbivore.Caterpillar;
import model.animals.predatory.PredatoryAnimal;

import java.lang.reflect.InvocationTargetException;
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

    public MoveDirection move(int leftToTop, int leftToBottom, int leftToRight, int leftToLeft) {
        MoveDirection moveDirection;
        do {
            moveDirection = new MoveDirection();
            for (int i = 0; i < fieldsPerMove; i++) {
                int random = Math.abs(ThreadLocalRandom.current().nextInt(100));
                if (random <= 25 && moveDirection.getToTop() < leftToTop) {
                    moveDirection.increaseToTop();
                } else if (random <= 50 && moveDirection.getToBottom() < leftToBottom) {
                    moveDirection.increaseToBottom();
                } else if (random <= 75 && moveDirection.getToRight() < leftToRight) {
                    moveDirection.increaseToRight();
                } else if (moveDirection.getToLeft() < leftToLeft) {
                    moveDirection.increaseToLeft();
                }
            }
        } while (abs(moveDirection.getToLeft()) - abs(moveDirection.getToRight()) == 0 &&
                abs(moveDirection.getToTop()) - abs(moveDirection.getToBottom()) == 0);
        setSkipsAMoveNow(true);
        return moveDirection;
    }

    public abstract int getProbabilityToEat(GameEntity gameEntity);

    public boolean tryEat(GameEntity gameEntity) {
        if (getProbabilityToEat(gameEntity) == 0)
            return false;
        if (this instanceof PredatoryAnimal && !(gameEntity instanceof Caterpillar))
            ((PredatoryAnimal) this).increaseTired();
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
        this.health = health;
    }


}