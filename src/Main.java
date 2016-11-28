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

    public Vertex(char letter){
        this.letter = letter;
    }

    public char getLetter(){
        return letter;
    }

    public void printEdges(){

        while(edges.hasNext()){
            Edge temp = edges.next();
            System.out.print(letter + " "+ temp.getWeight() + " "+ temp.getVertex().getLetter()+ " , ");
        }
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
