package testing;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import backend.Board;
import backend.Chronometer;
import backend.Parser;
import frontEnd.FlowJframe;
import frontEnd.PrintListener;

public class testaprox{


    @Test
    public void crearTabla() throws Exception{
        Parser parser=new Parser();
        Chronometer chrono=new  Chronometer();

        String[] niveles = {"3x3_1color.txt", "8x8_1color.txt", "3x3_3colores.txt", "5x5_4colores.txt",
                "5x5_4colores_B.txt", "6x6_4colores.txt", "8x8_7colores.txt", "8x8_8colores.txt",
                "9x9_7colores.txt", "9x9_9colores.txt", "29x30_1color.txt", "3x3_2colores.txt",
                "7x7_5colores.txt", "7x7_6colores.txt", "9x9_10colores.txt", "14x14_10colores.txt",
                "8x8_2colors.txt", "8x8_3colors.txt"
        };
        System.out.println("       Mapa       " + "    tiempo(ms)    " + "    llamadas    ");
        for(String s: niveles){
            Board board = parser.parseLevel("ArchivosEntrada" + File.separator + s);
            chrono.start();
            Board boardSolution2 = board.solveAprox(null, chrono);
            chrono.stop();
            System.out.println(s + "        " + chrono.getElapsedTimeInMilisecs() + "         "+board.getCalls());
            System.out.println(boardSolution2);
        }
    }

/*test qeu permite comparar solucion aprox con exacta.
 * La solucion exacta se encuentra en resources/comparacion.png*/
	 @Test
	   	public void testCOMPARACION() throws Exception{
		 
		 	String fileName="ArchivosEntrada" + File.separator + "8x8_3colors.txt";
	   		Parser parser=new Parser();
	   		
	   		Board board=parser.parseLevel(fileName);
	   		FlowJframe frame=new FlowJframe(board);
	   		frame.showBoard();

	   		Chronometer chronometer=new Chronometer();chronometer.start();
	   		board=board.solve(null); 
	   		
	   		
//	   		/*sacar sleep para el mapa dificil*/
//	   		try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	   		chronometer.stop();
	   		//frame.changeBoard(board);
	   		//frame.showBoard();
	   		//frame=new FlowJframe(board);
	   		frame.showBoard();
	   		int cant=board.unPaintedCells();
	   		//int cant=0;
	   		
	   		long tiempo=chronometer.getElapsedTimeInSecs();
	   		//long tiempo=1509;
	   		System.out.println("solucion exacta encontrada en:"+tiempo+"segundos");
	   		System.out.println("cantidad minima de lugares libres:"+cant);
	   		
	   		System.out.println("Solucion aproximada");
	   		System.out.println("% de tiempo respecto exact--tiempo      --celdas libres-- diferencia de lugares libres con exacta");
	   		double[] porcentajesTiempos={0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1};
	   		
	   		for(double porcentaje:porcentajesTiempos){
	   			board=parser.parseLevel(fileName);
	   			double currentTime=((double)tiempo)*porcentaje;	/*limite de tiempo*/
	   			
	   		Board currentSol=board.solveAprox(null,new Chronometer((long) Math.abs(currentTime)));
	   			
	   			if(currentSol!=null){
	   				int currentFreeCels=currentSol.countFreecels();
	   				System.out.println(porcentaje+"                              "+currentTime+"          "+currentFreeCels+"                           "+(currentFreeCels-cant));
	   			
	   				frame.changeBoard(currentSol);
	   				frame.showBoard();
	   			}else{
	   				System.out.println("no se alcanzo alguna solucion");
	   			}
	   		}
	   		System.out.println("listo");
	   		
	 }
	
	 @Test
	   	public void testSolve5() throws Exception{
	   		Parser parser=new Parser();
	   		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "test3x3(2).txt");
	   		FlowJframe frame=new FlowJframe(board);
	   		frame.showBoard();

	   		board=board.solveAprox(new PrintListener(frame),new Chronometer(10));//puse un tiempo grande para testear
	   		if(board!=null){
	   		frame=new FlowJframe(board);
	   		frame.showBoard();
	   		int cant=board.countFreecels();
	   		assertTrue(cant>=0);
	   		assertTrue(cant==1);
	   		}
	   		
	   	}
	@Test
	public void testSolve2() throws Exception{
		Parser parser=new Parser();
		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "testf.txt");
		FlowJframe frame=new FlowJframe(board);
		frame.showBoard();
		 board=board.solveAprox(new PrintListener(frame),new Chronometer((int)(185.088*0.6)));
		 frame.changeBoard(board);
		 frame.showBoard();

		/*int cant=board.countFreecels();
		assertTrue(board!=null && board.colsSize()>0);/*se controla la matriz del tablero solucion no este vacia*/
		/*assertTrue(cant>=0);
		assertTrue(cant<9);*/
	}
	
	@Test
	public void testSolve3() throws Exception{
		Parser parser=new Parser();
		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "testf.txt");
		FlowJframe frame=new FlowJframe(board);
		frame.showBoard();
		Board boardSolution2=board.solveAprox(/*new PrintListener(frame)*/null,new Chronometer((int)(185.088*0.2)));
		frame=new FlowJframe(boardSolution2);
		frame.showBoard();
		assertTrue(boardSolution2!=null);
		assertTrue( boardSolution2.rowsSize()>0 && boardSolution2.colsSize()>0);/* se comprueba que se grabo correctamente la matriz solucion del Board*/
		int cant=boardSolution2.countFreecels();
		assertTrue(cant<10);
		assertTrue(cant>=0);
	}
	@Test
	public void testSolve7()  throws Exception{
		Parser parser=new Parser();
		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "treinta.txt");
		FlowJframe frame=new FlowJframe(board);
		frame.showBoard();
		Chronometer chrono= new Chronometer();
		chrono.start();
		Board boardSolution=board.solveAprox(new PrintListener(frame), new Chronometer(100));
		chrono.stop();
		System.out.println("tiempo total tardado  "+ chrono.getElapsedTimeInMilisecs());
		if(boardSolution.getMatrix()==null){
			System.out.println("esta quedando nula la matrix");
		}
	}
    @Test
    public void testSolve30x30()  throws Exception{
        Parser parser=new Parser();
        Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "29x30_1color.txt");
        Chronometer chrono= new Chronometer();
        FlowJframe frame=new FlowJframe(board);
		frame.showBoard();
        chrono.start();
        Board boardSolution=board.solveAprox(new PrintListener(frame), new Chronometer(10000));
        chrono.stop();
        System.out.println("tiempo total tardado  "+ chrono.getElapsedTimeInMilisecs());
        if(boardSolution.getMatrix()==null){
            System.out.println("esta quedando nula la matrix");
        }
    }
}
