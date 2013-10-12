package testing;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import backend.Parser;

public class TestArchivos {
	static Parser parser;
	 @BeforeClass
	  public static void testSetup() {
		 
		 Parser parser=new Parser();
	  }
	
	/*
	 * se testea la lectura del archivo del enunciado
	 */
	@Test
	public void lecturaArchivoValido() throws IOException{
		
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
	
	
}
