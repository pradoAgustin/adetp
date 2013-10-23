package backend;

public class Cell {
    protected int color;
    protected Direction nextPathDir = null;

    public Cell(int color){
       this(color, null);
    }

    public Cell(int color, Direction nextPathDir){
        this.color = color;
        this.nextPathDir = nextPathDir;
    }

    public int getColor(){
        return color;
    }
}
