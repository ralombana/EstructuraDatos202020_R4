package clases;

import clases.Pelicula;
import model.data_structures.ArregloDinamico;



public class Director implements Comparable<Director>
{

	private String director;
	private int id ;
	private ArregloDinamico<Pelicula>movies;




	public Director(int elId, String elDirector )
	{
		id = elId;
		director = elDirector;
		movies = new ArregloDinamico<Pelicula>(100);

	}

	public int darId(){
		return id;
	}

	public String darDirector(){
		return director;
	}

	public void agregarPelicula (Pelicula d){
		movies.agregarAlFinal(d);;
	}


	public ArregloDinamico<Pelicula> darPeliculas(){
		return movies;
	}

	public double promedioCalificacionPeliculas(){
		double prom= 0;
		int x = movies.darCapacidad();
		for(int i=0; i < movies.darCapacidad();i++){
			Pelicula actual = movies.darElemento(i);
			prom += actual.darVotosPromedio();
			prom= prom/x;
		}
		return prom;
	}
	@Override
	public int compareTo(Director o) 
	{
		return (id - o.darId());
	}


}