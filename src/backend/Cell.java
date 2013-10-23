package backend;

public class Cell {
    private int color;
    private Direction nextPathDir;

    public Cell(int color){
        this.color = color;
        nextPathDir = null;
    }

    public int getColor(){
        return color;
    }

    public Direction getNextPathDir(){
        return nextPathDir;
    }

}
