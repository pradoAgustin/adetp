package backend;

public class Cell {
    protected int color;
    protected Direction nextPathDir;

    public Cell(int color){
        this.color = color;
        nextPathDir = null;
    }

    public Direction getNextPathDir(){
        return nextPathDir;
    }

}
