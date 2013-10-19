package testing;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import backend.Board;
import backend.Parser;
import backend.Position;
import frontEnd.FlowJframe;

public class testaprox{
	
	
	
	/* se prueba el metodo getPositionsWithPriority en el caso de que el siguiente movimiento de prioridad sea en forma horizontal izquierda*/
	@Test
	public void testGetPositionWithPriority(){
		int[][]m=new int[6][6];
		Board b=new Board(m,null);
		
		Position currentPos=new Position(5, 0);
		Position finalPos=new Position(5, 5);
		Position[] ans=b.getPositionsWithPriority(currentPos, finalPos);
		assertTrue(ans[0].equals(new Position(5, 1)));
	}
	/* se prueba el metodo getPositionsWithPriority en el caso de que el siguiente movimiento de prioridad sea en forma horizontal derecha*/
	@Test
	public void testGetPositionWithPriority2(){
		int[][]m=new int[6][6];
		Board b=new Board(m,null);
		
		Position currentPos=new Position(5, 5);
		Position finalPos=new Position(5, 0);
		Position[] ans=b.getPositionsWithPriority(currentPos, finalPos);
		assertTrue(ans[0].equals(new Position(5, 4)));
	}
	/* se prueba el metodo getPositionsWithPriority en el caso de que el siguiente movimiento de prioridad sea en forma vertical abajo*/
	@Test
	public void testGetPositionWithPriority3(){
		int[][]m=new int[6][6];
		Board b=new Board(m,null);
		
		Position currentPos=new Position(0, 0);
		Position finalPos=new Position(5,0);
		Position[] ans=b.getPositionsWithPriority(currentPos, finalPos);
		System.out.println(ans[0]);
		System.out.println(ans[1]);
		System.out.println(ans[2]);
		System.out.println(ans[3]);
		assertTrue(ans[0].equals(new Position(1, 0)));
	}
	/* se prueba el metodo getPositionsWithPriority en el caso de que el siguiente movimiento de prioridad sea en forma diagonal abajo*/
	@Test
	public void testGetPositionWithPriority5(){
		int[][]m=new int[6][6];
		Board b=new Board(m,null);
		
		Position currentPos=new Position(0, 0);
		Position finalPos=new Position(3, 2);
		Position[] ans=b.getPositionsWithPriority(currentPos, finalPos);
		assertTrue(ans[0].equals(new Position(1, 0))||ans[0].equals(new Position(0, 1)) );
	}
	
	@Test
	public void testSolve2() throws Exception{
		Parser parser=new Parser();
		Board board=parser.levantarNivel("ArchivosEntrada"+File.separator+"test3x3.txt");
		Board boardSolution2=board.solveAprox();
		//FlowJframe frame=new FlowJframe(boardSolution2);
		//frame.showBoard();
		int cant=boardSolution2.unPaintedCells();
		assertTrue(boardSolution2!=null && boardSolution2.colsSize()>0);/*se controla la matriz del tablero solucion no este vacia*/
		assertTrue(cant<9);
	}
	
	@Test
	public void testSolve3() throws Exception{
		System.out.println("este es el test3");
		Parser parser=new Parser();
		Board board=parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoEnunciado.txt");
		Board boardSolution2=board.solveAprox();
		FlowJframe frame=new FlowJframe(boardSolution2);
		frame.showBoard();
		assertTrue(boardSolution2!=null);
		assertTrue(boardSolution2.getIntBoard()!=null);
		assertTrue( boardSolution2.rowsSize()>0 && boardSolution2.colsSize()>0);/* se comprueba que se grabo correctamente la matriz solucion del Board*/
		int cant=boardSolution2.unPaintedCells();
		assertTrue(cant<6);
	}
}
