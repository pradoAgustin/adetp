package frontEnd;

import backend.Board;
import backend.Listener;

public class printListener implements Listener{

	private FlowJframe frame;
	@Override
	public void printToScreen() {
		if(frame!=null)
			frame.showBoard();
		
	}
	@Override
	public void changeBoard(Board b) {
		frame.changeBoard(b);
		
	}

}
