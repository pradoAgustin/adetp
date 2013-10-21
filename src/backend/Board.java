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
    public Board solveAprox(Listener l,Chronometer chronometer){
    	Dot initialDot = dots.get(0);
    	Board solution = new Board(null, dots);
    	findInitialSolution(initialDot.getColor(), null,initialDot.getStart(), 0, solution,l);
       if(solution.unPaintedCells()==0){
    	   return solution;
       }
    	
    	
    	boolean wasChange=true;
    	do{
    		Board best;
    		best=new Board(null, dots);
//    		if(!wasChange){
//    			
//    			sortDots(initialDot);//funcion que hace un sort de la lista de colores
//    			
//    			
//    			//se llama a la funcion inicial, pero con la lista de colores mezclada para simular una solucion al azar.
//    			//De esta manera, se busca una solucion  trabajando con los colores en un orden al azar distinto del inicial
//    			findInitialSolution(initialDot.getColor(), null, initialDot.getStart(), 0, best, l);
//    		}
//    		else{
    			wasChange=false;
    			if(solution.matrix ==null)//es decir que no hay solucion 
    				return null;
    			 best=new Board(null, dots);
    			copyMatrix(best);
    			tryBestSolution(best,l);
    			if(best!=null && best.unPaintedCells()<solution.unPaintedCells()){
    				solution=best;
    				wasChange=true;
    			}
    		
    		//}
    		
        
    	}while(chronometer.isThereTimeRemaining());
    	return solution;
    }
    private void tryBestSolution(Board solution, Listener l)
    {
		for(Dot dot:dots){
		tryCycle(dot,solution,l);
			
		}
		
		
	}

	private void tryCycle(Dot dot,Board board,Listener l) {
		Position pos = dot.getStart();
		
		int x=dot.getStart().row;
		int y =dot.getStart().col;
		int [][] matrix=board.getIntBoard();
		
		if ((x+1)<matrix.length && matrix[x+1][y]==dot.getColor()||(x-1)>=0&& matrix[x-1][y]==dot.getColor()){
			tryCycleCols(dot.getColor(),x,y,matrix);//
			tryCycleFils(dot.getColor(),x,y,matrix);
			}
			  /*secci�n para imprimir con intervalos de a 100ms*/
	       /* if(l!=null) l.printToScreen();*/
		
		
		else if(((y-1)>=0&& matrix[x][y-1]==dot.getColor())||((y+1)<matrix[0].length && matrix[x][y+1]==dot.getColor())){
			tryCycleFils(dot.getColor(),x,y,matrix);//
			tryCycleCols(dot.getColor(),x,y,matrix);
			System.out.println("entro en ciclo filas");
		}
		
			
			
	
		
}

	private void tryCycleFils(int color, int fila, int col, int[][] matrix) {
		System.out.println("got here");
		boolean flag=false;
		int i=fila+1;
		int c=((col-1)>=0&& matrix[fila][col-1]==color)?col-1:col+1;
		while(i<matrix.length)
		{	if(matrix[i][col]==-1&& matrix[i][c]==-1)
		{	flag=true;
			matrix[i][col]=color;
			matrix[i][c]=color;
			i+=1;
		}
		else{
			break;
		}
		}
		
		if(!flag)
		{	i=fila-1;
			while(i<=0)
			{	if(matrix[i][col]==-1&& matrix[i][c]==-1)
			{	flag=true;
				matrix[i][c]=color;
				matrix[i][col]=color;
				i-=1;
			}
			else{
				return;
			}}
		}
		for(int h=0;h<matrix.length;h++)/*cableo una impresion de la matrix para probar que se cambiaron*/
		{
			for(int k=0;k<matrix[0].length;k++){
				System.out.print(matrix[h][k]);
			}
			System.out.println();
		}
			
			}
			
	
		
	

	private void tryCycleCols(int color, int fila, int col,int[][]matrix) /*trata de ciclar por columna*/
	{
		System.out.println("llega a tryCycle");
		int f=((fila-1)>=0&& matrix[fila-1][col]==color)?fila-1:fila+1;
		System.out.println("vALOR DE FILA");
		System.out.println(f);
		boolean flag=false;
		int i=col+1;
		while(i<matrix[0].length){	
		if((matrix[fila][i])==-1&&( matrix[f][i]==-1))
		{	flag=true;
			matrix[fila][i]=color;
			matrix[f][i]=color;
			i+=1;
		}
		else{
			i=matrix[0].length;
		}
		}
		
		if(!flag)
		{	i=col-1;
			while(i<=0){	
				if((matrix[fila][i]==-1)&& (matrix[f][i]==-1))
				{	flag=true;
				matrix[fila][i]=color;
				matrix[f][i]=color;
				i-=1;
			}
			else{
				return;
			}
				}
			
		
		}
	for(int h=0;h<matrix.length;h++)/*cableo una impresion de la matrix para probar que se cambiaron*/
	{
		for(int k=0;k<matrix[0].length;k++){
			System.out.print(matrix[h][k]);
		}
		System.out.println();
	}
		
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
