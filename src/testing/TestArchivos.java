package testing;

import static org.junit.Assert.assertTrue;


import java.io.File;
import java.io.FileNotFoundException;

import backend.Parser;
import org.junit.Test;

public class TestArchivos {
	/*
	 * se testea la lectura del archivo del enunciado
	 */
	@Test
	public void lecturaArchivoValido() throws Exception{
		Parser parser=new Parser();
		Object aux=parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoEnunciado.txt");
		assertTrue(aux!=null);
	}
	
	
	/*se trata de leer el archivo invalido1, el cual no especifica la cantidad de filas y de columnas
	 * 
	 */
	@Test(expected = Exception.class)
	public void lecturaArchivoNoValido1() throws Exception{
	
		Parser parser=new Parser();
		parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoInvalido1.txt");
	}

	/*se trata de leer el archivo invalido2, el cual tiene menos columnas delas que especifica
	 * 
	 */
	@Test(expected = Exception.class)
	public void lecturaArchivoNoValido2() throws Exception{
		
		Parser parser=new Parser();
		parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoInvalido2.txt");
	}
	

	/*se trata de leer el archivo invalido3, el cual tiene menos filas delas que especifica en la primera l�nea
	 * 
	 */
	@Test(expected = Exception.class)
	public void lecturaArchivoNoValido3() throws Exception{
		Parser parser=new Parser();
		
		parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoInvalido3.txt");
	}
	

	/*se trata de leer el archivo invalido4, el cual tiene una columna menos en la segunda l�nea
	 * 
	 */
	@Test(expected = Exception.class)
	public void lecturaArchivoNoValido4() throws Exception{
		Parser parser=new Parser();
		
		parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoInvalido4.txt");
	}
	
	
	
	/*se trata de leer el archivo invalido5, el cual tiene un color sin destino
	 * 
	 */
	@Test(expected = Exception.class)
	public void lecturaArchivoNoValido5() throws Exception{
		
		Parser parser=new Parser();
		parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoInvalido5.txt");
	}
	

	/*se trata de leer  un archivo que no existe
	 * 
	 */
	@Test(expected = FileNotFoundException.class)
	public void lecturaArchivoNoValido6() throws Exception{
		Parser parser=new Parser();
		
		parser.levantarNivel("ArchivosEntrada"+File.separator+"inexistente.txt");
	}
	
	
}
