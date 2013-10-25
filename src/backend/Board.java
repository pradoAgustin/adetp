package backend;
//import sun.util.resources.CurrencyNames_es_HN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    /* Array con las direcciones en el orden óptimo precalculadas, para usar
       en la solución aproximada a la hora de buscar una solución inicial */
    private final static Direction[][] optimalDir = new Direction[][]{
    /* 0 */ {Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT},
    /* 1 */ {Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN},
    /* 2 */ {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT},
    /* 3 */ {Direction.LEFT, Direction.UP, Direction.DOWN, Direction.RIGHT},
    /* 4 */ {Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT},
    /* 5 */ {Direction.DOWN, Direction.LEFT, Direction.UP, Direction.RIGHT},
    /* 6 */ {Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.UP},
    /* 7 */ {Direction.DOWN, Direction.RIGHT, Direction.UP, Direction.LEFT}
    };

    /* Constante T de probabilidad para el algoritmo aproximádo
       hill-climber estocástico */
    private final static double T = 0.1;
 
	private Cell matrix[][];
	private ArrayList<Dot> dots = new ArrayList<Dot>();
    private long calls = 0;
    private int paintedCells = 0;

    public Board(Cell[][] matrix, ArrayList<Dot> dots) {
        this.matrix = matrix;
        this.dots = dots;
    }

	public Cell[][] getIntBoard(){
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
		return matrix != null ? matrix[0].length : 0;
	}
	
	public int rowsSize(){
		return matrix != null ? matrix.length : 0;
	}

    /**
     * @return una instancia de Board con la variable de instancia matrix
     *         conteniendo la solución si es que había una, null en caso
     *         contrario
     */
	public Board solve(Listener listener){
        Dot initialDot = dots.get(0);
        Board solution = new Board(null, dots);
        solve(initialDot.getColor(), null, initialDot.getStart(), 0, solution,listener);
        return solution.matrix == null ? null : solution;
    }

    /**
     *
     * @param color
     * @param prevPos
     * @param currentPos
     * @param index
     * @param solution
     * @param listener
     * @return true si la solución cubre el tablero completo, false en caso contrario
     */
    private boolean solve(int color, Position prevPos, Position currentPos, int index, Board solution,Listener listener){
        calls++;
        if(matrix.length <= currentPos.row || currentPos.row < 0
           || matrix[0].length <= currentPos.col || currentPos.col < 0) return false;

        int currentPosColor = this.matrix[currentPos.row][currentPos.col].color; //color original de la celda
        if(color == currentPosColor){
            if(!currentPos.equals(dots.get(index).getStart())){
                if(currentPos.equals(dots.get(index).getEnd())){
                    if(dots.size() == index+1){
                        saveSolution(solution);
                        if(solution.unPaintedCells() == 0) return true;
                    }else{
                        Dot nextDot = dots.get(index+1);
                        solve(nextDot.getColor(), null, nextDot.getStart(), index+1, solution,listener);
                    }
                }
                return false;
            }
            if(prevPos != null){
               return false;
            }
        }else if(currentPosColor!= -1) return false;

        this.matrix[currentPos.row][currentPos.col].color = color;
        Position nextPos;

        /*secci�n para imprimir con intervalos de a 100ms*/
        if(listener!=null) listener.printToScreen();

        for(Direction d : Direction.values()){
            if( !(nextPos = currentPos.getPosition(d)).equals(prevPos) ){
                if(solve(color, currentPos, nextPos, index, solution,listener)) return true;
            }
        }
        this.matrix[currentPos.row][currentPos.col].color = currentPosColor;
        return false;
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
        board.matrix = new Cell[matrix.length][matrix[0].length];
        for(row = 0; row < matrix.length; row++){
            for(col = 0; col < matrix[0].length; col++){
                Cell c = this.matrix[row][col];
                board.matrix[row][col] = new Cell(c.color,c.nextPathDir);
            }
        }
    }

    private int paintedCells(){
        if(this.paintedCells != 0) return this.paintedCells;

        int cellsPainted = 0;
        for(int row = 0; row < matrix.length; row++){
            for(int col = 0; col < matrix[0].length; col++){
                if(matrix[row][col].color != -1)           /* -1 era la marca para espacio no? */
                    cellsPainted++;
            }
        }
        return cellsPainted;
    }

    public int unPaintedCells(){
    	return colsSize()*rowsSize()-paintedCells();
    }
    
    /*Algoritmo basado en Hill Climbing  ,
     * empieza por un color y evalua sus vecinos,si la solucion es mejor la acepta y sino sigue buscando otra mejor
     * hasta que tenga tiempo
     */
    public Board solveAprox(Listener l,Chronometer chronometer){
    	
        Board copy = new Board(null, dots);
        copyMatrix(copy);
        Board solution = new Board(null, dots);
        Board bestSolution = null;
        chronometer.start();
        System.out.println("buscando mejorar solucion");
        while(chronometer.isThereTimeRemaining()){
            Dot initialDot = dots.get(0);
            if(findInitialSolution(initialDot.getColor(), null, initialDot.getStart(), 0, copy, solution, l))
            	System.out.println(("encontre solucion aprox"));
            if(solution == null) return null; 
            improveSolution(solution, l);
            if(bestSolution == null ||  bestSolution.paintedCells() < solution.paintedCells()){
                bestSolution = solution;
            }
            Collections.shuffle(dots); // randomizar orden de colores para escapar al máximo local
        }
        System.out.println("la mejor mejora fue");
        for(int i=0;i<solution.matrix.length;i++){
        	for(int j=0;j<solution.matrix[0].length;j++){
        		System.out.print(solution.matrix[i][j].color);
        	}System.out.println();
        }
        return solution;
    }

    public Cell at(Position pos){
        return this.matrix[pos.row][pos.col];
    }

    public void improveSolution(Board solution, Listener l){
        Change change=null;
        int previousPaintedCells;
        do{
            previousPaintedCells = solution.paintedCells();
            for(Dot dot: dots){
                Position currentPos = dot.getStart();
                Cell currentCell = solution.matrix[currentPos.row][currentPos.col];
                Position aux1, aux2;
                Direction currentDir = currentCell.nextPathDir;
                if(currentDir==null)System.out.println("no estoy guardando los dir");
                int paintedCells = solution.paintedCells;
                while(currentDir != null){
                	System.out.println("chequeo current dir");
                    switch(currentDir){
       
                        case UP:    if((solution.at(aux1 = currentPos.getPosition(Direction.LEFT)).color == -1 &&
                                    (solution.at(aux2 = currentPos.getPosition(Direction.UPPERLEFT)).color == -1))){
                        				System.out.println("case up");
                                        change = new Change(currentPos,aux1,aux2,Direction.LEFT,Direction.UP,Direction.RIGHT,0,dot.getColor());
                                    }else if((solution.at(aux1 =currentPos.getPosition(Direction.RIGHT))).color == -1 &&
                                    ((solution.at(aux2 =currentPos.getPosition(Direction.UPPERRIGHT))).color == -1)){
                                    	System.out.println("case up");
                                        change = new Change(currentPos,aux1,aux2,Direction.RIGHT,Direction.UP,Direction.LEFT,0,dot.getColor());
                                    }
                                    break;
                        case DOWN:  if((solution.at(aux1=currentPos.getPosition(Direction.LEFT))).color == -1 &&
                                  (solution.at(aux2 =currentPos.getPosition(Direction.LOWERLEFT))).color == -1){
                        	
                                        change = new Change(currentPos,aux1,aux2,Direction.LEFT,Direction.UP,Direction.RIGHT,0,dot.getColor());
                                    }else if((solution.at(aux1 =currentPos.getPosition(Direction.RIGHT))).color == -1 &&
                                            (solution.at(aux2=currentPos.getPosition(Direction.LOWERRIGHT))).color == -1){
                                        change = new Change(currentPos,aux1,aux2,Direction.RIGHT,Direction.DOWN,Direction.LEFT,0,dot.getColor());
                                    }System.out.println("case down");
                                    break;
                        case LEFT:  if(((solution.at(aux1 =currentPos.getPosition(Direction.UP))).color == -1 &&
                                (solution.at(aux2=currentPos.getPosition(Direction.UPPERLEFT))).color == -1)){
                                        change = new Change(currentPos,aux1,aux2,Direction.UP,Direction.LEFT,Direction.DOWN,0,dot.getColor());
                                    } else if((solution.at(aux1 =currentPos.getPosition(Direction.DOWN))).color == -1 &&
                                              (solution.at(aux2 =currentPos.getPosition(Direction.LOWERLEFT))).color == -1){
                                        change = new Change(currentPos,aux1,aux2,Direction.DOWN,Direction.LEFT,Direction.UP,0,dot.getColor());
                                    }
                        			System.out.println("case left");
                                    break;
                        case RIGHT: if((solution.at(aux1 =currentPos.getPosition(Direction.UP))).color == -1 &&
                                (solution.at(aux2=currentPos.getPosition(Direction.UPPERRIGHT))).color == -1){
                                        change = new Change(currentPos,aux1,aux2,Direction.UP,Direction.RIGHT,Direction.DOWN,0,dot.getColor());
                                    }else if((solution.at(aux1 =currentPos.getPosition(Direction.DOWN))).color == -1 &&
                                              (solution.at(aux2 =currentPos.getPosition(Direction.LOWERRIGHT))).color == -1){
                                        change = new Change(currentPos,aux1,aux2,Direction.DOWN,Direction.RIGHT,Direction.UP,0,dot.getColor());
                                    }
                        			System.out.println("case right");
                                    break;
                        default:    change = null;
                    }
                    currentPos = currentPos.getPosition(solution.at(currentPos).nextPathDir);
                    currentDir = solution.at(currentPos).nextPathDir;
                    if(change != null && Math.random() < 1/(1+ Math.pow(Math.E, (double)(paintedCells - change.paintedcells)/T))){
                    	System.out.println("se hizo la potencia");
                        break;
                    }
                }
                if(change != null) solution.applyChanges(change);
            }
        }while(previousPaintedCells < solution.paintedCells());
    }


    public Cell[][] getBoardMatrix(){
    	return matrix;
    }

    private boolean findInitialSolution(int color, Position prevPos, Position currentPos, int index, Board boardCopy, Board solution,Listener l){
        Cell[][] cpMatrix = boardCopy.matrix;
        if(cpMatrix.length <= currentPos.row || currentPos.row < 0
           || cpMatrix[0].length <= currentPos.col || currentPos.col < 0) return false;

        int currentPosColor = cpMatrix[currentPos.row][currentPos.col].color;

        if(color == currentPosColor){
            if(!currentPos.equals(dots.get(index).getStart())){
                if(currentPos.equals(dots.get(index).getEnd())){
                    if(dots.size() == index+1){
                        saveSolution(solution);
                        return true;
                    }else{
                        Dot nextDot = dots.get(index+1);
                        findInitialSolution(nextDot.getColor(), null, nextDot.getStart(), index+1, boardCopy, solution,l);
                    }
                }
                return false;
            }
            if(prevPos != null){
                return false;
            }
        }else if(currentPosColor != -1) return false;
        cpMatrix[currentPos.row][currentPos.col].color = color;

        /*secci�n para imprimir con intervalos de a 100ms*/
        if(l!=null)	l.printToScreen();

        Direction[] dir = getOptimalDirArray(currentPos, dots.get(index).getEnd());
        Direction prevDir;
        Position nextPos;
        for(int i = 0; i < 4; i++){
            if( !(nextPos = currentPos.getPosition(dir[i])).equals(prevPos)){
                prevDir = boardCopy.at(currentPos).nextPathDir;
                boardCopy.at(currentPos).nextPathDir = dir[i];
                if(findInitialSolution(color,currentPos,nextPos,index,boardCopy,solution,l)) return true;
                boardCopy.at(currentPos).nextPathDir = prevDir;
            }
        }
        boardCopy.matrix[currentPos.row][currentPos.col].color = currentPosColor;
        return false;
    }
    
    /**
     * Siendo C el "current point", hay 8 posibles posiciones diferentes
     * en las que el destino puede encontrarse, numeradas desde el extremo
     * izquierdo superior al derecho inferior.
     * --------------------
     * | >>     =>     <> |
     * |                  |
     * | >=     C      <= |
     * |                  |
     * | ><     =<     << |
     * --------------------
     * @param from Posición en la que se encuentra el algoritmo
     * @param to Posición a la que busca llegar el algoritmo
     * @return Arreglo de direcciones óptimas que corresponda
     */
    private Direction[] getOptimalDirArray(Position from, Position to){
        int horizontal = from.col - to.col;
        int vertical = from.row - to.row;
        if(horizontal > 0)
            return (vertical > 0) ? optimalDir[0] : (vertical < 0) ? optimalDir[5] : optimalDir[3];
        if(horizontal == 0)
            return (vertical > 0) ? optimalDir[1]: optimalDir[6];
        return (vertical > 0) ? optimalDir[2] : vertical == 0 ? optimalDir[4] : optimalDir[7];
    }

	public void addDot(Dot dot) {
		dots.add(dot);
	}

    public static void main(String args[]){
        if(args.length < 2){
            System.out.println("Arguments are lacking");
            return;
        }
        Board board;
        try{
            board = (new Parser()).parseLevel(args[0].toString());
        }catch(Exception e){
            e.getMessage();
            return;
        }

        if(args[1].equals("exact")){
            if(args.length > 2 && args[2].equals("progress")){
                board.solve(null /*reemplazar por listener*/);
            }else{
                board.solve(null);
            }
            return;
        }else if(args[1].equals("approx")){
            if(args.length > 2){
                if(args.length > 3 && args[2].equals("progress")){
                    board.solveAprox(null, null /*reemplazar por valores!*/);
                    return;
                }
                board.solveAprox(null, null /*reemplazar por valores!*/);
                return;
            }
            board.solveAprox(null, null);
        }else{
            throw new IllegalArgumentException();
        }
    }
    
    
    private void applyChanges(Change change){
    	this.at(change.pos1).color=change.color;
    	this.at(change.pos2).color=change.color;
    	this.at(change.origin).nextPathDir = change.d0;
    	this.at(change.pos1).nextPathDir=change.d1;
    	this.at(change.pos2).nextPathDir=change.d2;
    	this.paintedCells=change.paintedcells;
    }
   

    private class Change {
    	int paintedcells;
    	int color;
    	Position origin;
    	Position pos1;
    	Direction d0;
    	Direction d1;
    	Direction d2;
    	Position pos2;
    	Change(Position origin,Position c1,Position c2,Direction d0, Direction d1,Direction d2,int pc,int color){
    		this.origin=origin;
    		this.d0=d0;
    		this.pos1=c1;
    		this.d1=d1;
    		this.pos2=c2;
    		this.d2=d2;
    		paintedcells=pc;
    		this.color=color;
    	}
    	
    	

    }

    
    
}
