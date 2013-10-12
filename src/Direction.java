/**
 * Created with IntelliJ IDEA.
 * User: cristian
 * Date: 10/12/13
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Direction {
    UP(-1,0), DOWN(1,0), LEFT(0,-1), RIGHT(0,1);

    int row, col;

    Direction(int row, int col){
        this.row = row;
        this.col = col;
    }
}
