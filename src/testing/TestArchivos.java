package testing;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import backend.Parser;

public class TestArchivos {
	static Parser parser;
	
	
	/*
	 * se testea la lectura del archivo del enunciado
	 */
	@Test
	public void lecturaArchivoValido() throws IOException{
		Parser parser=new Parser();
		Object aux=parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoEnunciado.txt");
		assertTrue(aux!=null);
	}
	
	
	/*se trata de leer el archivo invalido1, el cual no especifica la cantidad de filas y de columnas
	 * 
	 */
	@Test(expected = IOException.class)
	public void lecturaArchivoNoValido1() throws Exception{
		System.out.println();
		parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoInvalido1.txt");
	}
	
	
	
	/*se trata de leer el archivo invalido2, el cual tiene menos columnas delas que especifica
	 * 
	 */
	@Test(expected = IOException.class)
	public void lecturaArchivoNoValido2() throws Exception{
		
		
		parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoInvalido2.txt");
	}
	

	/*se trata de leer el archivo invalido3, el cual tiene menos filas delas que especifica en la primera línea
	 * 
	 */
	@Test(expected = IOException.class)
	public void lecturaArchivoNoValido3() throws Exception{
		
		
		parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoInvalido3.txt");
	}
	

	/*se trata de leer el archivo invalido4, el cual tiene una columna menos en la segunda línea
	 * 
	 */
	@Test(expected = IOException.class)
	public void lecturaArchivoNoValido4() throws Exception{
		
		
		parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoInvalido4.txt");
	}
	
	
	
	/*se trata de leer el archivo invalido5, el cual tiene un color sin destino
	 * 
	 */
	@Test(expected = IOException.class)
	public void lecturaArchivoNoValido5() throws Exception{
		
		
		parser.levantarNivel("ArchivosEntrada"+File.separator+"ArchivoInvalido5.txt");
	}
	

	/*se trata de leer  un archivo que no existe
	 * 
	 */
	@Test(expected = FileNotFoundException.class)
	public void lecturaArchivoNoValido6() throws Exception{
		
		
		parser.levantarNivel("ArchivosEntrada"+File.separator+"inexistente.txt");
	}
	
	
}
