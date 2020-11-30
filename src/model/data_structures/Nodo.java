package model.data_structures;

public class Nodo {

	private Object key;
	private Object valor;
	private Nodo siguiente;

	public Nodo(Object pkey, Object value) {
		this.key = pkey;
		this.valor = value;
	}
	
	public void cambiarValor(Object pDato) {valor = pDato;}

	public Object darValor() { return valor; }
	
	public Object darKey() { return key; }
	
	public Nodo darSiguiente() {return siguiente;}
	
	public void setSiguiente (Nodo pSiguiente) {siguiente = pSiguiente;}
	
	public boolean tieneSiguiente () {
		if(siguiente==null) return false;
		else { return true;}
	}
	

}
