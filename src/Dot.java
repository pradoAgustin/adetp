
public class Dot {
	private Position start;
	private Position end;
    private int color;

	public Dot(Position a , Position b, int color){
		this.start = a;
		this.end = b;
        this.color = color;
	}

    public Position getStart(){
        return this.start;
    }

    public Position getEnd(){
        return this.end;
    }

    public int getColor(){
        return this.color;
    }
}
