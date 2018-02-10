
public class Edge {
	//Variables
	private int weight;
	private Node father;
	private Node child;
	
	//Constructor
	Edge(Node father,Node child){
		weight = 1;
		this.father = father;
		this.child = child;
		father.addEdge(this);
		child.addEdge(this);
	}
	
	public Node getFather() {
		return father;
	}

	public Node getChild() {
		return child;
	}

	// get the edge
	public String getEdge(){
		return(father.getName()+"-"+child.getName());
	}
	
	public void removeEdge(Node node){
		if(node == child){
			father.removeEdge(this);
		}else{
			child.removeEdge(this);
		}
	}
	
	public Node getNeighbour(Node node){
		if(node == child){
			return father;
		}else{
			return child;
		}
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int d) {
		this.weight = d;
	}
}
