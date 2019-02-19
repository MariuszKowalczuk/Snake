package pl.sda.backend;

public enum SnakeDirection {
    UP, LEFT, RIGHT, DOWN;

    public boolean isOpposite(SnakeDirection direction) {
        switch (this){
            case UP:return direction==DOWN;
            case DOWN:return direction==UP;
            case LEFT:return direction==RIGHT;
            case RIGHT:return direction==LEFT;
        }
        return false;
    }
}
