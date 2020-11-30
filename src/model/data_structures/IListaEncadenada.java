package model.data_structures;

import model.data_structures.ListaEncadenada.Nodo;

public interface IListaEncadenada<T extends Comparable<T>> {
	
	int contarDatos();
	
	T darPosicionDatos(int pos);
	
	T darPrimerElemento();
	
	T darUltimoElemento();
	
	boolean existeEnLaLista();
	
	T borrar(T dato);

	void insert(T dato);
	
	T buscar(T dato);
	
	void agregarAlFinal(T dato);
	
	T darElemento(int index);
	
	Comparable[] elementos();

}
