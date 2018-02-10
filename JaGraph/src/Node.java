import java.util.ArrayList;


public class Node {
	//Variables
	private int name;
	private int degree;
	protected ArrayList<Edge> edges;
	private String color;
	private int d;
	private Node pi;
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public Node getPi() {
		return pi;
	}

	public void setPi(Node pi) {
		this.pi = pi;
	}

	//Constructor
	Node(int name){
		this.name = name;
		degree = 0;
		edges = new ArrayList<Edge>();
	}
	
	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	//Functions
	public void addEdge(Edge e){
		edges.add(e);
		degree++;
	}
	
	public int getName(){
		return name;
	}
	
	//Remove his edges
	public void removeEdges(){
		for(int i=0;i<edges.size();i++){
			edges.get(i).removeEdge(this);
		}
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void removeEdge(Edge edge){
		edges.remove(edge);
		
	}
}
