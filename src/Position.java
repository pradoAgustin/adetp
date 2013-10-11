
public class Position {
    int row, col;

    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Position down(){
        return new Position(row+1,col);
    }
    public Position up(){
        return new Position(row-1,col);
    }
    public Position left(){
        return new Position(row,col-1);
    }
    public Position right(){
        return new Position(row,col+1);
    }
}
