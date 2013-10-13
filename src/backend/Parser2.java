package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser2 {
    public static final int COLORS_AMOUNT = 10;
    public Board levantarNivel(String  pathname) throws Exception{
        File mapFile = new File(pathname);
        BufferedReader buffer = null;
        ArrayList<Position> colorPosList = new ArrayList<Position>();
        int rowsSize, colsSize;
        String line;
        try {
            buffer = new BufferedReader(new FileReader(mapFile));
            if((line = buffer.readLine()) == null) throw new InvalidFileException("Invalid map file");

            line = line.trim();
            rowsSize = line.charAt(0)-'0';
            if(line.charAt(1) != ',')
                throw new InvalidFileException("Invalid map file: No comma present on the first line");
            colsSize = line.charAt(2)-'0';

            int board[][] = new int[rowsSize][colsSize];
            int row;

            for(row = 0; (line = (buffer.readLine())) != null; row++) {
                if(line.length() != colsSize)
                    throw new InvalidFileException("Invalid map file: some row contains more columns than the rest");
                for(int col = 0; col < line.length(); col++){
                    char currentChar = line.charAt(col);
                    if(currentChar != ' '){
                        if(currentChar < '0' && currentChar >'9')
                            throw new InvalidFileException("Invalid map file: unsupported character present");
                        colorPosList.add(new Position(row, col));
                        board[row][col] = currentChar -'0';
                    }else{
                        board[row][col] = -1;
                    }
                }
            }
            if(row != rowsSize) throw new InvalidFileException("Invalid map file: not all the rows are specified");

            return new Board(board, createDotList(colorPosList, board));
        }finally{
            if (buffer != null) buffer.close();
        }
    }

    private ArrayList<Dot> createDotList(ArrayList<Position> colorPosList, int[][] board) throws InvalidFileException{
        ArrayList<Dot> ret = new ArrayList<Dot>();
        int[] presentColors = new int[COLORS_AMOUNT];
        for(int v = 0; v < presentColors.length; v++){
            presentColors[v] = -2;
        }
        for(int i = 0; i < colorPosList.size(); i++){
            for(int j = i+1; j < colorPosList.size(); j++){
                Position pos1 = colorPosList.get(i);
                Position pos2 = colorPosList.get(j);
                int color;
                if( (color = board[pos1.row][pos1.col]) == board[pos2.row][pos2.col] ){
                    ret.add(new Dot(pos1, pos2, color));
                    if(presentColors[color] != -2){
                        throw new InvalidFileException("There are more than 2 dots for a single color");
                    }
                    presentColors[color] = color;
                }
            }
        }
        return ret;
    }

    private class InvalidFileException extends Exception{
        public InvalidFileException(String message){
            super(message);
        }

    }
}
