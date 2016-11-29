import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by KP Admin on 11/28/2016.
 */
public class Main {

    public static void main(String args[]){

        Graph graph = new Graph();
        graph.add('A');
        graph.add('B');
        graph.add('C');
        graph.add('D');
        graph.add('E');
        graph.add('F');
        graph.add('G');
        graph.add('H');
        graph.add('K');
        graph.add('L');
        graph.add('M');
        graph.add('N');
        graph.addEdge('A','B',2);
        graph.addEdge('B','C',3);
        graph.addEdge('C','D',2);
        graph.addEdge('E','A',2);
        graph.addEdge('B','F',1);
        graph.addEdge('C','G',2);
        graph.addEdge('E','F',2);
        graph.addEdge('G','F',2);
        graph.addEdge('G','H',3);
        graph.addEdge('H','N',2);
        graph.addEdge('K','E',1);
        graph.addEdge('K','L',3);
        graph.addEdge('L','M',1);
        graph.addEdge('M','N',1);
        graph.addEdge('F','L',1);

        String command = "";
        Scanner in = new Scanner(System.in);
        while(!command.equals("Q")) {

            System.out.println("Enter Command[PE = print edges,PP = print paths, A = add vertex, D = delete Vertex , E = add edge,Q = quit ]: ");
            command = in.next();
            switch (command){
                case "PE":graph.print();break;
                case "PP":graph.printOutPathLengths('A');break;
                case "A":System.out.print("Letter to add:");
                    char letter = in.next().charAt(0);
                    graph.add(letter);
                    break;
                case "D":System.out.print("Letter to remove:");
                    letter = in.next().charAt(0);
                    graph.remove(letter);
                    break;
                case "E":System.out.print("Edge to Add:");
                    String string = in.next();
                    letter = string.charAt(0);
                    int weight = Integer.parseInt(String.valueOf(string.charAt(1)));
                    char letter2 = string.charAt(2);
                    graph.addEdge(letter,letter2,weight);
                    break;
            }
        }



        graph.printOutPathLengths('A');
        System.out.println("---------");
        graph.printOutPathLengths('C');


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
        while(vertexList.hasNext()&& (start.getLetter() == '/' || end.getLetter() == '/' )){
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


   public void printOutPathLengths(char letter) {

       Vertex originVertex = new Vertex('/');
       Iterator<Vertex> vertexList = vertexes.iterator();
       while (vertexList.hasNext()) {
           Vertex temp = vertexList.next();
           temp.setCost(0);
           temp.setParent(null);
           temp.resetVisit();
           if (temp.getLetter() == letter) {
               originVertex = temp;
           }

       }

       System.out.println("Paths from "+ originVertex.getLetter()+" :");
       PQueue pq = new PQueue();
       pq.add(originVertex);
       while (!pq.isEmpty()) {

           Vertex currentVertex = pq.pop();

           if(currentVertex.getLetter() != originVertex.getLetter()){
           System.out.println("to "+currentVertex.getLetter() + " : "+ currentVertex.getCost());}
           while (currentVertex.hasNonvisitedEdge()) {
               Vertex frontVertex =  currentVertex.getUnvisitedEdge().getVertex();
               int tempCost = currentVertex.getUnvisitedEdge().getWeight();

               if(frontVertex.getCost() != 0){
                    if(tempCost + currentVertex.getCost() < frontVertex.getCost()){
                        frontVertex.setCost(tempCost + currentVertex.getCost());
                        frontVertex.setParent(currentVertex);
                    }

               }
               else {

                   frontVertex.setParent(currentVertex);
                   frontVertex.setCost(tempCost);
                   if (frontVertex.getParent() != null) {
                       frontVertex.setCost(frontVertex.getCost() + frontVertex.getParent().getCost());
                   }
                   frontVertex.visit();
                   pq.add(frontVertex);
               }
           }

       }
       vertexList = vertexes.iterator();
       while (vertexList.hasNext()) {
           Vertex temp = vertexList.next();
           if (temp.isVisited() == false && temp.getLetter() != originVertex.getLetter()) {
              System.out.println("to "+temp.getLetter() + " :not reachable");
           }
       }


   }


}
class Vertex{

    private char letter;
    private ArrayList<Edge> edges = new ArrayList<Edge>() ;
    private boolean visited;
    private Vertex parent;
    private int cost;

    public Vertex(char letter){
        this.letter = letter;
        visited = false;
        parent = null;
    }

    public void setParent(Vertex parent){
        this.parent = parent;
    }

    public Vertex getParent(){
        return parent;
    }

    public int getCost(){
        return cost;
    }

    public void setCost(int cost){
        this.cost = cost;
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

    public void resetVisit(){
        visited = false;
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

    public Edge getUnvisitedEdge(){
        Edge result = new Edge(new Vertex('/'),0);
        Iterator<Edge> edgelist = edges.iterator();
        while(edgelist.hasNext() ) {
            Edge temp = edgelist.next();
            if(temp.getVertex().isVisited() == false){
                result = temp;
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

class PQueue{

    private Node head = null;

    public Vertex peek(){
        return head.getVertex();
    }

    public boolean isEmpty(){
        if(head == null){
            return true;
        }
        return false;
    }

    public Vertex pop(){
        Vertex vertex = head.getVertex();
        head = head.getNext();
        return vertex;
    }

    public void add(Vertex vertex){
        Node nodeToAdd = new Node(vertex);
        if(head == null || head.getVertex().getCost() > vertex.getCost()){
            nodeToAdd.setNext(head);
            head = nodeToAdd;
        }

        else {
            Node q = head;
            Node p = head.getNext();
            while (p!= null && p.getVertex().getCost() < vertex.getCost()) {
                p = p.getNext();
                q = q.getNext();
            }
            q.setNext(nodeToAdd);
            nodeToAdd.setNext(p);
        }
    }

}

class Node{

    private Vertex vertex;
    private Node next;

    public Node(Vertex vertex){
        this.vertex = vertex;
        next = null;
    }

    public Vertex getVertex(){
        return vertex;
    }

    public void setVertex(Vertex vertex){
        this.vertex = vertex;
    }

    public void setNext(Node node){
        next = node;
    }

    public Node getNext(){
        return next;
    }
}
