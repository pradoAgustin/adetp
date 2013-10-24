package frontEnd;

import java.io.FileNotFoundException;

import backend.Board;
import backend.Chronometer;
import backend.InvalidFileException;
import backend.Listener;
import backend.Parser;

public class Main {
public static void main(String[] args) {

	Board board = null,boardSolution=null;
	
	
	Parser parser=new Parser();
	if(args.length<2){
		System.out.println("Error:cantidad de parametros menor a dos");
		return;
	}
	
	
	
	try {
		board = parser.parseLevel(args[0]);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("Error:Archivo \""+args[0]+"\"inexistente.");return;
	}catch(InvalidFileException e){
		System.out.println(e.getMessage());return;
	}catch(Exception e){
		System.out.println("Error de lectura de archivo ");return;
	}
	
	
	System.out.println("Se leyo el archivo \""+args[0]+"\".");
	FlowJframe frame=new FlowJframe(board);
	
	Listener l=null;
	
	
	Chronometer chronometer=new Chronometer();//cronometro que se usa para medir el tiempo total tardado por el algoritmo

	if(args[1].equals("exact")){
		
		
		chronometer.start();

		
		System.out.println("Se busca la solucion en forma exacta");
		if(args.length>=3 && args[2].equals("progress"))
			l=new PrintListener(frame);
		else if(args.length>=3){
			System.out.println("Error: tercer parametro ingresado invalido ");
			return;
		}
		
		boardSolution=board.solve(l);
		chronometer.stop();
		
	}
	else if(args[1].equals("approx")){
		if(args.length<2 ){
			System.out.println("Error: hay que pasar como parametro el tiempo que se le desea dedicar al algoritmo");
			return;
		}
		System.out.println("Se busca la solucion en forma aproximada con "+Integer.valueOf(args[2]));
		Chronometer timer=new Chronometer(Integer.valueOf(args[2]));
		
		
		
		if(args.length>3 && args[3].equals("progress"))
			l=new PrintListener(frame);
		chronometer.start();
		
		boardSolution=board.solveAprox(l, timer);
		chronometer.stop();
	}else{
		System.out.println("Error:los parametros ingresados son invalidos.");
		return;
	}
	
		frame.changeBoard(boardSolution);
		System.out.println("Tiempo transcurrido en obtener la solucion exacta:"+chronometer.getElapsedTimeInMilisecs()+" milisegundos.");
		frame.showBoard();
	
	
}


private static void printBoardMatrixConsole(){
	solution
	for(int h=0;h<solution.getIntBoard().length;h++){
		for(int k=0;k<solution.getIntBoard()[0].length;k++){
			System.out.print(solution.getIntBoard()[h][k]);
		}
		System.out.println();
	}
	
	
}


}
