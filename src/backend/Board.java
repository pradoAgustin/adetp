package backend;
import java.util.ArrayList;
import java.util.List;

public class Board {
	private int matrix[][];
	private ArrayList<Dot> dots = new ArrayList<Dot>();
    private long calls = 0;
	
	public int[][] getIntBoard(){
		return matrix;
	}

    public long getCalls(){
        return calls;
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

    public Board(int[][] matrix, ArrayList<Dot> dots) {
		this.matrix = matrix;
		this.dots = dots;
	}

    /**
     * @return a Board with the matrix containing the solution if there is one,
     *         null otherwise
     */
	public Board solve(){
        Dot initialDot = dots.get(0);
        Board solution = new Board(null, dots);
        solve(initialDot.getColor(), null, initialDot.getStart(), 0, solution);
        if(solution.matrix == null) return null;
        return solution;
    }

    private void solve(int color, Position prevPos, Position currentPos, int index, Board solution){
        calls++;
        if(matrix.length <= currentPos.row || currentPos.row < 0
           || matrix[0].length <= currentPos.col || currentPos.col < 0) return;

        int currentPosColor = this.matrix[currentPos.row][currentPos.col];
        if(color == currentPosColor){
            if(!currentPos.equals(dots.get(index).getStart())){
                if(currentPos.equals(dots.get(index).getEnd())){
                    if(dots.size() == index+1){
                        saveSolution(solution);		
                    }else{
                        Dot nextDot = dots.get(index+1);
                        solve(nextDot.getColor(), null, nextDot.getStart(), index+1, solution);
                    }
                }
                return;
            }
            if(prevPos != null){
               return;
            }
        }else if(currentPosColor!= -1) return;

        this.matrix[currentPos.row][currentPos.col] = color;
        Position nextPos;

        for(Direction d : Direction.values()){
            if( !(nextPos = currentPos.getPosition(d)).equals(prevPos))
                solve(color, currentPos, nextPos, index, solution);
        }
        this.matrix[currentPos.row][currentPos.col] = currentPosColor;
    }

    private void saveSolution(Board solution){
        if( (solution.matrix == null)
                || (solution.paintedCells() < this.paintedCells()) ){
           this.copyMatrix(solution);
        }
    }

    /**
     * @param board
     * @return a Board with it's matrix initialized with a copy of this Board's matrix
     */

    private void copyMatrix(Board board){
        int row, col;
        board.matrix = new int[matrix.length][matrix[0].length];
        for(row = 0; row < matrix.length; row++){
            for(col = 0; col < matrix[0].length; col++){
                board.matrix[row][col] = this.matrix[row][col];
            }
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
    
    public Board solveAprox(){
    	Board solution = new Board(null, dots);
    	findInitialSolution(dots.get(0).getColor(), null,dots.get(0).getStart(), 0, solution);
		return solution;
    }
    private void solveAprox(int color, Position prevPos, Position currentPos, int index, Board solution){
    	if(matrix.length <= currentPos.row || currentPos.row < 0
    	    || matrix[0].length <= currentPos.col || currentPos.col < 0) return;

    	int currentPosColor = this.matrix[currentPos.row][currentPos.col];
    	if(color == currentPosColor){
            if(!currentPos.equals(dots.get(index).getStart())){
                if(currentPos.equals(dots.get(index).getEnd())){
                    if(dots.size() == index+1&&solution!=null){
                        saveSolution(solution);
                        return;
                    }else{
                        Dot nextDot = dots.get(index+1);
                        solve(nextDot.getColor(), null, nextDot.getStart(), index+1, solution);
                    }
                }
                return;
            }
            if(prevPos != null){
               return;
            }
        }else if(currentPosColor!= -1) return;

        this.matrix[currentPos.row][currentPos.col] = color;
        Position nextPos;

        for(Direction d : Direction.values()){
            if( !(nextPos = currentPos.getPosition(d)).equals(prevPos))
                solve(color, currentPos, nextPos, index, solution);
        }
        this.matrix[currentPos.row][currentPos.col] = currentPosColor;
    }

    private void findInitialSolution(int color, Position prevPos, Position currentPos, int index, Board solution){
        if(matrix.length <= currentPos.row || currentPos.row < 0
                || matrix[0].length <= currentPos.col || currentPos.col < 0) return;

        int currentPosColor = this.matrix[currentPos.row][currentPos.col];
        if(color == currentPosColor){
            if(!currentPos.equals(dots.get(index).getStart())){
                if(currentPos.equals(dots.get(index).getEnd())){
                    if(dots.size() == index+1 && solution != null){
                        saveSolution(solution);
                        return;
                    }else{
                        Dot nextDot = dots.get(index+1);
                        solve(nextDot.getColor(), null, nextDot.getStart(), index+1, solution);
                    }
                }
                return;
            }
            if(prevPos != null){
                return;
            }
        }else if(currentPosColor != -1) return;

        this.matrix[currentPos.row][currentPos.col] = color;
        
        
        for(Position pos:getPositionsWithPriority(currentPos, dots.get(index).getEnd())){
        	if(!pos.equals(prevPos)){
        		solve(color,currentPos,pos,index,solution);
        	}
        }
        
        
        
       
        this.matrix[currentPos.row][currentPos.col] = currentPosColor;
    }
    
    
    public  Position[] getPositionsWithPriority(Position currentPos,Position finalPos){
    	Position[] positions = new Position[4];
        int horizontal = currentPos.col - finalPos.col;
        int vertical = currentPos.row - finalPos.row;

        if(horizontal > 0){
            positions[0] = currentPos.getPosition(Direction.LEFT);
            positions[3] = currentPos.getPosition(Direction.RIGHT);
        }else if(horizontal == 0){
            positions[2] = currentPos.getPosition(Direction.LEFT);
            positions[3] = currentPos.getPosition(Direction.RIGHT);
            if(vertical > 0){
                positions[0] = currentPos.getPosition(Direction.UP);
                positions[1] = currentPos.getPosition(Direction.DOWN);
            }else{
                positions[0] = currentPos.getPosition(Direction.DOWN);
                positions[1] = currentPos.getPosition(Direction.UP);
            }
        }else if(horizontal < 0){
            positions[0] = currentPos.getPosition(Direction.RIGHT);
            positions[3] = currentPos.getPosition(Direction.LEFT);
        }
        if(vertical >= 0){
            positions[1] = currentPos.getPosition(Direction.UP);
            positions[2] = currentPos.getPosition(Direction.DOWN);
        }else{
            positions[1] = currentPos.getPosition(Direction.DOWN);
            positions[2] = currentPos.getPosition(Direction.UP);
        }
    	return positions;
    }
}
