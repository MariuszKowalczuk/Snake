package pl.sda.backend;

import lombok.*;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString


public class GameField {
    private int x;
    private int y;

    public GameField getRight(){
        return new GameField(x+1, y);
    }

    public GameField getLeft(){
        return new GameField(x-1, y);
    }
    public GameField getUp(){
        return new GameField(x, y-1);
    }
    public GameField getDown(){
        return new GameField(x, y+1);
    }


}
