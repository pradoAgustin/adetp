package backend;


/**
 * Enum de 4 direcciones en un tablero (horizontales, verticales)
 */
public enum Direction4axis {
    UP(-1,0), DOWN(1,0), LEFT(0,-1), RIGHT(0,1);
    

    int row, col;

    Direction4axis(int row, int col){
        this.row = row;
        this.col = col;
    }
}