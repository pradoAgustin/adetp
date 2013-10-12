package testing;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import org.junit.BeforeClass;
import org.junit.Test;

import frontEnd.FlowJframe;

import backend.Board;
import backend.Color;
import backend.Dot;
import backend.Position;

public class TestExact {
	
	

	/*mapa a resolver se encuentra en /resources/level30png
	 * casilleros vacios que deberian quedar :0
	 */
	
	
	
	
	@Test
	public void testSolve1(){
		int matrix[][]=new int[9][9];
		Board board=new Board(matrix);
		
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				matrix[i][j]=-1;
			}
		}
		FlowJframe frame=new FlowJframe(board);
		frame.showBoard();
		matrix[0][6]=Color.RED.getNum(); 	matrix[2][3]=Color.RED.getNum();board.addDot(new Dot(new Position(0, 6), new Position(2, 3), backend.Color.RED.getNum()));
		matrix[0][7]=Color.GRAY.getNum(); 	matrix[3][3]=Color.GRAY.getNum();board.addDot(new Dot(new Position(0,7 ), new Position(3,3 ), backend.Color.GRAY.getNum()));
		matrix[0][8]=Color.GREEN.getNum(); 	matrix[4][1]=Color.GREEN.getNum();board.addDot(new Dot(new Position(0,8 ), new Position(4,1 ), backend.Color.GREEN.getNum()));
		matrix[1][1]=Color.BLUE.getNum(); 	matrix[8][0]=Color.BLUE.getNum();board.addDot(new Dot(new Position(1,1 ), new Position(8,0 ), backend.Color.BLUE.getNum()));
		
		matrix[2][0]=Color.BLACK.getNum(); 	matrix[7][3]=Color.BLACK.getNum();board.addDot(new Dot(new Position(2,0 ), new Position(7, 3), backend.Color.BLACK.getNum()));
		matrix[4][7]=Color.ORANGE.getNum(); 	matrix[7][7]=Color.ORANGE.getNum();board.addDot(new Dot(new Position(4,7 ), new Position(7,7 ), backend.Color.ORANGE.getNum()));
		matrix[5][4]=Color.PINK.getNum(); 	matrix[7][6]=Color.PINK.getNum();board.addDot(new Dot(new Position(5,4 ), new Position(7,6 ), backend.Color.PINK.getNum()));
		matrix[6][6]=Color.YELLOW.getNum(); 	matrix[6][8]=Color.YELLOW.getNum();board.addDot(new Dot(new Position(6,6 ), new Position(6,8 ), backend.Color.YELLOW.getNum()));
		
		matrix[8][5]=Color.MAGENTA.getNum(); 	matrix[7][8]=Color.MAGENTA.getNum();board.addDot(new Dot(new Position(8,5 ), new Position(7, 8), backend.Color.MAGENTA.getNum()));
	
		
		 frame=new FlowJframe(board);
		frame.showBoard();
		
		Board boardSolution=board.solve();
		
		int cant=boardSolution.unPaintedCells();
		
		frame=new FlowJframe(boardSolution);frame.showBoard();/*se muestra el tablero al finalizar*/
		
		assertTrue(cant==0);/*se controla que efectivamente esten todos los lugares ocupados*/
		System.out.println("cantidad que quedo libre:"+cant);
		
		System.out.println("fin");
	}
	
	
}
