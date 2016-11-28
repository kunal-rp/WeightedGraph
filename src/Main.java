import java.util.Iterator;

/**
 * Created by KP Admin on 11/28/2016.
 */
public class Main {

    public static void main(String args[]){


    }

}
class Vertex{

    private char letter;
    private Iterator<Edge> edges;

}

class Edge{

    private Vertex vertex;
    private int weight;

    public Edge(Vertex vertex, int weight){
        this.vertex = vertex;
        this.weight = weight;
    }
}
