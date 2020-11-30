package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import clases.Pelicula;
import model.data_structures.Station;
import model.data_structures.Vertex;
import model.logic.Modelo;
import view.View;
import java.util.Set;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;

	/* Instancia de la Vista*/
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo(this);
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		try {
			while( !fin ){
				view.printMenu();
				int option = lector.nextInt();
				switch(option){

				case 1:
					view.printMessage("--------- \nCargando Grafo...");
					modelo = new Modelo(this);
					modelo.CargarGrafo();
					view.printMessage("--------- \nGrafo Cargado!");
					break;

				case 2:
					view.printMessage("Estaciones críticas");
					
					ArrayList rta = modelo.req3();
					for(int i = 0; i < rta.size(); i++)
					{
						view.printMessage(rta.get(i)+"");
					}

				case 3:
					view.printMessage("Cantidad de Clusters en el grafo");
					view.printMessage("Cargando...");
					int clusters = modelo.cantidadClusters(); 
					view.printMessage("La cantidad de Clusters en este grafo es de: " + clusters + " clusters");
				
				case 4:	
					view.printMessage("Están dos estaciones fuertemente conectadas? ");
					view.printMessage("Escriba el ID primera estación");
					lector.nextLine();
					int id1 = lector.nextInt();
					view.printMessage("Escriba el ID segunda estación");
					int id2 = lector.nextInt();
					boolean sonIguales = modelo.mismoIdCC(id1, id2); 
					if(sonIguales)
					{
						view.printMessage("Los dos componentes están fuertemente conectados");
					}
					else
					{
						view.printMessage("Los dos componentes no están fuertemente conectados");
					}
					
				case 5:
					view.printMessage("Identificación de Bicicletas");
					view.printMessage("Ingrese id de la bicicleta y fecha a buscar en el formato AAAA-MM-DD separado por comas");
					lector.nextLine();
					String s = lector.nextLine();
					String[] ss = s.split(",");
					int id = Integer.parseInt(ss[0]);
					SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
			    	Date d = sdf.parse(ss[1]);
			    	ArrayList rta1 = modelo.req8(id, d);
			    	
			    	view.printMessage("Tiempo de uso: " + rta1.get(0) + " minutos");
			    	view.printMessage("Tiempo sin usar: " + ((int)rta1.get(1)/60) + " horas");
			    	
			    	Set a = (Set) rta1.get(2);
			    	
					for(Object o: a)
					{
						Vertex v = (Vertex) o;
						Station s1 = (Station) v.getInfo();
						view.printMessage(s1.getNombre());
					}
				}
			}
		}
		catch(Exception e) {
			view.printMessage("--------- \n Error!! \n---------");
			e.printStackTrace();
			run();
		}
	}
	
	public void ImprimirPelicula(Pelicula aImprimir) {
		if (aImprimir != null) {
			view.printMessage("----------");
			view.printMessage("ID:"+aImprimir.darIdentificador());
			view.printMessage("Nombre:"+aImprimir.darNombrePelicula());
			view.printMessage("Votos:"+(int)aImprimir.darCantidadVotos());
			view.printMessage("Promedio de Votacion:"+aImprimir.darVotosPromedio());
			view.printMessage("Genero:"+aImprimir.darGenero());
			view.printMessage("Actores:");
			String[] actores = aImprimir.darListaNombresActores();
			for (int i =0;i<5;i++) {
				view.printMessage(actores[i]);
			}
		}
		else {
			view.printMessage("Ocurrio un errror, revise que el indice dado sea menor al tamaño de la lista");
		}
	}
}
