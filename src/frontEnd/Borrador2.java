package frontEnd;

import java.io.File;

import backend.Board;
import backend.Chronometer;
import backend.Parser;


public class Borrador2 {
public static void main(String[] args) throws Exception {
	String fileName="9x9_7colores.txt";
		Parser parser=new Parser();
		
		Board board=parser.parseLevel(fileName);
		//FlowJframe frame=new FlowJframe(board);
		//frame.showBoard();

		Chronometer chronometer=new Chronometer();chronometer.start();
		board=board.solve(null);
		chronometer.stop();
		//frame.changeBoard(board);
		//frame.showBoard();
		//frame=new FlowJframe(board);
		//frame.showBoard();
		int cant=board.unPaintedCells();
		System.out.println(board.toString());
		
		
		System.out.println("solucion exacta encontrada en:"+chronometer.getElapsedTimeInSecs()+"segundos");
		System.out.println("cantidad minima de lugares libres:"+cant);
		
		System.out.println("Solucion aproximada");
		System.out.println("% de tiempo respecto aproximada --tiempo      --celdas libres-- diferencia de lugares libres con exacta");
		double[] porcentajesTiempos={0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1};

		for(double porcentaje:porcentajesTiempos){
			board=parser.parseLevel(fileName);
			double currentTime=((double)chronometer.getElapsedTimeInSecs())*porcentaje;	/*limite de tiempo*/
			
			
			//int currentFreeCels=5;
			int currentFreeCels=board.solveAprox(null,new Chronometer((long) Math.abs(currentTime))).unPaintedCells();
		System.out.println(porcentaje+"                              "+currentTime+"          "+currentFreeCels+"                           "+(currentFreeCels-cant));
		}
}
}
