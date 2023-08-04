package model;

import map.MoveDirection;
import model.animals.predatory.PredatoryAnimal;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends GameEntity {

    protected int fieldsPerMove;
    protected double kgsForEatUp;

    protected int health;


    public Animal(double weight, int maxPerField, int fieldsPerMove, double kgsForEatUp, int health) {
        super(weight, maxPerField);
        this.fieldsPerMove = fieldsPerMove;
        this.kgsForEatUp = kgsForEatUp;
        this.health = health;
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
        MoveDirection moveDirection = new MoveDirection();
        for (int i = 0; i < fieldsPerMove; i++) {
            int random = Math.abs(ThreadLocalRandom.current().nextInt(100));
            if (random <= 25 && leftToTop > 0) {
                moveDirection.increaseHeight();
            } else if (random <= 50 && leftToBottom > 0) {
                moveDirection.decreaseHeight();
            } else if (random <= 75 && leftToRight > 0) {
                moveDirection.increaseWidth();
            } else if (leftToLeft > 0) {
                moveDirection.decreaseWidth();
            }
        }
        return moveDirection;
    }

    public abstract int getProbabilityToEat(GameEntity gameEntity);

    public abstract boolean canEat(GameEntity gameEntity);

    public boolean tryEat(GameEntity gameEntity) {
        if (!canEat(gameEntity))
            return false;
        if (this instanceof PredatoryAnimal) ((PredatoryAnimal)this).increaseTired();
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