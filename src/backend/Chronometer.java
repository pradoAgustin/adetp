package backend;

	/**
     * Clase que modela un cronometro. Todos los metodos de entrada y de salida son con parametros en segundos.El tiempo se almacena internamente en milisegundos.
	 * Se puede consultar el tiempo transcurrido en segundos en milisegundos.
	 * Tambi�n permite saber si queda tiempo, usando el metodo isThereTime remaining
	 */
public class Chronometer {
    private long start,end,maxTime;

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

    public boolean thereIsTimeRemaining(){
        return maxTime > (System.currentTimeMillis()- start);
    }

    /**
     * Devuelve el tiempo transcurrido desde que se lo inicio medido en segundos
     */
    public long getElapsedTimeInSecs(){
        return this.getElapsedTimeInMilisecs()/1000;
    }

    public long checkCurrentTime(){
        return System.currentTimeMillis()-this.start;
    }

    /**
     * Devuelve el tiempo transcurrido desde que se lo inicio medido en milisegundos
     */
    public long getElapsedTimeInMilisecs(){
        return end-start;
    }
}

