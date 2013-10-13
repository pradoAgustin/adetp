package testing;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import backend.*;

import org.junit.BeforeClass;
import org.junit.Test;

import frontEnd.FlowJframe;

public class TestExact {



	/*mapa a resolver se encuentra en /resources/level30png
	 * casilleros vacios que deberian quedar :0
	 */




	@Test
	public void testSolve1(){
        Board board;
        try{
		    board=(new Parser2()).levantarNivel("ArchivosEntrada"+File.separator+"test3x3.txt");
        }catch (Exception e){
            e.getMessage();
            return;
        }
		//FlowJframe frame=new FlowJframe(board);
		//frame.showBoard();
		//frame=new FlowJframe(board);
		//frame.showBoard();
		
		Board boardSolution=board.solve();
		
		int cant=boardSolution.unPaintedCells();
		
		//frame=new FlowJframe(boardSolution);frame.showBoard();/*se muestra el tablero al finalizar*/
		
		// assertTrue(cant==0);/*se controla que efectivamente esten todos los lugares ocupados*/
		System.out.println("cantidad que quedo libre:"+cant);
		
		System.out.println("fin");
        try{
            Thread.sleep(100000);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
	}
	
	
}
