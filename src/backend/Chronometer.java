package backend;

	/**
     * Clase que modela un cronometro. El tiempo se almacena internamente en milisegundos.
	 * Se puede consultar el tiempo transcurrido en segundos en milisegundos.
	 * Tambi�n permite saber si queda tiempo, usando el metodo isThereTime remaining
	 */
public class Chronometer {
    public long start,end,maxTime;

    public Chronometer(){
    }

    public Chronometer(long maxSecs){
        maxTime = maxSecs*1000;
    }

    public void start(){
        start = System.currentTimeMillis();
    }
    public void stop(){
        end = System.currentTimeMillis();
    }

    public boolean isThereTimeRemaining(){
        return maxTime < System.currentTimeMillis()- start;
    }

    /**
     * Devuelve el tiempo transcurrido desde que se lo inicio medido en segundos
     */
    public long getElapsedTimeInSecs(){
        return this.getElapsedTimeInMilisecs()/1000;
    }

    /**
     * Devuelve el tiempo transcurrido desde que se lo inicio medido en milisegundos
     */
    public long getElapsedTimeInMilisecs(){
        return end-start;
    }
}

