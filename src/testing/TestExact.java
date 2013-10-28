package testing;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import backend.*;

import org.junit.Test;

import frontEnd.FlowJframe;

public class TestExact {
	/*mapa a resolver se encuentra resuelto  en /resources/level30png
	 * casilleros vacios que deberian quedar :0
	 */


	/*testeo del algoritmo exacto con el mapa test3x3.txt, la solucion debe dar todo el mapa cubierto*/
	@Test
	public void testSolve2() throws Exception{
		Parser parser=new Parser();
		Chronometer chrono=new  Chronometer();
		chrono.start();
		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "level8*8B.txt");
		chrono.stop();
		System.out.println("crhono"+chrono.getElapsedTimeInMilisecs());
		FlowJframe frame=new FlowJframe(board);
		frame.showBoard();
		//Board boardSolution2=board.solve(new PrintListener(frame));/* para ver la opcion "progress"*/
		Board boardSolution2=board.solve(null);
		frame=new FlowJframe(boardSolution2);
		frame.showBoard();
		int cant=boardSolution2.unPaintedCells();
		assertTrue(boardSolution2.rowsSize()>0);
		assertTrue(cant==0);/*se controla que efectivamente esten todos los lugares ocupados*/
	}
    @Test
    public void unsolvableLevelTest() throws Exception{
        Parser parser = new Parser();
        Board solution = parser.parseLevel("ArchivosEntrada" + File.separator + "withoutSolution.txt").solve(null);
        
        if(solution != null){
            FlowJframe frame = new FlowJframe(solution);
            frame.showBoard();
        }else{
            System.out.println("No tiene solucion");
        }
    }
    @Test
	public void testSolve3() throws Exception{
		Parser parser=new Parser();
		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "ArchivoEnunciado.txt");
		Chronometer chrono=new Chronometer();
		chrono.start();
		
		Board boardSolution2=board.solve(null);
		chrono.stop();System.out.println(chrono.getElapsedTimeInMilisecs());
		
		/*FlowJframe frame=new FlowJframe(boardSolution2);
		frame.showBoard();
		int cant=boardSolution2.unPaintedCells();
		assertTrue(cant==0);/*se controla que efectivamente esten todos los lugares ocupados*/
	}
    @Test
   	public void testSolve4() throws Exception{
   		Parser parser=new Parser();
   		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "test3x3.txt");
   		Board boardSolution2=board.solve(null);
   		
   		FlowJframe frame=new FlowJframe(boardSolution2);
   		frame.showBoard();
   		int cant=boardSolution2.unPaintedCells();
   		assertTrue(cant==0);/*se controla que efectivamente esten todos los lugares ocupados*/
   	}

    @Test
    public void testpaintedCells() throws Exception{
        Board board = (new Parser()).parseLevel("ArchivosEntrada" + File.separator + "3x3ConDots.txt");
        board = board.solve(null);
        System.out.println(board.unPaintedCells());
    }

    @Test
    public void testIvana() throws Exception{
        Parser parser=new Parser();
        Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "level5*5Bj.txt");
        Chronometer chrono=new Chronometer();
        chrono.start();

        Board boardSolution2=board.solve(null);
        chrono.stop();System.out.println("tiempo tardado"+chrono.getElapsedTimeInMilisecs());

        FlowJframe frame=new FlowJframe(boardSolution2);
        frame.showBoard();
        int cant=boardSolution2.unPaintedCells();
        assertTrue(cant==0);/*se controla que efectivamente esten todos los lugares ocupados*/
    }

}
