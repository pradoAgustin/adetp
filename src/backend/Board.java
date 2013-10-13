package backend;
import java.util.ArrayList;
import java.util.List;

public class Board {
	private int matrix[][];
	private ArrayList<Dot> dots = new ArrayList<Dot>();
	
	public int[][] getIntBoard(){
		return matrix;
	}
	public  void addDot(Dot d){
		dots.add(d);
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
        if(matrix.length <= currentPos.row || currentPos.row < 0
           || matrix[0].length <= currentPos.col || currentPos.col < 0) return;
        int currentPosColor = this.matrix[currentPos.row][currentPos.col];
        if(color == currentPosColor && currentPos != dots.get(index).getStart()){
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
        this.matrix[currentPos.row][currentPos.col] = color;
        Position nextPos;

        for(Direction d : Direction.values()){
            if(!(nextPos = currentPos.getPosition(d)).equals(prevPos));
                solve(color, currentPos, nextPos, index, solution);
        }
        this.matrix[currentPos.row][currentPos.col] = currentPosColor;
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
    public int unPaintedCells(){
    	return colsSize()*rowsSize()-paintedCells();
    }
}
