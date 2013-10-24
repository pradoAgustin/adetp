package backend;

import java.util.LinkedList;

/**
 * Clase que simula un par de puntos (destinos en un tablero)
 * Guarda dos instancias de Position representando arbitrariamente el comienzo
 * y el fin de un camino, el color correspondiente al mismo
 */
public class Dot {
	private Position start;
	private Position end;
    private int color;

	public Dot(Position start , Position end, int color){
		this.start = start;
		this.end = end;
        this.color = color;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + color;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dot other = (Dot) obj;
		if (color != other.color)
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
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
    
    public String toString(){
    	return "["+"1:"+"start"+start.toString()+"end"+end.toString()+"]";
    }
}
