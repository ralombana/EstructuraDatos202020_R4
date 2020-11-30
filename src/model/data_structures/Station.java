package model.data_structures;

public class Station implements Comparable<Station>
{
	
	private String nombre;
	
	private int bicicletas;
	
	private int outcicletas;
	
	private int[] edades = new int[7];
	
	public Station(String nombre)
	{
		this.nombre = nombre;
		this.bicicletas = 0;
		this.outcicletas = 0;
	}
	
	public void agregarEdad(int edad)
	{
		if(edad < 11)
			edades[0]++;
		else if(edad < 21)
			edades[1]++;
		else if(edad < 31)
			edades[2]++;
		else if(edad < 41)
			edades[3]++;
		else if(edad < 51)
			edades[4]++;
		else if(edad < 61)
			edades[5]++;
		else
			edades[6]++;
	}
	
	public void addBicicletas()
	{
		bicicletas++;
	}
	public void addOutcicletas()
	{
		outcicletas++;
	}

	public int[] getEdades() {
		return edades;
	}

	public String getNombre()
	{
		return nombre;
	}
	
	public int getBicicletas() {
		return bicicletas;
	}
	
	public int getOutcicletas() {
		return outcicletas;
	}

	@Override
	public int compareTo(Station o) 
	{
		// TODO Auto-generated method stub
		return o.getNombre().compareTo(this.getNombre());
	}
	
	

}
