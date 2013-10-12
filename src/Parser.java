import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicLookAndFeel;

public class Parser {

    public ArrayList<String> levantarNivel(File  archivo) throws IOException{
        ArrayList<String> numbers = new ArrayList<String>();
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
    /*trata de armar la matriz con los colores y blancos , sino puede retorna null*/
    public int[][] setearJuego(ArrayList<String> nivel) {
        int matrix[][] = makeBoard(nivel.get(0));
        Color colores[]={Color.RED,Color.YELLOW,Color.BLACK};
        List<Position> listcolor = new ArrayList<Position>();
        for(int i=1;i<nivel.size();i++){
            String aux=nivel.get(i);
            for(int c=0;c<matrix.length;c++){
                char character=aux.charAt(c);
                for(int j=0;j<colores.length;j++)
                {
                    if(colores[j].color==character){
                        matrix[i][c]=(int)character;
                        if(!checkColorQty(listcolor,character,i,c))
                            return null;
                    }
                    else
                    {
                        if(character == ' '){
                            matrix[i][c] = -1;
                        }
                        else
                        {
                            return null;
                        }
                    }

                }

            }

        }
        return matrix;
    }

    /*chequea y guarda la posicion de los colores actuales si hay mas de 2 colores del mismo tipo retorna false*/
    private boolean checkColorQty(List<Position> listcolor, char character,
                                  int i, int c) {
        // TODO Auto-generated method stub
        return false;
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
