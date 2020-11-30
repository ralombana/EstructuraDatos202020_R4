package clases;

public class Hash 
{
	
	String llave;
	int posicion;
	int tamaño = 0;
	int sigPrimo = siguientePrimo(tamaño);
	
	public Hash(String pLlave, int pTamaño)
	{
		llave = pLlave;
		tamaño = pTamaño; 
		posicion = funcionHash(llave, tamaño, sigPrimo);
	}
	
	public String darLlave()
	{
		return llave;
	}
	
	public int darPosicion()
	{
		return posicion;
	}
	
	public int funcionHash(String llaveACambiar, int tamañoLista, int tamañoSiguientePrimo)
	{
		int rta = Math.abs(llaveACambiar.hashCode());
		rta = ((rta*darNumeroAlAzar(tamañoSiguientePrimo)+ darNumeroAlAzar(tamañoSiguientePrimo))% tamañoSiguientePrimo)%tamañoLista;
		if(rta <0)
		{
			rta*= -1;
		}
		return rta;
	}
	
	public int siguientePrimo(int num) 
	{
		num++;
		for (int i = 2; i < num; i++) 
		{
			if(num%i == 0) 
			{
				num++;
				i=2;
			} else 
			{
				continue;
			}
		}
		return num;
	}
	
	   
	public int darNumeroAlAzar(int tamañoSiguientePrimo){ 
     int max = tamañoSiguientePrimo; 
     int min = 1; 
     int range = max - min + 1; 
     int rta = 0;

     for (int i = 0; i < tamañoSiguientePrimo; i++) 
     { 
         rta = (int)(Math.random() * range) + min; 
     } 
    return rta;
 }
	
}
