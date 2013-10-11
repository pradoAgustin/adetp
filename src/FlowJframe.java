
import java.awt.GridLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FlowJframe {

	private Board board;
	private int rows,cols;
	JFrame frame;
	JPanel squares[][] ;
	
	
	public FlowJframe(Board board){
		this.board=board;
		
		 rows=board.rowsSize();
		cols=board.colsSize();
		
		squares = new JPanel[rows][cols];
		
		
		
	}
	
	public void showBoard(){
		 frame = new JFrame("Flow");
		    frame.setSize(500, 500);
		    frame.setLayout(new GridLayout(rows, cols));
		    int [][] boardMatrix=board.getIntBoard();
		    for (int i = 0; i < rows; i++) {
		        for (int j = 0; j < cols; j++) {
		            squares[i][j] = new JPanel();
		            
		            		int current=boardMatrix[i][j];
		            		java.awt.Color backgroundColor;
		            		//squares[i][j].setBackground(java.awt.Color.white);
		            		
		            		
		            		
		            		switch(current){
		            		case -1:{
		            				backgroundColor=java.awt.Color.white;
		            			break;
		            		}
		            		case 1:{
	            				backgroundColor=java.awt.Color.red;
	            			break;
	            		}
		            		case 2:{
	            				backgroundColor=java.awt.Color.MAGENTA;
	            			break;
	            		}
		            		case 3:{
	            				backgroundColor=java.awt.Color.blue;
	            			break;
	            		}
		            		
		            		
		            		case 4:{
	            				backgroundColor=java.awt.Color.yellow;
	            			break;
	            		}
		            		case 5:{
	            				backgroundColor=java.awt.Color.black;
	            			break;
	            		}
		            		
		            		case 6:{
	            				backgroundColor=java.awt.Color.green;
	            			break;
	            		}
		            		
		            		case 7:{
	            				backgroundColor=java.awt.Color.pink;
	            			break;
	            			
	            		}
		            		case 8:{
	            				backgroundColor=java.awt.Color.GRAY;
	            			break;
	            		}
		            		default:
		            			backgroundColor=java.awt.Color.white;
		            		}
		            		squares[i][j].setBackground(backgroundColor);
		            		
		            
		            
		            if(current!=-1){
		            	JLabel image;	
		            	
		            	if(board.isOrigin(i, j,current) || board.isEnd(i, j,current) ){
		            		image=new JLabel(new ImageIcon("resources"+File.separator+"target.png"));
		            	}
		            	else{
		            		image=new JLabel(new ImageIcon("resources"+File.separator+"punto.png"));
				            
		            	}
		            

		            	squares[i][j].add(image);
	               
		            }
		            	
		            		//squares[i][j].add(new JLabel(new ImageIcon("resources"+File.separator+"punto.png")));
		                frame.add(squares[i][j]);
		        }
		    }
		    
		   
		    

		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setVisible(true);
	}
	
	
	

	public static void main(String[] args) {
	    FlowJframe aux = new FlowJframe(new Board());
	    	aux.showBoard();
	}
}
