package frontEnd;

import static org.junit.Assert.assertTrue;

import java.io.File;

import backend.Board;
import backend.Parser;

public class Main {
public static void main(String[] args) {
	System.out.println("probando ant");
	Parser parser=new Parser();
	Board board = null;
	try {
		board = parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoEnunciado.txt");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	FlowJframe frame=new FlowJframe(board);
	frame.showBoard();
	//Board boardSolution2=board.solve(new printListener(frame));/* para ver la opcion "progress"*/
	
}
}
