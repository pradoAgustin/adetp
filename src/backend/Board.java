package backend;
import java.util.ArrayList;

public class Board {
    private final static Direction[][] optimalDir = new Direction[][]{
            {Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT},
            {Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN},
            {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT},
            {Direction.LEFT, Direction.UP, Direction.DOWN, Direction.RIGHT},
            {Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT},
            {Direction.DOWN, Direction.LEFT, Direction.UP, Direction.RIGHT},
            {Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.UP},
            {Direction.DOWN, Direction.RIGHT, Direction.UP, Direction.LEFT}
    };
	private int matrix[][];
	private ArrayList<Dot> dots = new ArrayList<Dot>();
    private long calls = 0;
	
	public int[][] getIntBoard(){
		return matrix;
	}

    public long getCalls(){
        return calls;
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
		return (matrix!=null)?(matrix[0].length):0;
	}
	
	public int rowsSize(){
		return (matrix!=null)?matrix.length:0;
	}

    public Board(int[][] matrix, ArrayList<Dot> dots) {
		this.matrix = matrix;
		this.dots = dots;
	}

    /**
     * @return a Board with the matrix containing the solution if there is one,
     *         null otherwise
     */
	public Board solve(Listener listener){
        Dot initialDot = dots.get(0);
        Board solution = new Board(null, dots);
        solve(initialDot.getColor(), null, initialDot.getStart(), 0, solution,listener);
        if(solution.matrix == null) return null;
        return solution;
    }

