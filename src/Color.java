
public enum Color {
    RED(1), MAGENTA(2), BLUE(3), YELLOW(4) ,
    BLACK(5), GREEN(6) , PINK(7), GRAY(8), WHITE(-1);

    int color;
   
    Color(int color){
	    this.color = color;
    }
    public int getNum(){
	    return color;
    }
}
