package model.data_structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import model.data_structures.*;
public class DiGraph2<K extends Comparable<K>, V extends Comparable<V>> {

	TablaHashLinearProbing2<Integer, Vertex> hashVertices;
	TablaHashLinearProbing2<Integer, Edge> hashEdges;
	TablaHashLinearProbing2<Integer, Vertex> visited;
	TablaHashLinearProbing2<Integer, Vertex> notVisited;
	TablaHashSeparateChaining<Integer, Vertex> listaIdCC = new TablaHashSeparateChaining<Integer, Vertex>(15000000); 
	QueueGenerica<K> pre = new QueueGenerica<K>(); 
	QueueGenerica<K> pos = new QueueGenerica<K>(); 
	StackGenerico<K> reversePos = new StackGenerico<K>(); 
	int preCounter= 0;
	int posCounter= 0; 
	int elIdAct; 
	

    public DiGraph2()
    {
    	hashVertices = new TablaHashLinearProbing2(1500000);
    	hashEdges = new TablaHashLinearProbing2(3500000);
    }

    public DiGraph2<K, V> reverse()
    {
    	DiGraph2<K, V> invertido = new DiGraph2<K, V>();
    	invertido = this;
    	List<Vertex<K, V>> listaVertices = vertices().valueSet();
    	if(listaVertices.size()!=0)
    	{
    		for (int i = 0; i < listaVertices.size(); i++) 
    		{
    			Vertex<K, V> vertexAct = listaVertices.get(i); 
    			vertexAct.revertAllEdges();
    		}
    	}
    	return invertido; 
    }

    public boolean hashPathDFS(K source, K destino)
    {
    	Vertex<K, V> sour = getVertex(source);
    	Vertex<K, V> dest = getVertex(destino);
    	TablaHashLinearProbing2<K, Vertex<K, V>> visitados = new TablaHashLinearProbing2<>(1500000);
    	return hayUnCamino(sour, dest, visitados);
    }

    private boolean hayUnCamino(Vertex<K, V> sour, Vertex<K, V> dest,
			TablaHashLinearProbing2<K, Vertex<K, V>> visitados) 
    {
    	if(visitados.contains(sour.id))
    	{
    		return false;
    	}
    	visitados.put(sour.getId(), sour);
    	if(sour == dest)
    	{
    		return true; 
    	}
    	ListaEncadenadaSinComparable<Vertex<K, V>> adjacentesSour = adjacentVertex(sour.getId()); 
    	for (int i = 0; i < adjacentesSour.contarDatos(); i++) 
    	{
			if(hayUnCamino(adjacentesSour.darElemento(i), dest, visitados))
			{
				return true; 
			}
		}
    	return false; 
    }

	public boolean containsVertex(K id) 
    {
        return hashVertices.contains((Integer) id);
    }
    
    public void dfsStack(K idP,  int idCC) 
    {
    	Vertex<K, V> verticeAct = getVertex(idP);
        if(visited.contains((Integer) idP)== false && verticeAct != null && verticeAct.idCC == 0)
        {
        	verticeAct.mark();
        	visited.put((Integer) idP, verticeAct);
        	listaIdCC.put(idCC, getVertex(idP));
        	verticeAct.idCC = idCC; 
        	ListaEncadenadaSinComparable<Vertex<K, V>> adyacentes = adjacentVertex(idP); 
        	for (int i = 0; i < adyacentes.contarDatos(); i++) 
        	{
				dfsStack(adyacentes.darElemento(i).getId(), idCC);
				elIdAct++; 
			}
        	//System.out.println("El vertice de llave " + idP + " tiene idCC " + idCC);
        }
       
       
    }
    
    public void dfsQueues(K idP) 
    {
        Vertex<K, V> verticeAct = getVertex(idP);
        
        
        if(verticeAct.getMark() == false)
        {
        	verticeAct.mark();
        	visited.put((Integer) idP, verticeAct);
        	pre.enqueue(idP);
        	ListaEncadenadaSinComparable<Vertex<K, V>> adyacentes = adjacentVertex(idP); 
        	if(adyacentes!=null)
        	{
        		for (int i = 0; i < adyacentes.contarDatos(); i++) 
        		{
        		
        			if(adyacentes.darElemento(i)!= null)
        			{
        				dfsQueues(adyacentes.darElemento(i).getId());
        			}
        		}
        		pos.enqueue(idP);
        		reversePos.push(idP);
        	}
        	
        }
        
    }
    
    public void dfo() 
    {
    	visited = new TablaHashLinearProbing2<Integer, Vertex>(1500000); 
    	List<Vertex> listaVertices = notVisited.valueSet(); 
    	for (int i = 0; i < listaVertices.size(); i++) 
    	{
			if(visited.contains((Integer) listaVertices.get(i).getId())== false)
			{
    		dfsQueues((K)listaVertices.get(i).getId());
			}
		}
    }
    
