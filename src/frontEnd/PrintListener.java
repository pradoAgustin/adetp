package frontEnd;

import backend.Board;
import backend.Listener;
/* clase que le permite al backend avisarle al front end que es necesario graficar el tablero en pantalla.
 * Se emplea solamente para el caso en que se corra el programa con la opción "progress"
 */
public class PrintListener implements Listener{

	private static final long sleepTime=100;
	private FlowJframe frame;
	
	public PrintListener(FlowJframe f){
		frame=f;
	}
	
	@Override
	public void printToScreen() {
		if(frame!=null){
			//frame.hide();
			frame.showBoard();
//			
//			try {
//				Thread.sleep(sleepTime);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		}
	}
	@Override
	public void changeBoard(Board b) {
		frame.changeBoard(b);
		
	}

}
