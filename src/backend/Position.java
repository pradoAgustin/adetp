package backend;

public class Position {
    int row, col;

    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the point in the specified direction
     */
    public Position getPosition(Direction d){
        return new Position(this.row + d.row, this.col + d.col);
    }
}
