package model.data_structures;


public interface IDiGraph<K extends Comparable<K>,V extends Comparable<V>> 
{
    /**
     * Busca si el vertice con el id suministrado esta en el grafo.
     * @param id el identificador del vertice
     * @return True si el vertice con el id dado por parametro esta en el grafo, False de lo contrario
     */
    boolean containsVertex(K id);

    /**
     * Retorna el numefro de vertices que hay en el grafo
     * @return Un int con el numero de vertices del grafo
     */
    int numVertices();

    /**
     * Devuelve el numero de Arcos/Aristas/Edges que hay en el grafo
     * @return Un int con el numero de Edges/Aristas del grafo
     */
    int numEdges();

    /**
     * Añade un vértice al grafo con su identificador(id) y valor(value).
     * @param id el identificador de un vertice
     * @param value el valor de un vertice
     */
    void insertVertex(K id, V value);

    /**
     *Añade un Edge(arco) dirigido pesado entre el vértice source y destino, con un peso weight. Si el Edge(arco) YA existe se modifica su peso.
     * @param source El vertice de origen
     * @param dest El vertice destino
     * @param weight El peso del Edge entre el vertice origen y destino
     */
    void addEdge(K source, K dest, double weight);

    /**
     * Retorna el vértice a partir de su identificador único.
     * @param id el identificador de un vertice
     * @return El vertice con el id dado por parametro
     */
    Vertex<K,V> getVertex(K id);

    /**
     * Retorna el Edge(arco) entre los vértices idSource y idDestino (si existe). Retorna null si no existe.
     * @param idS el identificador del vertice de origen
     * @param idD el identificador del vertice destino
     * @return El Edge(arco) entre dos vertices con los identificadores idS y idD.
     */
    Edge<K,V> getEdge(K idS, K idD);

    /**
     * Devuelve una lista de Edges(arcos) adyacentes (salientes) al vértice con id
     * @param id el identificador del vertice Base
     * @return Una lista que contiene todos los edges adjacentes a un vertice con un id dado
     */
    ListaEncadenadaSinComparable<Edge<K, V>> adjacentEdges(K id);

    /**
     * Devuelve una lista de vértices adyacentes (salientes) al vértice con id
     * @param id el identificador del vertice Base
     * @return Una lista que contiene todos los vertices adjacentes a un vertice correspondiente al id dado
     */
    ListaEncadenadaSinComparable<Vertex<K, V>> adjacentVertex(K id);

    /**
     * Devuelve el grado de entrada del vértice vertex (número de edges entrantes)
     * @param vertex el vertice dado
     * @return Un int que contiene el grado de entrada del vertice dado por parametro. (cuantos edges se dirigen a el)
     */
    int indegree(K vertex);

    /**
     * Devuelve el grado de salida del vértice vertex (número de edges salientes)
     * @param vertex el vertice dado
     * @return Un int que contiene el grado de salida del vertice dado por parametro. (cuantos edges salen de el)
     */
    int outdegree(K vertex);

    /**
     * Devuelve una lista con todos los edges del grafo
     * @return Una lista que ontiene todos los edges del grafo
     */
    TablaHashLinearProbing2<K, Edge<K, V>> edges();

    /**
     * Devuelve una lista con los vértices del grafo
     * @return
     */
    TablaHashLinearProbing2<K, Vertex<K,V>> vertices();
}