    public void dfoAlStack(StackGenerico<K> elStack) 
    {
    	elIdAct = 1;
    	visited = new TablaHashLinearProbing2<Integer, Vertex>(1500000); 
    	List<Vertex> listaVertices = notVisited.valueSet(); 
    	
    	for (int i = 0; i < listaVertices.size(); i++) 
    	{
    		Vertex vertice = listaVertices.get(i); 
    		if(visited.contains((Integer) listaVertices.get(i).getId()) && listaVertices.get(i).edges().valueSet().isEmpty()==false)
    		{
    			for (int j = 0; j < listaVertices.size(); j++) 
    			{
    				visited.get(i).addEdge((Edge) listaVertices.get(i).edges().get(i));
    			}
    		}
    	}
		
    	while(elStack.top()!= null)
    	{
    		K llave = elStack.pop();
    		if(visited.contains((Integer) llave)== false)
    		{
    			dfsStack(llave, elIdAct);	
    		}
    	}
        
    }
    
    public void unmarkAllVertex()
    {
    	List<Vertex<K, V >> vertices = vertices().valueSet(); 
    	for (int i = 0; i < vertices.size(); i++) 
    	{
    		vertices.get(i).unMark();
    		visited.remove((Integer) vertices.get(i).getId()); ; 
		}
    	
    }

   
    public int kosarajuSCC()
    {
    	int rta = 0;
    	DiGraph2<K, V> revertida = reverse(); 
    	notVisited = this.vertices(); 
    	revertida.dfo();
    	unmarkAllVertex();
    	dfoAlStack(reversePos);
    	rta = elIdAct; 
    	return rta; 
    }
    
    public boolean mismoIdCC(K id1, K id2)
    {
    	boolean rta = false;
    	if(getVertex(id1).idCC == getVertex(id2).idCC)
    	{
    		rta = true; 
    	}
    	return rta;
    }
    
    public ListaEncadenadaSinComparable<K> allWithTheSameIdCC()
    {
    	ListaEncadenadaSinComparable<K> rta = new ListaEncadenadaSinComparable<K>(); 
    	return rta; 
    }

    public int numVertices() 
    {
        return hashVertices.size();
    }


    public int numEdges() 
    {
        return hashEdges.size();
    }


    public void insertVertex(K id, V value) 
    {
    	if(containsVertex(id)== false)
    	{
    		if(hashVertices.contains((Integer) id) == false)
    		{
    			Vertex<K,V> vertex = new Vertex<K, V>(id, value);
    			hashVertices.put((Integer) id, vertex);
    		}
    	}
    }
    
    public void insertFullVertex(Vertex<K, V> vertex) 
    {
        hashVertices.put((Integer) vertex.getId(), vertex);
    }

    
    public void addEdge(K source, K dest, Viaje weight) 
    {
    	if(containsVertex(source)  && containsVertex(dest))
    	{
        Edge edge = new Edge(getVertex(source), getVertex(dest), weight);
        if(!hashEdges.contains(numEdges()) && edge != null)
        {
        	getVertex(source).addEdge(edge);
        	Station s = (Station) getVertex(source).getInfo();
        	s.addBicicletas();
        	getVertex(source).setValor((V) s);
        	getVertex(dest).addEdge(edge);
        	Station s1 = (Station) getVertex(dest).getInfo();
        	s1.addOutcicletas();
        	getVertex(dest).setValor((V) s1);
        	hashEdges.put(numEdges(), edge);
        }
    	}
    }

    public Vertex<K, V> getVertex(K id)
    {
        return hashVertices.get((Integer) id); 
    }
      

    public Edge<K, V> getEdge(K id) {
    	return hashEdges.get((Integer) id); 
    }


    public ListaEncadenadaSinComparable<Edge<K, V>> adjacentEdges(K id) 
    {
    	Vertex<K, V> ver= this.getVertex(id);
        List<Edge<K, V>> list= ver.edges().valueSet();
        ListaEncadenadaSinComparable<Edge<K, V>> rta = new ListaEncadenadaSinComparable<Edge<K,V>>();
        for (int i = 0; i < list.size(); i++) 
        {
        	Edge<K, V> act = list.get(i); 
        	if(act.getDest().getId()== id)
        	{
        		rta.agregarAlPrincipio(act);
        	}
		}
        return rta;
    }

    public ListaEncadenadaSinComparable<Vertex<K,V>> adjacentVertex(K id)
    {
    	ListaEncadenadaSinComparable<Edge<K, V>> list= adjacentEdges(id);
    	ListaEncadenadaSinComparable<Vertex<K,V>> vertexLista= new ListaEncadenadaSinComparable<Vertex<K,V>>();
        for(int i=0; i<list.contarDatos(); i++ )
        {
            vertexLista.agregarAlPrincipio(list.darElemento(i).getDest());
        }
        return vertexLista;
    }
    
    public int indegree(K vertex) {
        return getVertex(vertex).inDegrees();
    }

    public int outdegree(K vertex) {
        return getVertex(vertex).outDegrees();
    }

    public TablaHashLinearProbing2 edges() {
        return hashEdges;
    }


    public TablaHashLinearProbing2 vertices() {
        return hashVertices;
    }

    public List<Edge> edgesIter()
    {
    	return hashEdges.valueSet();
    }
}
