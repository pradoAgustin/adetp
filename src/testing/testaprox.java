package testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import backend.Board;
import backend.Chronometer;
import backend.Color;
import backend.Dot;
import backend.Parser;
import backend.Position;
import frontEnd.FlowJframe;
import frontEnd.PrintListener;

public class testaprox{
	
	
//	
//	/* se prueba el metodo getPositionsWithPriority en el caso de que el siguiente movimiento de prioridad sea en forma horizontal izquierda*/
//	@Test
//	public void testGetPositionWithPriority(){
//		int[][]m=new int[6][6];
//		Board b=new Board(m,null);
//		
//		Position currentPos=new Position(5, 0);
//		Position finalPos=new Position(5, 5);
//		Position[] ans=b.getPositionsWithPriority(currentPos, finalPos);
//		assertTrue(ans[0].equals(new Position(5, 1)));
//		assertTrue(ans.length==4);
//		for(int i=0;i<ans.length;i++){
//			for(int j=i;j>0;j--){
//				assertFalse(i==j && ans[i].equals(ans[j]));
//			}
//		}
//	}
//	/* se prueba el metodo getPositionsWithPriority en el caso de que el siguiente movimiento de prioridad sea en forma horizontal derecha*/
//	@Test
//	public void testGetPositionWithPriority2(){
//		int[][]m=new int[6][6];
//		Board b=new Board(m,null);
//		
//		Position currentPos=new Position(5, 5);
//		Position finalPos=new Position(5, 0);
//		Position[] ans=b.getPositionsWithPriority(currentPos, finalPos);
//		assertTrue(ans[0].equals(new Position(5, 4)));
//		assertTrue(ans.length==4);
//		for(int i=0;i<ans.length;i++){
//			for(int j=i;j>0;j--){
//				assertFalse(i==j && ans[i].equals(ans[j]));
//			}
//		}
//	}
//	/* se prueba el metodo getPositionsWithPriority en el caso de que el siguiente movimiento de prioridad sea en forma vertical abajo*/
//	@Test
//	public void testGetPositionWithPriority3(){
//		int[][]m=new int[6][6];
//		Board b=new Board(m,null);
//		
//		Position currentPos=new Position(0, 0);
//		Position finalPos=new Position(5,0);
//		Position[] ans=b.getPositionsWithPriority(currentPos, finalPos);
//		System.out.println(ans[0]);
//		System.out.println(ans[1]);
//		System.out.println(ans[2]);
//		System.out.println(ans[3]);
//		assertTrue(ans[0].equals(new Position(1, 0)));
//		assertTrue(ans.length==4);
//		for(int i=0;i<ans.length;i++){
//			for(int j=i;j>0;j--){
//				assertFalse(i==j && ans[i].equals(ans[j]));
//			}
//		}
//	}
//	/* se prueba el metodo getPositionsWithPriority en el caso de que el siguiente movimiento de prioridad sea en forma diagonal abajo*/
//	@Test
//	public void testGetPositionWithPriority5(){
//		int[][]m=new int[6][6];
//		Board b=new Board(m,null);
//		
//		Position currentPos=new Position(0, 0);
//		Position finalPos=new Position(3, 2);
//		Position[] ans=b.getPositionsWithPriority(currentPos, finalPos);
//		assertTrue(ans[0].equals(new Position(1, 0))||ans[0].equals(new Position(0, 1)) );
//		assertTrue(ans.length==4);
//		for(int i=0;i<ans.length;i++){
//			for(int j=i;j>0;j--){
//				assertFalse(i==j && ans[i].equals(ans[j]));
//			}
//		}
//	}

	
	
	 @Test
	   	public void testSolve5() throws Exception{
	   		Parser parser=new Parser();
	   		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "test3x3(2).txt");
	   		FlowJframe frame=new FlowJframe(board);
	   		frame.showBoard();
	   		board=board.solveAprox(new PrintListener(frame),new Chronometer(1000000000));//puse un tiempo grande para testear
	   		
