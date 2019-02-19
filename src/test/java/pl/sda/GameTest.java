package pl.sda;

import org.junit.Rule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.sda.backend.Game;
import pl.sda.backend.GameField;
import pl.sda.backend.GameOverException;
import pl.sda.backend.Snake;

import static pl.sda.backend.SnakeDirection.*;


public class GameTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldMoveForwardWhenThereIsNoAction() {
        //given
        Snake snake = new Snake(RIGHT,
                field(2, 1),
                field(1, 1),
                field(0, 1));
        Game game = new Game(snake);

        //when
        game.nextTurn();

        //then
        Snake expectedsnake = new Snake(RIGHT,
                field(3, 1),
                field(2, 1),
                field(1, 1));
        Assert.assertEquals(expectedsnake, snake);
    }

    @Test
    public void shouldMoveDownWhenThereIsDownAction() {
        //given
        Snake snake = new Snake(RIGHT,
                field(2, 1),
                field(1, 1),
                field(0, 1));
        Game game = new Game(snake);

        //when
        game.moveDown();
        game.nextTurn();

        //then
        Snake expectedsnake = new Snake(DOWN,
                field(2, 2),
                field(2, 1),
                field(1, 1));
        Assert.assertEquals(expectedsnake, snake);
    }

    @Test
    public void shouldMoveUpWhenThereIsUpAction() {
        //given
        Snake snake = new Snake(RIGHT,
                field(2, 1),
                field(1, 1),
                field(0, 1));
        Game game = new Game(snake);

        //when
        game.moveUp();
        game.nextTurn();

        //then
        Snake expectedsnake = new Snake(UP,
                field(2, 0),
                field(2, 1),
                field(1, 1));
        Assert.assertEquals(expectedsnake, snake);
    }

    @Test
    public void shouldMoveLeftWhenThereIsLeftAction() {
        //given
        Snake snake = new Snake(DOWN,
                field(2, 2),
                field(2, 1),
                field(2, 0));
        Game game = new Game(snake);

        //when
        game.moveLeft();
        game.nextTurn();

        //then
        Snake expectedsnake = new Snake(LEFT,
                field(1, 2),
                field(2, 2),
                field(2, 1));
        Assert.assertEquals(expectedsnake, snake);
    }

    @Test
    public void shouldMoveRightWhenThereIsRightAction() {
        //given
        Snake snake = new Snake(DOWN,
                field(2, 2),
                field(2, 1),
                field(2, 0));
        Game game = new Game(snake);

        //when
        game.moveRight();
        game.nextTurn();

        //then
        Snake expectedsnake = new Snake(RIGHT,
                field(3, 2),
                field(2, 2),
                field(2, 1));
        Assert.assertEquals(expectedsnake, snake);
    }

    @Test
    public void shouldMoveForwardWhenThereIsOppositeDirectionAction() {
        //given
        Snake snake = new Snake(DOWN,
                field(2, 2),
                field(2, 1),
                field(2, 0));
        Game game = new Game(snake);

        //when
        game.moveUp();
        game.nextTurn();

        //then
        Snake expectedsnake = new Snake(DOWN,
                field(2, 3),
                field(2, 2),
                field(2, 1));
        Assert.assertEquals(expectedsnake, snake);
    }

    @Test
    public void GameShouldBeOverWhenBottomBorderIsReached() {
        //given
        Snake snake = new Snake(DOWN,
                field(2, 2),
                field(2, 1),
                field(2, 0));
        Game game = new Game(snake);
        game.setAreaHeight(2);

        //expect
        expectedException.expect(GameOverException.class);
        game.nextTurn();
    }

    @Test
    public void shouldEatApple() {
        //given
        Snake snake = new Snake(DOWN,
                field(2, 2),
                field(2, 1),
                field(2, 0));
        Game game = new Game(snake);
        game.setApple(new GameField(2,3));
        game.setApple(new GameField(2,3));


        //when
        game.nextTurn();

        //then
        Snake expectedSnake = new Snake(DOWN,
                field(2, 3),
                field(2, 2),
                field(2, 1),
                field(2, 0));

        Assert.assertEquals(expectedSnake, snake);
    }

    @Test
    public void shouldGenerateNewApple() {
        //given
        Snake snake = new Snake(DOWN,
                field(2, 2),
                field(2, 1),
                field(2, 0));
        Game game = new Game(snake);
        game.setApple(new GameField(2,3));


        //expect
        game.nextTurn();

        //then
        GameField apple = game.getApple();
        Assert.assertNotEquals(field(2, 3), apple);
        Assert.assertNotEquals(field(2, 2), apple);
        Assert.assertNotEquals(field(2, 1), apple);
        Assert.assertNotEquals(field(2, 0), apple);


    }

    @Test
    public void GameShouldBeOverWhenTailIsReached() {
        //given
        Snake snake = new Snake(LEFT,
                field(2, 3),
                field(3, 3),
                field(3, 2),
                field(3, 1),
                field(2, 1),
                field(1, 1),
                field(1, 2),
                field(1, 3),
                field(1, 4));
        Game game = new Game(snake);


        //expect
        expectedException.expect(GameOverException.class);
        game.nextTurn();
    }

    @Test
    public void shouldIgnoreSecondActionPerTurn() {
        //given
        Snake snake = new Snake(RIGHT,
                field(2, 1),
                field(1, 1),
                field(0, 1));
        Game game = new Game(snake);

        //when
        game.moveDown();
        game.moveLeft();
        game.moveUp();
        game.nextTurn();

        //then
        Snake expectedsnake = new Snake(DOWN,
                field(2, 2),
                field(2, 1),
                field(1, 1));
        Assert.assertEquals(expectedsnake, snake);
    }




    private GameField field(int x, int y) {
        return new GameField(x, y);
    }


}