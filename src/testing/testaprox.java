package testing;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import backend.Board;
import backend.Parser;
import frontEnd.FlowJframe;

public class testaprox{
	@Test
	public void testSolve2() throws Exception{
		Parser parser=new Parser();
		Board board=parser.levantarNivel("ArchivosEntrada"+File.separator+"test3x3.txt");
		Board boardSolution2=board.solveAprox();
		
		FlowJframe frame=new FlowJframe(boardSolution2);
		frame.showBoard();
		int cant=boardSolution2.unPaintedCells();
		assertTrue(cant==0);/*se controla que efectivamente esten todos los lugares ocupados*/
	}
}
