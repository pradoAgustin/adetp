import java.util.ArrayList;
import java.util.List;

public class Board {
	private int matrix[][];
	private ArrayList<Dot> dots =new ArrayList<Dot>();
	
	public int[][] getIntBoard(){
		return matrix;
	}

	public boolean isOrigin(int row,int col,int color){
		for(Dot d:dots){
			if(d.getColor() == color){
				return d.getStart().col == col && d.getStart().row == row;
			}
		}
		return false;
	}
	
	public boolean isEnd(int row,int col,int color){
		for(Dot d : dots){
			if(d.getColor() == color){
				return d.getEnd().col == col && d.getEnd().row == row;
			}
		}
		return false;
	}

	public int colsSize(){
		return matrix[0].length;
	}
	
	public int rowsSize(){
		return matrix.length;
	}

	public Board(){
			matrix=new int[10][10];

			for(int row = 0; row < matrix.length; row++)
			{
				for(int col = 0; col < matrix[0].length; col++){
					matrix[row][col] = Color.WHITE.getNum();
					
				}
			
			}
			matrix[0][0]=1;dots.add(new Dot(new Position(0, 0), new Position(8, 9), 1));
			matrix[8][9]=1;
			matrix[5][6]=2;dots.add(new Dot(new Position(5, 6), new Position(9, 9), 2));
			matrix[9][9]=2;
	}

    public Board(int[][] matrix2, ArrayList<Dot> listcolor) {
		matrix=matrix2;
		dots=listcolor;
	}

	public Board solve(){
        Dot initialDot = dots.get(0);
        Board solution = new Board();
        solve(initialDot.getColor(), null, initialDot.getStart(), 0, solution);
        return solution;
    }

    private void solve(int color, Position prevPos, Position currentPos, int index, Board solution){
        if(matrix.length <= currentPos.row || matrix[0].length <= currentPos.col) return;
        if(color == this.matrix[currentPos.row][currentPos.col]){
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
        int originalColor = this.matrix[currentPos.row][currentPos.col];
        this.matrix[currentPos.row][currentPos.col] = color;
        Position nextPos;

        if((nextPos = currentPos.down()) != prevPos)
            solve(color, currentPos, currentPos.down(), index, solution);
        if((nextPos = currentPos.up()) != prevPos)
            solve(color, currentPos, currentPos.up(), index, solution);
        if((nextPos = currentPos.left()) != prevPos)
            solve(color, currentPos, currentPos.left(), index, solution);
        if((nextPos = currentPos.right()) != prevPos)
            solve(color, currentPos, currentPos.right(), index, solution);
        this.matrix[currentPos.row][currentPos.col] = originalColor;
    }

    private void saveSolution(Board solution){      // TODO ver si no hay problema con los clones
        if(solution.matrix == null){
            solution.matrix = this.matrix.clone();
            return;
        }
        if(solution.paintedCells() > this.paintedCells()){
            solution.matrix = this.matrix.clone();
        }
    }

    private int paintedCells(){
        int paintedCells = 0;
        for(int row = 0; row < matrix.length; row++){
            for(int col = 0; col < matrix[0].length; col++){
                if(matrix[row][col] != -1)           /* -1 era la marca para espacio no? */
                    paintedCells++;
            }
        }
        return paintedCells;
    }
}
