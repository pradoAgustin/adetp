
public enum Color {
   ROJO(1), VIOLETA(2), AZUL(3), AMARRILO(4) , NEGRO(5), VERDE(6) ,ROSA(7),GRIS(8),BLANCO(-1);
   int color;
   
   Color(int color){
	   this.color = color;
   }
   public int getNum(){
	   return color;
   }
}
