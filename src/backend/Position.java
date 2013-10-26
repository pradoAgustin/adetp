package backend;

public class Position {
    int row, col;

    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the point at the specified direction
     */
    public Position getPosition(Direction d){
        return new Position(this.row + d.row, this.col + d.col);
    }

    public String toString(){
    	return "Position("+row+","+col+");";
    }
    public boolean equals(Object o){
        if(o == null) return false;
        if(!(o instanceof Position)) return false;
        Position other = ((Position)o);
        return this.row == other.row && this.col == other.col;
    }
}
