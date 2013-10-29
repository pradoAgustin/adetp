package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Parser {
    public static final int COLORS_AMOUNT = 10;
    public  Board parseLevel(String pathname) throws Exception{
        File mapFile = new File(pathname);
        BufferedReader buffer = null;
        ArrayList<Position> colorPosList = new ArrayList<Position>();
        int rowsSize, colsSize;
        String line;
        String[] boardSize;
        int[] colorDotsAmount = new int[COLORS_AMOUNT];
        try {
            buffer = new BufferedReader(new FileReader(mapFile));
            if((line = buffer.readLine()) == null) throw new InvalidFileException("Invalid map file");
            line = line.trim();
            boardSize = line.split(",");
            rowsSize = Integer.valueOf(boardSize[0]);
            colsSize = Integer.valueOf(boardSize[1]);

            Cell board[][] = new Cell[rowsSize][colsSize];
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
                        board[row][col] = new Cell(currentChar -'0');
                        colorDotsAmount[currentChar-'0']++;
                    }else{
                        board[row][col] =new Cell(-1);
                    }
                }
            }
            if(row != rowsSize) throw new InvalidFileException("Invalid map file: not all the rows are specified");
            for(int i : colorDotsAmount){
                if(i == 1) throw new InvalidFileException("There is a color which has a single dot instead of 2");
                if(i > 2) throw new InvalidFileException("There are more than 2 dots for a single color");
            }
            ArrayList<Dot> aux;
            return new Board(board, createDotArray(colorPosList, board));
        }finally{
            if (buffer != null) buffer.close();
        }
    }

    private Dot[] createDotArray(ArrayList<Position> colorPosList, Cell[][] board) throws InvalidFileException{
        ArrayList<Dot> ret = new ArrayList<Dot>();
        int i, j;
        for(i = 0; i < colorPosList.size(); i++){
            for(j = i+1; j < colorPosList.size(); j++){
                Position pos1 = colorPosList.get(i);
                Position pos2 = colorPosList.get(j);
                int color;
                if( (color = board[pos1.row][pos1.col].color) == board[pos2.row][pos2.col].color ){
                    ret.add(new Dot(pos1, pos2, color));
                    continue;
                }
            }
        }
        return ret.toArray(new Dot[ret.size()]);
    }

    private class InvalidFileException extends Exception{
        public InvalidFileException(String message){
            super(message);
        }
    }
}
