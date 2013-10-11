import java.util.ArrayList;

public class Board {
	private int board[][];
	private ArrayList<Dot> dots =new ArrayList<Dot>();
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

    public void solve(){
        Dot initialDot = dots.get(0);
        Board solution = new Board();
        solve(initialDot.getColor(), initialDot.getStart(), 0, solution);
    }

    private void solve(int color, Position point, int index, Board solution){
        if(color == this.board[point.row][point.col]){
            if(point == dots.get(index).getEnd()){
                if(dots.size() == index+1){                     /* Si no quedan mas puntos por unir... */
                    saveSolution(solution);
                    return;
                }
                Dot nextDot = dots.get(index+1);
                solve(nextDot.getColor(), nextDot.getStart(), index+1, solution);
            }else{
                return;
            }
        }
        solve(color, point.down(), index, solution);
        solve(color, point.up(), index, solution);
        solve(color, point.left(), index, solution);
        solve(color, point.right(), index, solution);
    }

    private void saveSolution(Board solution){
        // TODO
    }
}
