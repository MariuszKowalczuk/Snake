package pl.sda.backend;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter

public class Game {
    @Setter(AccessLevel.NONE)
    private Snake snake;
    private int areaHeight = 10;
    private int areaWidth = 10;
    private GameField apple;
    private boolean alreadyMoved;

    public Game(Snake snake) {
        this.snake = snake;
    }


    public void nextTurn() {
        GameField nextField = snake.getNextField();
        if (isXOutOfArea(nextField) || isYOutOfArea(nextField)) {
            throw new GameOverException();
        }
        if (nextField.equals(apple)) {
            snake.eatApple();
            generateNewApple();

        }
        if (snake.getTail().contains(nextField)) {
            throw new GameOverException();
        }

        snake.move();
        alreadyMoved=false;
    }

    private void generateNewApple() {
        List<GameField> allFields = new ArrayList<>(areaHeight * areaWidth);
        for (int y = 0; y < areaHeight; y++) {
            for (int x = 0; x < areaWidth; x++) {
                allFields.add(new GameField(x, y));
            }
        }
        allFields.removeAll(snake.getTail());
        allFields.remove(apple);
        Collections.shuffle(allFields);
        apple = allFields.get(0);
    }

    private boolean isYOutOfArea(GameField nextField) {
        return nextField.getY() < 0 || nextField.getY() > areaHeight;
    }

    private boolean isXOutOfArea(GameField nextField) {
        return nextField.getX() < 0 || nextField.getX() > areaWidth;
    }


    public void moveDown() {
        move(SnakeDirection.DOWN);
    }

    public void moveUp() {
        move(SnakeDirection.UP);
    }

    public void moveRight() {
        move(SnakeDirection.RIGHT);
    }

    public void moveLeft() {
        move(SnakeDirection.LEFT);
    }

    private void move(SnakeDirection direction) {
        if (!alreadyMoved) {
            snake.setDirection(direction);
        }
        alreadyMoved = true;
    }


}


