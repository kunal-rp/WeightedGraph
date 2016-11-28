import java.util.ArrayList;
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
    private ArrayList<Edge> edges ;
    private boolean visited;

    public Vertex(char letter){
        this.letter = letter;
        visited = false;
    }

    public char getLetter(){
        return letter;
    }

    public boolean isVisited(){
        return visited;
    }

    public void visit(){
        visited = true;
    }

    public void printEdges(){
        Iterator<Edge> edgelist = edges.iterator();
        while(edgelist.hasNext()){
            Edge temp = edgelist.next();
            System.out.print(letter + " "+ temp.getWeight() + " "+ temp.getVertex().getLetter()+ " , ");
        }
    }

    public boolean addEdge(Vertex vertex, int weight){
        boolean result = true;
        Iterator<Edge> edgelist = edges.iterator();
        boolean duplicate = false;
        while(edgelist.hasNext() && duplicate == false){
            Edge temp = edgelist.next();
            if(temp.getWeight() == weight && temp.getVertex().getLetter() == vertex.getLetter()){
                duplicate = true;
            }
        }
        if(duplicate == true){
            result = false;
        }
        else{
            edges.add(new Edge(vertex, weight));
        }
        return result;
    }

    public boolean hasEdges(){
        return edges.isEmpty();
    }

    public boolean hasNonvisitedEdge(){
        boolean result = false;
        Iterator<Edge> edgelist = edges.iterator();
        while(edgelist.hasNext() ) {
            Edge temp = edgelist.next();
            if(temp.getVertex().isVisited() == false){
                result = true;
                break;
            }
        }
        return result;
    }

}

class Edge{

    private Vertex vertex;
    private int weight;

    public Edge(Vertex vertex, int weight){
        this.vertex = vertex;
        this.weight = weight;
    }

    public int getWeight(){
        return weight;
    }

    public Vertex getVertex(){
        return vertex;
    }
}
