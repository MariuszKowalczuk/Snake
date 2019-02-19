package pl.sda.backend;

public class GameOverException extends RuntimeException{
    public GameOverException() {
        super("Gra skończona");
    }
}
