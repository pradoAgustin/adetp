import java.util.ArrayList;


public class Board {
	private int matrix[][];
	private ArrayList<Dot>colors=new ArrayList<Dot>();
	public Board(){
			int matrix[][]=new int[10][10];
			matrix[0][0]=1;
			matrix[8][9]=1;
			matrix[5][6]=2;
			matrix[9][9]=2;
			for(int i =0; i<10;i++)
			{
				for(int j=0;j<10;j++){
					System.out.print(matrix[i][j]);
					
				}
			System.out.println(" ");
			}
	}
	
}
