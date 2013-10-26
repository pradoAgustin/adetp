package testing;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import backend.*;

import org.junit.Test;

import frontEnd.FlowJframe;
import frontEnd.PrintListener;

public class TestExact {
	/*mapa a resolver se encuentra resuelto  en /resources/level30png
	 * casilleros vacios que deberian quedar :0
	 */

	@Test
	public void testSolve1(){
       Cell matrix[][]=new Cell[9][9];
          Board board=new Board(matrix,new ArrayList<Dot>());
           
            for(int i=0;i<9;i++){
             for(int j=0;j<9;j++){
               matrix[i][j]=new Cell(-1);
              }
          }
            FlowJframe frame=new FlowJframe(board);
            frame.showBoard();
            matrix[0][6]=new Cell(Color.RED.getNum());   matrix[2][3]=new Cell(Color.RED.getNum());board.addDot(new Dot(new Position(0, 6), new Position(2, 3), backend.Color.RED.getNum()));
            matrix[0][7]=new Cell(Color.GRAY.getNum());   matrix[3][3]=new Cell(Color.GRAY.getNum());board.addDot(new Dot(new Position(0,7 ), new Position(3,3 ), backend.Color.GRAY.getNum()));
            matrix[0][8]=new Cell(Color.GREEN.getNum());   matrix[4][1]=new Cell(Color.GREEN.getNum());board.addDot(new Dot(new Position(0,8 ), new Position(4,1 ), backend.Color.GREEN.getNum()));
            matrix[1][1]=new Cell(Color.BLUE.getNum());   matrix[8][0]=new Cell(Color.BLUE.getNum());board.addDot(new Dot(new Position(1,1 ), new Position(8,0 ), backend.Color.BLUE.getNum()));
            
            matrix[2][0]=new Cell(Color.BLACK.getNum());   matrix[7][3]=new Cell(Color.BLACK.getNum());board.addDot(new Dot(new Position(2,0 ), new Position(7, 3), backend.Color.BLACK.getNum()));
            matrix[4][7]=new Cell(Color.ORANGE.getNum());   matrix[7][7]=new Cell(Color.ORANGE.getNum());board.addDot(new Dot(new Position(4,7 ), new Position(7,7 ), backend.Color.ORANGE.getNum()));
            matrix[5][4]=new Cell(Color.PINK.getNum());   matrix[7][6]=new Cell(Color.PINK.getNum());board.addDot(new Dot(new Position(5,4 ), new Position(7,6 ), backend.Color.PINK.getNum()));
            matrix[6][6]=new Cell(Color.YELLOW.getNum());   matrix[6][8]=new Cell(Color.YELLOW.getNum());board.addDot(new Dot(new Position(6,6 ), new Position(6,8 ), backend.Color.YELLOW.getNum()));
            
            matrix[8][5]=new Cell(Color.MAGENTA.getNum());   matrix[7][8]=new Cell(Color.MAGENTA.getNum());board.addDot(new Dot(new Position(8,5 ), new Position(7, 8), backend.Color.MAGENTA.getNum())); 
        

            frame=new FlowJframe(board);
		frame.showBoard();
		//frame=new FlowJframe(board);
		//frame.showBoard();
		
		Board boardSolution=board.solve(null);
		Cell[][] matrix2=boardSolution.getIntBoard();
		
		
		for(int i=0;i<matrix2[0].length;i++)
		{
			for(int j=0;j<matrix2[0].length;j++)
			{
				System.out.print(matrix2[i][j]);
			}
			System.out.println();
		}
		
		int cant=boardSolution.unPaintedCells();
		
		//frame=new FlowJframe(boardSolution);frame.showBoard();/*se muestra el tablero al finalizar*/
        System.out.println(cant);
        assertTrue(cant==0);/*se controla que efectivamente esten todos los lugares ocupados*/
		System.out.println("cantidad que quedo libre:"+cant);
		
		System.out.println("fin");
   //     try{
     //       Thread.sleep(100000);
      //  }catch(InterruptedException e){
       //     Thread.currentThread().interrupt();
       // }
	}
	
	
	
	/*testeo del algoritmo exacto con el mapa test3x3.txt, la solucion debe dar todo el mapa cubierto*/
	@Test
	public void testSolve2() throws Exception{
		Parser parser=new Parser();
		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "ArchivoEnunciado.txt");
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
		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "testf.txt");
		Chronometer chrono=new Chronometer();
		chrono.start();
		
		Board boardSolution2=board.solve(null);
		chrono.stop();System.out.println(chrono.getElapsedTimeInMilisecs());
		
		FlowJframe frame=new FlowJframe(boardSolution2);
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
}