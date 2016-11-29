import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by KP Admin on 11/28/2016.
 */
public class Main {

    public static void main(String args[]){

        Graph graph = new Graph();
        graph.add('A');
        graph.add('B');
        graph.add('C');
        graph.addEdge('A','B',3);
        graph.addEdge('B','C',4);
        graph.addEdge('B','A',4);
        graph.print();
        graph.remove('C');
        graph.print();
    }

}

class Graph{

    private ArrayList<Vertex> vertexes = new ArrayList<Vertex>();

    public void add(char letter){
        Vertex vertex = new Vertex(letter);
        vertexes.add(vertex);
    }

    public void addEdge(char startChar, char endChar, int weight){

        Vertex start = new Vertex('/');
        Vertex end = new Vertex('/');
        Iterator<Vertex> vertexList = vertexes.iterator();
        while(vertexList.hasNext()){
            Vertex temp = vertexList.next();
            if(temp.getLetter() == startChar){
                start = temp;
            }
            if(temp.getLetter() == endChar){
                end = temp;
            }
        }
        start.addEdge(end, weight);
    }

    public void remove(char letter){
        Vertex vertex = new Vertex(letter);
        Iterator<Vertex> vertexList = vertexes.iterator();
        while(vertexList.hasNext()){
            Vertex temp = vertexList.next();
            if(temp.getLetter() == vertex.getLetter()){
                vertexList.remove();
            }
            else{
                temp.removeEdge(vertex);
            }
        }

    }

    public void print(){
        Iterator<Vertex> vertexList = vertexes.iterator();
        while(vertexList.hasNext()){
            Vertex temp = vertexList.next();
            temp.printEdges();
            System.out.println();
        }

    }


}
class Vertex{

    private char letter;
    private ArrayList<Edge> edges = new ArrayList<Edge>() ;
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

    public void removeEdge(Vertex vertex){
        Iterator<Edge> edgelist = edges.iterator();
        while(edgelist.hasNext()){
            Edge temp = edgelist.next();
            if(temp.getVertex().getLetter() == vertex.getLetter()){
                edgelist.remove();
                break;
            }
        }
    }

    public void printEdges(){
        System.out.print(letter + " :");
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

    public Vertex getUnvisitedVertex(){
        Vertex result = new Vertex('/');
        Iterator<Edge> edgelist = edges.iterator();
        while(edgelist.hasNext() ) {
            Edge temp = edgelist.next();
            if(temp.getVertex().isVisited() == false){
                result = temp.getVertex();
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
