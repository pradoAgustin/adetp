

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicLookAndFeel;

public class parser {

	public ArrayList<String> levantarNivel(File  archivo) throws IOException{
		ArrayList<String> numbers= new ArrayList<String>();
		String line;
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new FileReader(archivo));
			while ((line = (buffer.readLine())) != null) {
				numbers.add(line);
			}
			return numbers;
		}
			
		finally{
			if (buffer != null) {
				buffer.close();
			}
		}
		}
	/*trata de armar la matrix con los colores y blancos , sino puede retorna null*/
	public Board setearJuego(ArrayList<String> nivel) {
		int matrix[][]=makeBoard(nivel.get(0));
		ArrayList<Dot>listcolor=new ArrayList<Dot>();
		Color colors[]={Color.RED, Color.MAGENTA,Color.BLUE,Color.YELLOW ,
			    Color.BLACK,Color. GREEN ,Color. PINK, Color.GRAY};
		for(int i=1;i<nivel.size();i++){
			String aux=nivel.get(i);
			for(int c=0;c<matrix.length;c++){
				char character=aux.charAt(c);
					for(int j=0;j<colors.length;j++)
					{
						if(colors[j].color==(int)character){
							matrix[i][c]=(int)character;
							if(!checkColorQty(listcolor,colors[j],i,c))
								return null;
						}
						else
						{
							if(character==' '){
								matrix[i][c]=Color.WHITE.color;
							}
							else
							{
								return null;
							}
						}
						
			}	
			
		}
		
	}
		return new Board(matrix,listcolor);
	}

/*chequea y guarda la posicion de los colores actuales si hay mas de 2 colores del mismo tipo retorna false*/
	private boolean checkColorQty(List<Dot> listcolor, Color color,
			int i, int c) {
		for(Dot point:listcolor){
			if(point.getColor()==color.color&& (color.color!=-1)){
				if(point.getStart()==null){
					Position aux=point.getStart();
					aux=new Position(i,c);
					return true;
				}
				else if(point.getEnd()==null){
					Position aux=point.getEnd();
					aux=new Position(i,c);
					return true;
				
				}
				return false;
			}
			}
		listcolor.add(new Dot(new Position(i,c),null,color.color));
		return true;
	}
/*levanta las 2 primeras , arma la matrix*/
	private int[][] makeBoard(String string) {
		int i=0;
		char a=string.charAt(i);
		i++;
		if(string.charAt(i)!=',')
			return null;
		i++;
		char b=string.charAt(i);
		
		if(a==b)
			return new int[a][b];
		return null;
	}
}
			
