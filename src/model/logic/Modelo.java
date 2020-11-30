package model.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.GrayFilter;

import com.opencsv.bean.CsvToBeanBuilder;

import clases.Hash;
import clases.Pelicula;
import clases.ShellSort;
import controller.Controller;
import model.data_structures.ArregloDinamico;
import model.data_structures.DiGraph2;
import model.data_structures.Edge;
import model.data_structures.IListaEncadenada;
import model.data_structures.ListaEncadenada;
import model.data_structures.ListaEncadenada.Nodo;
import model.data_structures.ListaEncadenadaSinComparable;
import model.data_structures.NodoHash;
import model.data_structures.RedBlackBST;
import model.data_structures.Station;
import model.data_structures.TablaHashSeparateChaining;
import model.data_structures.TablaSimbolos;
import model.data_structures.Vertex;
import model.data_structures.Viaje;
import model.data_structures.tablaHashLinearProbing;


import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	
	/**
	 * Atributos del modelo del mundo
	 */
	private Hash hash;
	private Controller controller;
	private ShellSort shellsort;
	private int tamañoLista = 2017;
	private int tamañoListaActores = 400009;
	private int tamañoSiguientePrimo;
	private boolean hayPeliculas;
	private IListaEncadenada datos;
	private TablaSimbolos linearProbing, separateChaining, separateChainingActores;
	
	private DiGraph2<Integer, Station> grafo;
	
	
	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo(Controller pController)
	{
		hayPeliculas = false;
		controller = pController;
		grafo = new DiGraph2<Integer, Station>();
	}
	
	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano()
	{
		return datos.contarDatos();
	}
	
	/**
	 * Servicio de consulta si hay una base de datos cargada 
	 * @return true si hay una base de datos cargada, false de lo contrario
	 */
	public boolean darCarga() {
		return hayPeliculas;
	}

	/**
	 * Requerimiento de agregar dato
	 * @param dato a agregar
	 */
	public void agregar(String dato)
	{	
		datos.insert(dato);
	}
	

	public void cargarLista() {
		datos = new ListaEncadenada();
		String archivo = "./data/SmallMoviesDetailsCleaned.csv";
		String archivo2 = "./data/MoviesCastingRaw-small.csv";
		String linea = "";
		String linea2 = "";
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			br.readLine();
			BufferedReader br2 = new BufferedReader(new FileReader(archivo2));
			br2.readLine();
			while((linea = br.readLine()) !=null && (linea2 = br2.readLine()) !=null)
			{
				String[] valores = linea.split(";");
				String[] valores2 = linea2.split(";"); 
				if(valores[0].equals(valores2[0]))
				{
					String[] fechaProduccion = valores[10].split("/");
					String añoProduccion = fechaProduccion[2];
					String llave = (valores[8]+"," + añoProduccion);
					Pelicula pelicula = new Pelicula((Integer.parseInt(valores[0])), ((String)valores[5]), valores[2], valores2[12], Float.parseFloat(valores[18]), Float.parseFloat(valores[17]),valores2[1],valores2[3],valores2[5],valores2[7],valores2[9], valores[8], añoProduccion);
					((ListaEncadenada) datos).insert(pelicula);
				}
			} 
			hayPeliculas = true;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void cargarArreglo()
	{
		datos = new ArregloDinamico(10);
		String archivo = "./data/SmallMoviesDetailsCleaned.csv";
		String archivo2 = "./data/MoviesCastingRaw-small.csv";
		String linea = "";
		String linea2 = "";
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			br.readLine();
			BufferedReader br2 = new BufferedReader(new FileReader(archivo2));
			br2.readLine();
			while((linea = br.readLine()) !=null && (linea2 = br2.readLine()) !=null)
			{
				String[] valores = linea.split(";");
				String[] valores2 = linea2.split(";"); 
				if(valores[0].equals(valores2[0]))
				{
					String[] fechaProduccion = valores[10].split("/");
					String añoProduccion = fechaProduccion[2];
					String llave = (valores[8]+"," + añoProduccion);
					Pelicula agregada = new Pelicula((Integer.parseInt(valores[0])), ((String)valores[5]), valores[2], valores2[12], Float.parseFloat(valores[18]), Float.parseFloat(valores[17]),valores2[1],valores2[3],valores2[5],valores2[7],valores2[9], valores[8], añoProduccion);
					agregada.ordenarActores();
					datos.agregarAlFinal(agregada);
				}
			} 
			hayPeliculas = true;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Funcion de cargar una base de datos de peliculas como una hash table
	 */
	public void cargarHashTable() 
	{
		datos = new ArregloDinamico(10);
		separateChaining = new TablaHashSeparateChaining<Hash, Pelicula>(tamañoLista);
		linearProbing = new tablaHashLinearProbing<>(tamañoLista);
		String archivo = "./data/SmallMoviesDetailsCleaned.csv";
		String archivo2 = "./data/MoviesCastingRaw-small.csv";
		String linea = "";
		String linea2 = "";
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			br.readLine();
			BufferedReader br2 = new BufferedReader(new FileReader(archivo2));
			br2.readLine();
			while((linea = br.readLine()) !=null && (linea2 = br2.readLine()) !=null)
			{
				String[] valores = linea.split(";");
				String[] valores2 = linea2.split(";"); 
				if(valores[0].equals(valores2[0]))
				{
					String[] fechaProduccion = valores[10].split("/");
					String añoProduccion = fechaProduccion[2];
					String llave = (valores[8]+"," + añoProduccion);

					Pelicula pelicula = new Pelicula((Integer.parseInt(valores[0])), ((String)valores[5]), valores[2], valores2[12], Float.parseFloat(valores[18]), Float.parseFloat(valores[17]),valores2[1],valores2[3],valores2[5],valores2[7],valores2[9],valores[8],añoProduccion);
					Hash key = new Hash(llave, tamañoLista); 
					ListaEncadenadaSinComparable<Pelicula> listaConLaPeli = new ListaEncadenadaSinComparable<Pelicula>();
					listaConLaPeli.agregarAlPrincipio(pelicula);
					
					separateChaining.put(key,pelicula);
			
					datos.agregarAlFinal(pelicula);
				}
			} 
			
			hayPeliculas = true; 
			
			ListaEncadenadaSinComparable<Hash> listaHash = new ListaEncadenadaSinComparable<Hash>();
			listaHash = separateChaining.keySet();
			for (int i = 0; i < listaHash.contarDatos(); i++) 
			{
				Hash act = listaHash.darElemento(i); 
				System.out.print("|| La llave en pos " + i + " es la llave: "  + act.darLlave());
			}

		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void cargarHashTableActores() 
	{
		separateChainingActores = new TablaHashSeparateChaining<Hash, Pelicula>(tamañoListaActores);
		String archivo = "./data/SmallMoviesDetailsCleaned.csv";
		String archivo2 = "./data/MoviesCastingRaw-small.csv";
		String linea = "";
		String linea2 = "";
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			br.readLine();
			BufferedReader br2 = new BufferedReader(new FileReader(archivo2));
			br2.readLine();
			while((linea = br.readLine()) !=null && (linea2 = br2.readLine()) !=null)
			{
				String[] valores = linea.split(";");
				String[] valores2 = linea2.split(";"); 
				if(valores[0].equals(valores2[0]))
				{
					String[] fechaProduccion = valores[10].split("/");
					String añoProduccion = fechaProduccion[2];
					String llave1 = (valores2[1]);
					String llave2 = (valores2[3]);
					String llave3 = (valores2[5]);
					String llave4 = (valores2[7]);
					String llave5 = (valores2[9]);
					
					Pelicula pelicula = new Pelicula((Integer.parseInt(valores[0])), ((String)valores[5]), valores[2], valores2[12], Float.parseFloat(valores[18]), Float.parseFloat(valores[17]),valores2[1],valores2[3],valores2[5],valores2[7],valores2[9],valores[8],añoProduccion);
					Hash key1 = new Hash(llave1, tamañoListaActores); 
					Hash key2 = new Hash(llave2, tamañoListaActores); 
					Hash key3 = new Hash(llave3, tamañoListaActores); 
					Hash key4 = new Hash(llave4, tamañoListaActores); 
					Hash key5 = new Hash(llave5, tamañoListaActores); 
					
					ListaEncadenadaSinComparable<Pelicula> listaConLaPeli = new ListaEncadenadaSinComparable<Pelicula>();
					listaConLaPeli.agregarAlPrincipio(pelicula);
					
					separateChainingActores.put(key1, pelicula);
					separateChainingActores.put(key2, pelicula);
					separateChainingActores.put(key3, pelicula);
					separateChainingActores.put(key4, pelicula);
					separateChainingActores.put(key5, pelicula);
					
					System.out.print(" || "+ llave1 + " || ");
				}
			} 
			
			hayPeliculas = true; 

		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Esta funcion ordena por conteo las peliculas
	 * @param tipo Tipo de ordenamiento, true para las peliculas mas votadas, false para las menos votadas
	 */
	public void ShellSortCount(boolean tipo) {
		Comparable[] peliculas = datos.elementos();
		shellsort.sortCount(peliculas,datos.contarDatos(),tipo);
		for (short i=0;i<10;i++) {
			ImprimirPelicula((Pelicula)peliculas[i]);
		}
	}
	
	/**
	 * Esta funcion ordena por conteo las peliculas
	 * @param tipo Tipo de ordenamiento, true para las peliculas mas votadas, false para las menos votadas
	 */
	public void ShellSortAverage(boolean tipo) {
		Comparable[] peliculas = datos.elementos();
		shellsort.sortAverage(peliculas,datos.contarDatos(),tipo);
		for (short i=0;i<10;i++) {
			ImprimirPelicula((Pelicula)peliculas[i]);
		}
	}
	
	public void ImprimirPelicula(int index) {
		controller.ImprimirPelicula((Pelicula)datos.darElemento(index));
	}
	
	public void ImprimirPelicula(Pelicula aImprimir) {
		controller.ImprimirPelicula(aImprimir);
	}
	
	public void darPeliculasActorHash(String pActor)
	{
		ArregloDinamico<String> pelis = new ArregloDinamico<String>(10);
		ArregloDinamico<String> directores = new ArregloDinamico<String>(10);
		String directorMasRepetido = null; 
		int numeroDirectoMasRepetido = 0; 
		float promedio = 0; 
		
		Hash actor = new Hash(pActor, tamañoListaActores);
		ListaEncadenadaSinComparable<Pelicula> listaPelisActor = ((TablaHashSeparateChaining<Hash, Pelicula>) separateChaining).getLista(actor);
		
		
		for (int i = 0; i < listaPelisActor.contarDatos(); i++) 
		{
			Pelicula act = (Pelicula) listaPelisActor.darElemento(i); 
			if(act.estaElActorEnLista(pActor)==true)
			{
				pelis.agregarAlFinal(act.darNombrePelicula());
				directores.agregarAlFinal(act.darNombreDirector());
				promedio+= act.darVotosPromedio(); 
			}
		}
		
		for (int i = 0; i < directores.contarDatos(); i++) 
		{
			int cantidadDeVecesRepetido = 1; 
			for (int j = i+1; j < directores.contarDatos(); j++) 
			{
				if(directores.darElemento(i).equalsIgnoreCase(directores.darElemento(j)))
				{
					cantidadDeVecesRepetido++; 
				}
			}
			if(cantidadDeVecesRepetido> numeroDirectoMasRepetido)
			{
				numeroDirectoMasRepetido = cantidadDeVecesRepetido;
				directorMasRepetido = directores.darElemento(i);
			}
		} 
		
		if (pelis.contarDatos()>0){
			System.out.println("----------");
			System.out.println("La cantidad de peliculas en las que ha actuado es de " + pelis.contarDatos());
			System.out.println("Las películas en las que actua son: ");
			for(int i=0;i<pelis.contarDatos();i++) {
				System.out.println(pelis.darElemento(i));	
			}
			System.out.println("----------");
			System.out.println("El promedio de votación de las peliculas en las que actua es de " + promedio/pelis.contarDatos());
			System.out.println("El director con el qué se han hecho más colaboraciones es " + directorMasRepetido);
		}
		else {
			System.out.println("----------");
			System.out.println("La persona dada no ha actuado en ninguna pelicula");
		}
	}
	
	public void darPeliculasGenero(String genero)
	{
		ArregloDinamico<String> pelis = new ArregloDinamico<String>(1000);
		float promedio = 0; 
		
		for (int i = 0; i < datos.contarDatos(); i++) 
		{
			Pelicula act = (Pelicula) datos.darElemento(i);
			if(act.darGenero().equalsIgnoreCase(genero))
			{
				pelis.agregarAlFinal(act.darNombrePelicula());
				promedio += act.darVotosPromedio(); 
			}
		}
		promedio = promedio/pelis.contarDatos(); 
		System.out.println("----------");
		System.out.println("Hay " + pelis.contarDatos() + " películas de ese género");
		if (pelis.contarDatos()>0){
			System.out.println("Las películas de ese género son: ");
			for(int i=0;i<pelis.contarDatos();i++) {
				System.out.println(pelis.darElemento(i));	
			}
			System.out.println("----------");
			System.out.println("El promedio de votación en esas peliculas es de " + promedio);
		}
	}
	
	public void darPeliculasActor(String pActor)
	{
		ArregloDinamico<String> pelis = new ArregloDinamico<String>(10);
		ArregloDinamico<String> directores = new ArregloDinamico<String>(10);
		String directorMasRepetido = null; 
		int numeroDirectoMasRepetido = 0; 
		float promedio = 0; 
		
		for (int i = 0; i < datos.contarDatos(); i++) 
		{
			Pelicula act = (Pelicula) datos.darElemento(i); 
			if(act.estaElActorEnLista(pActor)==true)
			{
				pelis.agregarAlFinal(act.darNombrePelicula());
				directores.agregarAlFinal(act.darNombreDirector());
				promedio+= act.darVotosPromedio(); 
			}
		};
		
		for (int i = 0; i < directores.contarDatos(); i++) 
		{
			int cantidadDeVecesRepetido = 1; 
			for (int j = i+1; j < directores.contarDatos(); j++) 
			{
				if(directores.darElemento(i).equalsIgnoreCase(directores.darElemento(j)))
				{
					cantidadDeVecesRepetido++; 
				}
			}
			if(cantidadDeVecesRepetido> numeroDirectoMasRepetido)
			{
				numeroDirectoMasRepetido = cantidadDeVecesRepetido;
				directorMasRepetido = directores.darElemento(i);
			}
		} 
		
		
		if (pelis.contarDatos()>0){
			System.out.println("----------");
			System.out.println("La cantidad de peliculas en las que ha actuado es de " + pelis.contarDatos());
			System.out.println("Las películas en las que actua son: ");
			for(int i=0;i<pelis.contarDatos();i++) {
				System.out.println(pelis.darElemento(i));	
			}
			System.out.println("----------");
			System.out.println("El promedio de votación de las peliculas en las que actua es de " + promedio/pelis.contarDatos());
			System.out.println("El director con el qué se han hecho más colaboraciones es " + directorMasRepetido);
		}
		else {
			System.out.println("----------");
			System.out.println("La persona dada no ha actuado en ninguna pelicula");
		}
	}
	
	public void darPeliculasDirector(String pDirector) {
		ArregloDinamico<String> pelis = new ArregloDinamico<String>(10);
		float promedio = 0; 
		for (int i = 0; i < datos.contarDatos(); i++) 
		{
			Pelicula act = (Pelicula) datos.darElemento(i); 
			if(act.darNombreDirector().compareToIgnoreCase(pDirector)==0)
			{
				pelis.agregarAlFinal(act.darNombrePelicula());
				promedio+= act.darVotosPromedio(); 
			}
		}
		if (pelis.contarDatos()>0){
			System.out.println("----------");
			System.out.println("El director ha dirigido " + pelis.contarDatos()+ " peliculas.");
			System.out.println("Las películas que ha dirigido son: ");
			for(int i=0;i<pelis.contarDatos();i++) {
				System.out.println(pelis.darElemento(i));	
			}
			System.out.println("----------");
			System.out.println("El promedio de votación de las peliculas en las que ha dirigido es de " + promedio/pelis.contarDatos());
		}
		else {
			System.out.println("----------");
			System.out.println("La persona dada no ha dirigido ninguna pelicula");
		}
	}
	
	public void CargarGrafo() throws ParseException
	{
		int total = 0;
		
		int notLoaded = 0;
		
		Path path = FileSystems.getDefault().getPath("data/", "2013-08-CitiBiketripdata.csv");
		Reader reader;
		
		try 
		{
			reader = Files.newBufferedReader(path);
			
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
				 
			CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build();
			
		    String[] line;
		    while ((line = csvReader.readNext()) != null && total < 1000) 
		    {
		    	if(line[0].equals("") || line[3].equals("")|| line[7].equals("")|| line[5].equals("")|| 
		    			line[6].equals("")|| line[9].equals("")|| line[10].equals("") || line[4].equals("")||
		    			line[8].equals("")||line[13].equals("N"))
		    	{
		    		notLoaded++;
		    	}
		    	else
		    	{
		    		Station sIn = new Station(line[4]);
		    		sIn.addBicicletas();
		    		int anio = Integer.parseInt(line[13]);
		    		int edad = 2020-anio;
		    		sIn.agregarEdad(edad);
		    		Station sTo = new Station(line[8]);
		    		sTo.addOutcicletas();
		    		sTo.agregarEdad(edad);
		    		if(grafo.containsVertex(Integer.parseInt(line[3])) == false && grafo.containsVertex(Integer.parseInt(line[7])) == false)
		    		{
			    	grafo.insertVertex(Integer.parseInt(line[3]), sIn);
			    	grafo.insertVertex(Integer.parseInt(line[7]), sTo);
			    	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
			    	Date d = sdf.parse(line[1]);
			    	Date d1 = sdf.parse(line[2]);
			    	Viaje v = new Viaje(Double.parseDouble(line[0]), Integer.parseInt(line[11]), d, d1);
			    	grafo.addEdge(Integer.parseInt(line[3]), Integer.parseInt(line[7]), v);
		    		}
		    		else
		    		{
		    			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
				    	Date d = sdf.parse(line[1]);
				    	Date d1 = sdf.parse(line[2]);
				    	Viaje v = new Viaje(Double.parseDouble(line[0]), Integer.parseInt(line[11]), d, d1);
				    	grafo.addEdge(Integer.parseInt(line[3]), Integer.parseInt(line[7]), v);
		    		}
			    	total++;
		    	}
		    	
		    }
		    reader.close();
		    csvReader.close();
		    
		    
		    path = FileSystems.getDefault().getPath("data/", "2013-07-CitiBiketripdata.csv");
		    
		    reader = Files.newBufferedReader(path);
			
			parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
				 
			csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build();

		    while ((line = csvReader.readNext()) != null && total < 1000) 
		    {
		    	if(line[0].equals("") || line[3].equals("")|| line[7].equals("")|| line[5].equals("")|| 
		    			line[6].equals("")|| line[9].equals("")|| line[10].equals("") || line[4].equals("")||
		    			line[8].equals("")||line[13].equals("N"))
		    	{
		    		notLoaded++;
		    	}
		    	else
		    	{
		    		Station sIn = new Station(line[4]);
		    		sIn.addBicicletas();
		    		int anio = Integer.parseInt(line[13]);
		    		int edad = 2020-anio;
		    		sIn.agregarEdad(edad);
		    		Station sTo = new Station(line[8]);
		    		sTo.addOutcicletas();
		    		sTo.agregarEdad(edad);
		    		if(grafo.containsVertex(Integer.parseInt(line[3])) == false && grafo.containsVertex(Integer.parseInt(line[7])) == false)
		    		{
			    	grafo.insertVertex(Integer.parseInt(line[3]), sIn);
			    	grafo.insertVertex(Integer.parseInt(line[7]), sTo);
			    	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
			    	Date d = sdf.parse(line[1]);
			    	Date d1 = sdf.parse(line[2]);
			    	Viaje v = new Viaje(Double.parseDouble(line[0]), Integer.parseInt(line[11]), d, d1);
			    	grafo.addEdge(Integer.parseInt(line[3]), Integer.parseInt(line[7]), v);
		    		}
		    		else
		    		{
		    			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
				    	Date d = sdf.parse(line[1]);
				    	Date d1 = sdf.parse(line[2]);
				    	Viaje v = new Viaje(Double.parseDouble(line[0]), Integer.parseInt(line[11]), d, d1);
				    	grafo.addEdge(Integer.parseInt(line[3]), Integer.parseInt(line[7]), v);
		    		}
		    		
			    	total++;
		    	}
		    	
		    }
		    
		    reader.close();
		    csvReader.close();
			
		    
		    System.out.println("Total viajes leidos: " + total);
		    System.out.println("Total viajes no leidos: " + notLoaded);
		    
		    System.out.println("Total de Estaciones: " + grafo.numVertices());
		    System.out.println("Total de Arcos: " + grafo.numEdges());
		    
		    double min = Double.POSITIVE_INFINITY;
		    double max = 0;
		    Edge rtaMin = null;
		    Edge rtaMax = null;
		    List<Edge<Integer, Station>> lista = grafo.edges().valueSet();
		    for(int i = 0; i< lista.size(); i++)
		    {
		    	Edge<Integer, Station> e = lista.get(i);
		    	if(e.getViaje().getTime() < min)
		    	{
		    		min = e.getViaje().getTime();
		    		rtaMin = e;
		    	}
		    	if(e.getViaje().getTime()  > max)
		    	{
		    		max = e.getViaje().getTime();
		    		rtaMax = e;
		    	}
		    	
		    }
		    
		    System.out.println("Arco con peso mínimo: " + min);
		    System.out.println("Vertices que conecta: " + rtaMin.getSource().getId() + " a " + rtaMin.getDest().getId());
		    System.out.println("Arco con peso máximo: " + max);
		    System.out.println("Vertices que conecta: " + rtaMax.getSource().getId() + " a " + rtaMax.getDest().getId());
		    
		    
		} 
		catch (IOException | NumberFormatException | CsvValidationException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void gradoInOut(Integer id)
	{		
		System.out.println("Grado de entrada: " + grafo.indegree(id));
		System.out.println("Grado de salida: " + grafo.outdegree(id));
	}
	
	public ArrayList req3()
	{
		Random r = new Random(100);
		ArrayList rta = new ArrayList();
		
		RedBlackBST<Integer, Integer> maxEnter = new RedBlackBST<Integer, Integer>();
		RedBlackBST<Integer, Integer> maxExit = new RedBlackBST<Integer, Integer>();
		RedBlackBST<Integer, Integer> minComb = new RedBlackBST<Integer, Integer>();
		
		List<Vertex<Integer, Station>> lista = grafo.vertices().valueSet();
		for(int i = 0; i< lista.size(); i++)
		{
			Station s = (Station) lista.get(i).getValor();
			int in = s.getBicicletas() + r.nextInt();
			int out = s.getOutcicletas() + r.nextInt();
			int comb = in + out;
			
			maxEnter.put(in, (Integer) lista.get(i).getId());
			maxExit.put(out, (Integer) lista.get(i).getId());
			minComb.put(comb, (Integer) lista.get(i).getId());
				
		}
		
		for(int i = 0; i < 3; i++)
		{
			Station s = (Station) grafo.getVertex(maxEnter.get(maxEnter.max())).getValor();
			rta.add(s.getNombre());
			maxEnter.deleteMax();
			
		}
		
		for(int i = 0; i < 3; i++)
		{
			Station s1 = (Station) grafo.getVertex(maxExit.get(maxExit.max())).getValor();
			rta.add(s1.getNombre());
			maxExit.deleteMax();
		}
		
		for(int i = 0; i < 3; i++)
		{
			Station s2 = (Station) grafo.getVertex(minComb.get(minComb.min())).getValor();
			rta.add(s2.getNombre());
			minComb.deleteMin();
		}
		
		return rta;
	}
	
	public int cantidadClusters()
	{
		int rta = 0;
		rta = grafo.kosarajuSCC(); 
		return rta;
	}
	
	public ArrayList req8(int id, Date d)
	{
		ArrayList rta = new ArrayList();
		
		Set tmp = new HashSet();
		
		long uso = 0;
		
		int noUso = 24*60;
		
		for(Edge e: grafo.edgesIter())
		{
			Viaje v = e.getViaje();
			
			if(v.getBikeId() == id && v.getStart().getMonth() == d.getMonth() && 
					v.getStart().getDay() == d.getDay())
			{
				tmp.add(e.getSource());
				tmp.add(e.getDest());
				long diff = Math.abs(v.getStart().getTime() - v.getStop().getTime());
				uso += TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
			}
		}
		
		noUso -= uso;
		
		rta.add(uso);
		rta.add(noUso);
		rta.add(tmp);
		
		return rta;
	}
	
	public boolean mismoIdCC(int id1, int id2)
	{
		boolean rta = grafo.mismoIdCC(id1, id2);
		return rta; 
	}
	
}
