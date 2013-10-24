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
	
	
	Chronometer chronometer=null;//cronometro que se usa para medir el tiempo total tardado por el algoritmo

	if(args[1].equals("exact")){
		
		chronometer=new Chronometer();//cronometro que se usa para medir el tiempo total tardado por el algoritmo
		chronometer.start();

		
		System.out.println("Se busca la solucion en forma exacta");
		if(args[2].equals("progress"))
			l=new PrintListener(frame);
		
		boardSolution=board.solve(l);
		
	}
	else if(args[1].equals("approx")){
		System.out.println("Se busca la solucion en forma aproximada con "+Integer.valueOf(args[2]));
		Chronometer timer=new Chronometer(Integer.valueOf(args[2]));
		
		if(args[3].equals("progress"))
			l=new PrintListener(frame);
		boardSolution=board.solveAprox(l, timer);
	}else{
		System.out.println("Error:los parametros ingresados son invalidos.");
	}
	
	if(chronometer!=null){
		System.out.println("Tiempo transcurrido en obtener la solucion exacta:"+chronometer.getElapsedTimeInSecs()+" segundos.");
	}
	
	
}
}
