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
        if(solution.matrix ==null) 
        	return null;
        Board best=new Board(null, dots);
        copyMatrix(best);
        tryBestSolution(best,l);
        
    	return solution;
    }
    private void tryBestSolution(Board solution, Listener l)
    {
		for(Dot dot:dots){
			System.out.println("color"+dot.getColor());
		tryCycle(dot,solution,l);
		System.out.println("luego de trycle");
		for(int h=0;h<solution.getIntBoard().length;h++)
		{
			for(int k=0;k<solution.getIntBoard()[0].length;k++){
				System.out.print(solution.getIntBoard()[h][k]);
			}
			System.out.println();
		}
			
			}
			
		/*tryCycleRandom(dot,solution,l)*/	
		}
		
		

	private void tryCycle(Dot dot,Board board,Listener l) {
		Position pos = dot.getStart();
		
		int x=dot.getStart().row;
		int y =dot.getStart().col;
		int [][] matrix=board.getIntBoard();
		//for(int i=0;i<matrix.length*2;i++)   /*posible ciclo for para poder tirar random y no empezar a tratar de ciclar por el mismo punto siempre*/
		//{int ctr=0;
			/*int x=(int) (Math.random()*matrix.length);
			int y =(int) (Math.random()*matrix[0].length);
			ctr+=check(x+1,y,dot.getColor());
			ctr+=check(x-1,y,dot.getColor());
			ctr+=check(x,y-1,dot.getColor());
			ctr+=check(x,y+1,dot.getColor());
			System.out.println("este es el valor de ctr"+ctr);
			System.out.println("val de x"+x+"val de y "+y);*/
		//if(ctr==2){}
		
		if ((x+1)<matrix.length && matrix[x+1][y]==dot.getColor()||(x-1)>=0&& matrix[x-1][y]==dot.getColor()){
			if(tryCycleCols(dot.getColor(),x,y,board)){
				tryCycleFils(dot.getColor(),x,y,board);
			}
			
			}
			  /*secci�n para imprimir con intervalos de a 100ms*/
	       /* if(l!=null) l.printToScreen();*/
		
		
		else if(((y-1)>=0&& matrix[x][y-1]==dot.getColor())||((y+1)<matrix[0].length && matrix[x][y+1]==dot.getColor())){
			if(tryCycleFils(dot.getColor(),x,y,board)){
				tryCycleCols(dot.getColor(),x,y,board);
			}
		}
			}

	private int check(int x, int y, int color) {
		if(x>=0&&x<matrix.length && y>=0 && y<matrix[0].length){
			return (matrix[x][y]==color)?1:0;
		}
		return 0;
		
	}

	private boolean tryCycleFils(int color, int fila, int col, Board board) {
	
		boolean flag=false;
		int i=fila+1;
		int c=((col-1)>=0&& board.getIntBoard()[fila][col-1]==color)?col-1:col+1;
		while(i<board.getIntBoard().length)
		{		if(board.getIntBoard()[i][col]==-1&& board.getIntBoard()[i][c]==-1)
			{	flag=true;
				board.getIntBoard()[i][col]=color;
				board.getIntBoard()[i][c]=color;
				i+=1;	
			
			}
		else{
				break;
			}
		}
		
		if(!flag)
		{	i=fila-1;
		
			while(i>=0)
			{	System.out.println("valor de la fila i col c");
				System.out.println("fila"+i+"col"+c);
				
				
				if(board.getIntBoard()[i][col]==-1&& board.getIntBoard()[i][c]==-1)
				{	flag=true;
					board.getIntBoard()[i][c]=color;
					board.getIntBoard()[i][col]=color;
					i-=1;
				}
			else{
				break;
				}
			}
		}
			for(int h=0;h<board.getIntBoard().length;h++)/*cableo una impresion de la matrix para probar que se cambiaron*/
				{
				for(int k=0;k<board.getIntBoard()[0].length;k++){
				System.out.print(board.getIntBoard()[h][k]);
				}
				System.out.println();
				}
			System.out.println("valor del flag en ciclo fils"+flag);
		return flag;
			
			}
			
	
		
	

	private boolean tryCycleCols(int color, int fila, int col,Board board) /*trata de ciclar por columna*/
	{
		System.out.println("llega a tryCycle");
		int f=((fila-1)>=0&& board.getIntBoard()[fila-1][col]==color)?fila-1:fila+1;
		boolean flag=false;
		int i=col+1;
		while(i<board.getIntBoard()[0].length){	
		if((board.getIntBoard()[fila][i])==-1&&( board.getIntBoard()[f][i]==-1))
		{	flag=true;
			board.getIntBoard()[fila][i]=color;
			board.getIntBoard()[f][i]=color;
			i+=1;
		}
		else{
			break;
		}
		}
		
		if(!flag)
		{	i=col-1;
			while(i<=0){	
				if((board.getIntBoard()[fila][i]==-1)&& (board.getIntBoard()[f][i]==-1))
				{	flag=true;
				board.getIntBoard()[fila][i]=color;
				board.getIntBoard()[f][i]=color;
				i-=1;
			}
			else{
				break;
			}
				}
			
		
		}
		System.out.println("valor del flag en ciclo cols"+flag);
	for(int h=0;h<board.getIntBoard().length;h++)/*cableo una impresion de la matrix para probar que se cambiaron*/
	{
		for(int k=0;k<board.getIntBoard()[0].length;k++){
			System.out.print(matrix[h][k]);
		}
		System.out.println();
	}
	return flag;
		
		}
		
		
		
		
		
		
			
			
			/*for(;nfila<=matrix.length&&fila<matrix.length &&col<matrix[0].length&&ncol<matrix[0].length;){{
					if((matrix[fila][col]==-1||matrix[fila][col]==color)&&(matrix[fila][col]==-1||matrix[fila][col]==color)){
						matrix[fila][col]=color;
						matrix[nfila][ncol]=color;
						col+=incrcol;
						System.out.println("llegue trycycle");
						
					}
					return;
				}*/
				
		
			
		

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
