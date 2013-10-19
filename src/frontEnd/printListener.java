package frontEnd;

import backend.Board;
import backend.Listener;

public class printListener implements Listener{

	private static final long sleepTime=100;
	private FlowJframe frame;
	@Override
	public void printToScreen() {
		if(frame!=null){
			frame.showBoard();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void changeBoard(Board b) {
		frame.changeBoard(b);
		
	}

}
