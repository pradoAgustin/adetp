import java.util.ArrayList;

public class Board {
	private int board[][];
    private int rows,
                cols;
	private ArrayList<Dot> dots =new ArrayList<Dot>();
	public Board(){
			int matrix[][]=new int[10][10];
			matrix[0][0]=1;
			matrix[8][9]=1;
			matrix[5][6]=2;
			matrix[9][9]=2;
			for(int i =0; i<10;i++)
			{
				for(int j=0;j<10;j++){
					System.out.print(matrix[i][j]);
					
				}
			System.out.println(" ");
			}
	}

    public void solve(){
        Dot initialDot = dots.get(0);
        Board solution = new Board();
        solve(initialDot.getColor(), null, initialDot.getStart(), 0, solution);
    }

    private void solve(int color, Position prevPos, Position currentPos, int index, Board solution){
        if(color == this.board[currentPos.row][currentPos.col]){
            if(currentPos == dots.get(index).getEnd()){
                if(dots.size() == index+1){             /* Si no quedan mas puntos por unir... */
                    saveSolution(solution);
                }else{                                  /* De lo contrario seguir con el siguiente punto */
                    Dot nextDot = dots.get(index+1);
                    solve(nextDot.getColor(), null, nextDot.getStart(), index+1, solution);
                }
            }
            return;
        }
        int originalColor = this.board[currentPos.row][currentPos.col];
        this.board[currentPos.row][currentPos.col] = color;
        Position nextPos;

        if((nextPos = currentPos.down()) != prevPos){
            solve(color, currentPos, currentPos.down(), index, solution);
        }
        if((nextPos = currentPos.up()) != prevPos){
            solve(color, currentPos, currentPos.up(), index, solution);
        }
        if((nextPos = currentPos.left()) != prevPos){
            solve(color, currentPos, currentPos.left(), index, solution);
        }
        if((nextPos = currentPos.right()) != prevPos){
            solve(color, currentPos, currentPos.right(), index, solution);
        }
        this.board[currentPos.row][currentPos.col] = originalColor;
    }

    private void saveSolution(Board solution){      // TODO ver si no hay problema con los clones
        if(solution.board == null){
            solution.board = this.board.clone();
            return;
        }
        if(solution.paintedCells() > this.paintedCells()){
            solution.board = this.board.clone();
        }
    }

    private int paintedCells(){
        int paintedCells = 0;
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                if(board[row][col] != -1)           /* -1 era la marca para espacio no? */
                    paintedCells++;
            }
        }
        return paintedCells;
    }
}
