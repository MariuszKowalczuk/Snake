package pl.sda.backend;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.LinkedList;

@EqualsAndHashCode
@ToString

public class Snake {

    @Getter
    private SnakeDirection direction;
    @Getter
    private LinkedList<GameField> tail = new LinkedList<>();
    private boolean isEating;

    public Snake(SnakeDirection direction, GameField... gameFields) {
        this.direction = direction;
        tail.addAll(Arrays.asList(gameFields));
    }

    public void move() {

        GameField nextField = getNextField();
        tail.addFirst(nextField);
        if (!isEating) {
            tail.removeLast();
        } else {
            isEating = false;
        }
    }

    public GameField getNextField() {
        switch (direction) {
            case RIGHT:
                return getHead().getRight();
            case LEFT:
                return getHead().getLeft();
            case UP:
                return getHead().getUp();
            case DOWN:
                return getHead().getDown();

        }
        throw new IllegalArgumentException("Brak kierunku węża");

    }

    public void setDirection(SnakeDirection direction) {
        if (!direction.isOpposite(this.direction)) {
            this.direction = direction;
        }
    }

    private GameField getHead() {
        return tail.getFirst();
    }

    public void eatApple() {
        isEating = true;

    }
}