	   		frame=new FlowJframe(board);
	   		frame.showBoard();
	   		int cant=board.unPaintedCells();
	   		
	   	}
	@Test
	public void testSolve2() throws Exception{
		Parser parser=new Parser();
		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "test3x3.txt");
		FlowJframe frame=new FlowJframe(board);
		frame.showBoard();
		 board=board.solveAprox(new PrintListener(frame),new Chronometer(1000000000));
		 frame.showBoard();

		int cant=board.unPaintedCells();
		assertTrue(board!=null && board.colsSize()>0);/*se controla la matriz del tablero solucion no este vacia*/
		assertTrue(cant<9);
	}
	
	@Test
	public void testSolve3() throws Exception{
		Parser parser=new Parser();
		Board board=parser.parseLevel("ArchivosEntrada" + File.separator + "ArchivoEnunciado.txt");
		FlowJframe frame=new FlowJframe(board);
		frame.showBoard();
		Board boardSolution2=board.solveAprox(null/*new PrintListener(frame)*/,new Chronometer(1000000000));
		
		frame=new FlowJframe(boardSolution2);
		frame.showBoard();
		assertTrue(boardSolution2!=null);
		assertTrue( boardSolution2.rowsSize()>0 && boardSolution2.colsSize()>0);/* se comprueba que se grabo correctamente la matriz solucion del Board*/
		int cant=boardSolution2.unPaintedCells();
		assertTrue(cant<6);
	}
	@Test
	public void testSolve4() throws Exception{
		

	        int matrix[][]=new int[9][9];
	          Board board=new Board(matrix,new ArrayList<Dot>());
	           
	            for(int i=0;i<9;i++){
	             for(int j=0;j<9;j++){
	               matrix[i][j]=-1;
	              }
	          }
	            FlowJframe frame=new FlowJframe(board);
	            frame.showBoard();
	            matrix[0][6]=Color.RED.getNum();   matrix[2][3]=Color.RED.getNum();board.addDot(new Dot(new Position(0, 6), new Position(2, 3), backend.Color.RED.getNum()));
	            matrix[0][7]=Color.GRAY.getNum();   matrix[3][3]=Color.GRAY.getNum();board.addDot(new Dot(new Position(0,7 ), new Position(3,3 ), backend.Color.GRAY.getNum()));
	            matrix[0][8]=Color.GREEN.getNum();   matrix[4][1]=Color.GREEN.getNum();board.addDot(new Dot(new Position(0,8 ), new Position(4,1 ), backend.Color.GREEN.getNum()));
	            matrix[1][1]=Color.BLUE.getNum();   matrix[8][0]=Color.BLUE.getNum();board.addDot(new Dot(new Position(1,1 ), new Position(8,0 ), backend.Color.BLUE.getNum()));
	            
	            matrix[2][0]=Color.BLACK.getNum();   matrix[7][3]=Color.BLACK.getNum();board.addDot(new Dot(new Position(2,0 ), new Position(7, 3), backend.Color.BLACK.getNum()));
	            matrix[4][7]=Color.ORANGE.getNum();   matrix[7][7]=Color.ORANGE.getNum();board.addDot(new Dot(new Position(4,7 ), new Position(7,7 ), backend.Color.ORANGE.getNum()));
	            matrix[5][4]=Color.PINK.getNum();   matrix[7][6]=Color.PINK.getNum();board.addDot(new Dot(new Position(5,4 ), new Position(7,6 ), backend.Color.PINK.getNum()));
	            matrix[6][6]=Color.YELLOW.getNum();   matrix[6][8]=Color.YELLOW.getNum();board.addDot(new Dot(new Position(6,6 ), new Position(6,8 ), backend.Color.YELLOW.getNum()));
	            
	            matrix[8][5]=Color.MAGENTA.getNum();   matrix[7][8]=Color.MAGENTA.getNum();board.addDot(new Dot(new Position(8,5 ), new Position(7, 8), backend.Color.MAGENTA.getNum())); 
	        
	
	            frame=new FlowJframe(board);
			frame.showBoard();
			//frame=new FlowJframe(board);
			//frame.showBoard();
			
			Board boardSolution=board.solve(null);
			int [][] matrix2=boardSolution.getIntBoard();
			
			
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
			
			 assertTrue(cant==0);/*se controla que efectivamente esten todos los lugares ocupados*/
			System.out.println("cantidad que quedo libre:"+cant);
			
			System.out.println("fin");
	   //     try{
	     //       Thread.sleep(100000);
	      //  }catch(InterruptedException e){
	       //     Thread.currentThread().interrupt();
	       // }
		
		
		
		
		
	 frame=new FlowJframe(board);
		frame.showBoard();
		Board boardSolution2=board.solveAprox(new PrintListener(frame),new Chronometer(1000000000));
		
		frame=new FlowJframe(boardSolution2);
		frame.showBoard();
		assertTrue(boardSolution2!=null);
		assertTrue( boardSolution2.rowsSize()>0 && boardSolution2.colsSize()>0);/* se comprueba que se grabo correctamente la matriz solucion del Board*/
		 cant=boardSolution2.unPaintedCells();
		assertTrue(cant<6);
	
	
}

}
