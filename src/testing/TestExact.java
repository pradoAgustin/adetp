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

    @Test
    public void crearTabla() throws Exception{
        Parser parser=new Parser();
        Chronometer chrono=new  Chronometer();

        String[] niveles = {//"3x3_1color.txt",
//                "3x3_3colores.txt",
//                "4x4_1color.txt",
//                "4x4_2colors.txt",
//                "4x4_3colors.txt",
//                "4x4_4colors.txt",
//                "5x5_1color.txt",
//                "5x5_2colors.txt",
//                "5x5_3colores.txt",
//                "5x5_4colores.txt",
//                "5x5_4colores_B.txt",
//                "6x6_4colores.txt",
//                "8x8_7colores.txt",
//                "8x8_8colores.txt",
//                "9x9_7colores.txt",
//                "9x9_9colores.txt",
//                "29x30_1color.txt",
//                "3x3_2colores.txt",
//                "7x7_5colores.txt",
//                "7x7_6colores.txt",
//                "9x9_10colores.txt",
//                "14x14_10colores.txt",
                "25x25_5colors.txt",
//                "6x6_5colors.txt",
//                "6x6_6colors.txt",
//                "8x8_1color.txt",
//                "8x8_2colors.txt",
//                "8x8_3colors.txt",
//                "10x10_4colores.txt"
        };
        System.out.println("       Mapa       " + "    tiempo(ms)    " + "    llamadas    ");
        for(String s: niveles){
            Board board = parser.parseLevel("ArchivosEntrada" + File.separator + s);
            chrono.start();
            Board boardSolution2=board.solve(null);
            chrono.stop();
            System.out.println(s + "        " + chrono.getElapsedTimeInMilisecs() + "         "+board.getCalls());
            System.out.println(boardSolution2);
        }
    }

	/*testeo del algoritmo exacto con el mapa 3x3_1color.txt, la solucion debe dar todo el mapa cubierto*/
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
   		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "3x3_1color.txt");
   		Board boardSolution2=board.solve(null);
   		
   		FlowJframe frame=new FlowJframe(boardSolution2);
   		frame.showBoard();
   		int cant=boardSolution2.unPaintedCells();
   		assertTrue(cant==0);/*se controla que efectivamente esten todos los lugares ocupados*/
   	}

    @Test
    public void testpaintedCells() throws Exception{
        Board board = (new Parser()).parseLevel("ArchivosEntrada" + File.separator + "3x3_3colores.txt");
        board = board.solve(null);
        System.out.println(board.unPaintedCells());
    }
}
