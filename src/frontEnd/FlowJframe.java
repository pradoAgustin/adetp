package frontEnd;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.Board;
import backend.Cell;
public class FlowJframe {
	private Board board;
	private JFrame frame;
	private JPanel squares[][] ;
	private Cell [][] boardMatrix;
	public FlowJframe(Board board){
	    this.board=board;
		squares = new JPanel[board.rowsSize()][board.colsSize()];
		
		 frame = new JFrame("Flow");
		 frame.setSize(500, 500);
		 if(board.rowsSize()<=0 || board.colsSize()<=0)
			 return;
		 frame.setLayout(new GridLayout(squares.length, squares[0].length));
		 
		 boardMatrix=board.getIntBoard();
		 
		 for (int i = 0; i < squares.length; i++) {
			    for (int j = 0; j < squares[0].length; j++) {
			        squares[i][j] = new JPanel();
			        frame.add(squares[i][j]);
			    }
		 }
		 
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setVisible(true);
	}
	protected void changeBoard(Board b){
		board=b;
	}
	public void showBoard(){
		
		//Border border = LineBorder.createGrayLineBorder();
		int [][] boardMatrix=board.getIntBoard();
		
		
		for (int i = 0; i < squares.length; i++) {
		    for (int j = 0; j < squares[0].length; j++) {
		        
		        int current=boardMatrix[i][j];
		        java.awt.Color backgroundColor;
		        //squares[i][j].setBackground(java.awt.Color.white);
                switch(current){
		            case -1:
                        backgroundColor=java.awt.Color.white;
                        break;
                    case 1:
                        backgroundColor=java.awt.Color.red;
                        break;
                    case 2:
                        backgroundColor=java.awt.Color.MAGENTA;
                        break;
                    case 3:
                        backgroundColor=java.awt.Color.blue;
                        break;
                    case 4:
                        backgroundColor=java.awt.Color.yellow;
                        break;
                    case 5:
                        backgroundColor=java.awt.Color.black;
                        break;
                    case 6:
                        backgroundColor=java.awt.Color.green;
                        break;
                    case 7:
                        backgroundColor=java.awt.Color.pink;
                        break;
                    case 8:
                        backgroundColor=java.awt.Color.GRAY;
                        break;
                    case 9:
                        backgroundColor=java.awt.Color.ORANGE;
                        break;
                    default:
                        backgroundColor=java.awt.Color.white;
                }
                squares[i][j].setBackground(backgroundColor);

                if(current!=-1){
                    JLabel image;

                    if(board.isOrigin(i, j,current) ){
                        image=new JLabel(new ImageIcon("resources"+File.separator+"start.png"));
                    }
                    else if(board.isEnd(i, j,current))
                    	 image=new JLabel(new ImageIcon("resources"+File.separator+"end.png"));
                    else{
                        image=new JLabel(new ImageIcon("resources"+File.separator+"punto.png"));

                    }
                    //image.setBorder(border);
                    
                    squares[i][j].removeAll();
                    squares[i][j].add(image);
                    squares[i][j].validate();
                  
                    
                }
                //squares[i][j].add(new JLabel(new ImageIcon("resources"+File.separator+"punto.png")));
                //  squares[i][j].paintImmediately(squares[i][j].getX(), squares[i][j].getY(), squares[i][j].getWidth(), squares[i][j].getHeight());
		    }
		 }
		frame.validate();
		frame.repaint();
		 
		
	}
	public void refresh(){
		frame.invalidate();
		frame.validate();
		frame.repaint();	}
	public void hide(){
		frame.setVisible(false);
	}
	
}