    private void solve(int color, Position prevPos, Position currentPos, int index, Board solution,Listener listener){
        calls++;
        if(matrix.length <= currentPos.row || currentPos.row < 0
           || matrix[0].length <= currentPos.col || currentPos.col < 0) return;

        int currentPosColor = this.matrix[currentPos.row][currentPos.col];//color original de la celda
        if(color == currentPosColor){
            if(!currentPos.equals(dots.get(index).getStart())){
                if(currentPos.equals(dots.get(index).getEnd())){
                    if(dots.size() == index+1){
                        saveSolution(solution);		
                    }else{
                        Dot nextDot = dots.get(index+1);
                        solve(nextDot.getColor(), null, nextDot.getStart(), index+1, solution,listener);
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

        /*secci�n para imprimir con intervalos de a 100ms*/
        if(listener!=null) listener.printToScreen();

        for(Direction d : Direction.values()){
            if( !(nextPos = currentPos.getPosition(d)).equals(prevPos))
                solve(color, currentPos, nextPos, index, solution,listener);
        }
        this.matrix[currentPos.row][currentPos.col] = currentPosColor;
    }

    private void saveSolution(Board solution){
        if( (solution.matrix==null)
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
    /*Algoritmo basado en Hill Climbing */
    public Board solveAprox(Listener l){
    	Dot initialDot = dots.get(0);
    	Board solution = new Board(null, dots);
    	findInitialSolution(initialDot.getColor(), null,initialDot.getStart(), 0, solution,l);
        if(solution.matrix ==  null) return null;
        Board best=new Board(null, dots);
        copyMatrix(best);
        tryBestSolution(best,l);
        
    	return solution;
    }
    private void tryBestSolution(Board solution, Listener l) {
		for(Dot dot:dots){
		tryCycle(dot,solution,l);
			
		}
		
	}

	private void tryCycle(Dot dot,Board board,Listener l) {
		Position pos = dot.getStart();
		pos.getPosition(Direction.DOWN);
		int x=dot.getStart().row;
		int y =dot.getStart().col;
		int [][] matrix=board.getIntBoard();
		if ((x+1)<=matrix.length&& matrix[x+1][y]==dot.getColor()){
			tryCycle(dot.getColor(),x,y,x+1,y,matrix,0,1);
			  /*secci�n para imprimir con intervalos de a 100ms*/
	        if(l!=null) l.printToScreen();
			
		}
		
	}

	private void tryCycle(int color, int fila, int col, int nfila , int ncol,
			int[][] matrix,int incrfila,int incrcol) {
			for(;nfila<=matrix.length&&fila<matrix.length &&col<matrix[0].length&&ncol<matrix[0].length;){{
					if((matrix[fila][col]==-1||matrix[fila][col]==color)&&(matrix[fila][col]==-1||matrix[fila][col]==color)){
						matrix[fila][col]=color;
						matrix[nfila][ncol]=color;
						col+=incrcol;
						System.out.println("llegue trycycle");
						
					}
					return;
					
				}
				
			}
			
		
	}

	/*
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
                        solveAprox(nextDot.getColor(), null, nextDot.getStart(), index+1, solution);
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
                solveAprox(color, currentPos, nextPos, index, solution);
        }
        this.matrix[currentPos.row][currentPos.col] = currentPosColor;
    }
*/
    private boolean findInitialSolution(int color, Position prevPos, Position currentPos, int index, Board solution,Listener l){
        if(matrix.length <= currentPos.row || currentPos.row < 0
                || matrix[0].length <= currentPos.col || currentPos.col < 0) return false;

        int currentPosColor = this.matrix[currentPos.row][currentPos.col];

        if(color == currentPosColor){
            if(!currentPos.equals(dots.get(index).getStart())){
                if(currentPos.equals(dots.get(index).getEnd())){
                    if(dots.size() == index+1 && solution != null){
                        saveSolution(solution);System.out.println("entro en el save solugion");
                        return true;
                    }else{
                        Dot nextDot = dots.get(index+1);
                        findInitialSolution(nextDot.getColor(), null, nextDot.getStart(), index+1, solution,l);
                    }
                }
                return false;
            }
            if(prevPos != null){
                return false;
            }
        }else if(currentPosColor != -1) return false;

        this.matrix[currentPos.row][currentPos.col] = color;

        Direction[] dir = optimalDir[getOptimalDirIndex(
                currentPos.col - dots.get(index).getEnd().col,currentPos.row - dots.get(index).getEnd().row)];
        Position nextPos;
        
        /*secci�n para imprimir con intervalos de a 100ms*/
        if(l!=null){
        	l.printToScreen();
               }

        for(int i = 0; i < 4; i++){
            if( !(nextPos = currentPos.getPosition(dir[i])).equals(prevPos)){
                if(findInitialSolution(color,currentPos,nextPos,index,solution,l))
                    return true;
            }
        }
        this.matrix[currentPos.row][currentPos.col] = currentPosColor;
        return false;
    }
    
    
    
    public  Position[] getPositionsWithPriority(Position currentPos,Position finalPos){
    	
    	
    	Position[] positions = new Position[4];
    	int c=finalPos.col-finalPos.col;
    	int f=finalPos.row-currentPos.row;
    	
    	
    	
        int horizontal = currentPos.col - finalPos.col;
        int vertical = currentPos.row - finalPos.row;

        if(horizontal >= 0){
            positions[0] = currentPos.getPosition(Direction.LEFT);
            positions[3] = currentPos.getPosition(Direction.RIGHT);
            }
            else
          {   positions[0] = currentPos.getPosition(Direction.RIGHT);
            positions[3] = currentPos.getPosition(Direction.LEFT);
            }
            if(vertical >= 0){
                positions[1] = currentPos.getPosition(Direction.UP);
                positions[2] = currentPos.getPosition(Direction.DOWN);
            }else{
                positions[1] = currentPos.getPosition(Direction.DOWN);
                positions[2] = currentPos.getPosition(Direction.UP);
           }
        
       for(int i=0;i<positions.length;i++){
        	System.out.print(positions[i]);
        }
       System.out.println();
        
    	return positions;
    }

    private int getOptimalDirIndex(int horizontal, int vertical){
        if(horizontal > 0){
            if(vertical > 0){
                return 0;
            }else if(vertical < 0){
                return 5;
            }else{
                return 3;
            }
        }else if(horizontal == 0){
            if(vertical > 0){
                return 1;
            }else{
                return 7;
            }
        }else{
            if(vertical > 0){
                return 2;
            }else if(vertical == 0){
                return 4;
            }else{
                return 7;
            }
        }

    }

	public void addDot(Dot dot) {
		dots.add(dot);
	}
}
