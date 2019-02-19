package pl.sda.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import pl.sda.backend.*;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SnakeApplication extends Application {

    private Game game;
    private static final double FIELD_HEIGHT = 40;
    private static final double FIELD_WIDTH = 40;
    private Canvas canvas;
    private Image appleImage;
    private Image grassImage;
    private Image snakeDownImage;
    private Image snakeUpImage;
    private Image snakeLeftImage;
    private Image snakeRightImage;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        addFiles();

        ExecutorService pool = Executors.newFixedThreadPool(1);


        newGame(pool);

        double height = (game.getAreaHeight() + 1) * FIELD_HEIGHT;
        double width = (game.getAreaHeight() + 1) * FIELD_HEIGHT;


        canvas = new Canvas(width, height);


        paint();





        Pane pane = new Pane(canvas);
        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DOWN:
                    game.moveDown();
                    break;
                case LEFT:
                    game.moveLeft();
                    break;
                case RIGHT:
                    game.moveRight();
                    break;
                case UP:
                    game.moveUp();
                    break;
                case N:
                    newGame(pool);
                    break;
            }


        });

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Wąż");
        primaryStage.show();

    }

    private void addFiles() {
        File appleFile = new File("C:\\Users\\lordm\\Development\\snake\\src\\main\\resources\\2076345.png");
        appleImage = new Image(appleFile.toURI().toString());
        File grassFile = new File("C:\\Users\\lordm\\Development\\snake\\src\\main\\resources\\Grass0073_1_270.jpg");
        grassImage = new Image(grassFile.toURI().toString());
        File snakeDown = new File("C:\\Users\\lordm\\Development\\snake\\src\\main\\resources\\snakeHeadDown.png");
        snakeDownImage = new Image(snakeDown.toURI().toString());
        File snakeUp = new File("C:\\Users\\lordm\\Development\\snake\\src\\main\\resources\\snakeHeadUp.png");
        snakeUpImage = new Image(snakeUp.toURI().toString());
        File snakeLeft = new File("C:\\Users\\lordm\\Development\\snake\\src\\main\\resources\\snakeHeadLeft.png");
        snakeLeftImage = new Image(snakeLeft.toURI().toString());
        File snakeRight = new File("C:\\Users\\lordm\\Development\\snake\\src\\main\\resources\\snakeHeadRight.png");
        snakeRightImage = new Image(snakeRight.toURI().toString());
    }

    private void paint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(grassImage, 0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.LIGHTGREY);
        for (int x = 0; x <= game.getAreaWidth(); x++) {
            for (int y = 0; y <= game.getAreaHeight(); y++) {
                gc.strokeRect(x * FIELD_WIDTH,
                        y * FIELD_HEIGHT,
                        FIELD_WIDTH,
                        FIELD_HEIGHT);
            }

        }
        gc.setFill(Color.LIMEGREEN);
        game.getSnake().getTail().forEach(gameField -> {
            gc.fillRect(
                    gameField.getX() * FIELD_WIDTH,
                    gameField.getY() * FIELD_HEIGHT,
                    FIELD_WIDTH,
                    FIELD_HEIGHT);
        });
        GameField head = game.getSnake().getTail().getFirst();
        SnakeDirection direction = game.getSnake().getDirection();
        switch (direction){
            case DOWN:
                gc.drawImage(snakeDownImage, head.getX()*FIELD_WIDTH, head.getY()*FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT);
                break;
            case UP:
                gc.drawImage(snakeUpImage, head.getX()*FIELD_WIDTH, head.getY()*FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT);
                break;
            case LEFT:
                gc.drawImage(snakeLeftImage, head.getX()*FIELD_WIDTH, head.getY()*FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT);
                break;
            case RIGHT:
                gc.drawImage(snakeRightImage, head.getX()*FIELD_WIDTH, head.getY()*FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT);
                break;
        }


        GameField apple = game.getApple();
        gc.drawImage(appleImage, apple.getX() * FIELD_WIDTH,
                apple.getY() * FIELD_HEIGHT,
                FIELD_WIDTH,
                FIELD_HEIGHT);


    }
    private void newGame(ExecutorService pool){
        game = new Game(new Snake(SnakeDirection.RIGHT,
                new GameField(2, 0),
                new GameField(1, 0),
                new GameField(0, 0)
        ));
        game.setApple(new GameField(6, 6));

        pool.submit(() ->
        {
            boolean isOver = false;
            while (!isOver) {

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    game.nextTurn();
                    paint();
                } catch (GameOverException e) {
                    isOver = true;

                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.setStroke(Color.BLACK);
                    gc.setFill(Color.WHITE);
                    gc.setFont(Font.font("Comic Sans MS",64));
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.strokeText("GAME OVER", canvas.getWidth() / 2, canvas.getHeight() / 2);
                    gc.fillText("GAME OVER", canvas.getWidth() / 2, canvas.getHeight() / 2);

                }

            }
        });

    }
}
