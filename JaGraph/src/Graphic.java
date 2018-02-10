import java.util.ArrayList;

import org.graphstream.graph.*;



public class Graphic {
	//Variables
	private String name;
	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;
	private String type;
	//Constructor
	Graphic(String name){
		this.name = name;
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();		
		
	}
	
	//Functions
	
	//Add a node
	public void addNode(Node n){
		nodes.add(n);
	}
	
	//Add an edge
	public void addEdge(Edge e){
		edges.add(e);
	}
	
	public Node getNode(int i){
		try{
			return nodes.get(i);
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	
	//Write graph information
	public void AfficheGraph(Graph image){
		Node temp;
		System.out.println("Graph name : "+getName());
		System.out.println("Number of nodes : "+getNbNodes());
		for(int i=0 ; i<nodes.size();i++){
			temp = nodes.get(i);
			System.out.println(temp.getName()+"/");
			for(int j=0; j<temp.edges.size();j++){
				System.out.print(" "+temp.edges.get(j).getEdge());
			}
			System.out.println("");
		}
		createImage(image);
	}
	
	public String getName(){
		return name;
	}
	
	public int getNbNodes(){
		return nodes.size();
	}
	
	public void changeType(String type){
		this.type=type;
	}
	
	public void suppressNode(int i,Graph image){
		Node node;
		ArrayList<Edge> e;
		//Cycle graph
		if(type == "cycle"){
			node = nodes.remove(i);
			e = node.getEdges();
			for(int cpt = 0; cpt<e.size();cpt++){
				edges.remove(e.get(cpt));
			}
			node.removeEdges();
			if(i==0){
				edges.add(new Edge(nodes.get(i),nodes.get(nodes.size()-1)));
			}else if(i<nodes.size()){
				edges.add(new Edge(nodes.get(i-1),nodes.get(i)));
			}else{
				edges.add(new Edge(nodes.get(nodes.size()-1),nodes.get(0)));
			}
		//Complete graph
		}else{
			//Remove just the node and their edges
			node = nodes.remove(i);
			e = node.getEdges();
			for(int cpt = 0; cpt<e.size();cpt++){
				edges.remove(e.get(cpt));
			}
			node.removeEdges();
		}
		createImage(image);
	}
	
	//BFS function
	public void bfs(int i, Graph image){
		ArrayList<Node> queue = new ArrayList<Node>();
		Node u; //Actual vertex
		Node s = nodes.get(i); //The source vertex
		Node v; //Neighbour vertex
		//initialise all the nodes
		for(int cpt=0;cpt<nodes.size();cpt++){
			if(i!=cpt){
				u = nodes.get(cpt);
				u.setColor("white");
				u.setD(1000);
				u.setPi(null);
			}
		}
		s.setColor("gray");
		s.setD(0);
		s.setPi(null);
		queue.add(s);
		//Terminate when all the nodes are visited
		while (queue.size()!=0){
			//Obtain the next vertex to visited
			u = queue.get(0);
			queue.remove(0);
			//Check his neighbour
			for (int j=0;j<u.edges.size();j++){
				v = u.edges.get(j).getNeighbour(u);
				//If we find a new node, add to the queue
				if(v.getColor()=="white"){
					v.setColor("gray");
					v.setD(u.getD()+1);
					v.setPi(u);
					queue.add(v);
				}
			}
			//Vertex is visited
			u.setColor("black");
		}
		//Print the Graph
		createImageBfs(image);
	}
	
	//Print a graph
	public void createImage(Graph image){
		image.clear();
		nodesLoop(image);
		Edge e;
		for(int t=0;t<edges.size();t++){
			e=edges.get(t);
			image.addEdge(""+e.getChild().getName()+""+e.getFather().getName(), ""+e.getChild().getName(), ""+e.getFather().getName());
		}
	}
	
	//Print BFS graph
	public void createImageBfs(Graph image){
		image.clear();
		Node temp;
		nodesLoop(image);
		for(int t=0;t<nodes.size();t++){
			temp=nodes.get(t);
			if(temp.getPi()!=null){
				image.addEdge(""+temp.getName()+""+temp.getPi().getName(), ""+temp.getName(), ""+temp.getPi().getName());
			}
		}
	}
	
	//Create all nodes on a graph
	public void nodesLoop(Graph image){
		Node temp;
		for(int t=0 ;t<nodes.size();t++){
			temp=nodes.get(t);
			image.addNode(""+temp.getName());
			image.getNode(""+temp.getName()).addAttribute("ui.label", ""+temp.getName());		
		}
	}
	
	public void dijkstra(int i,Graph image){
		initialiseSource(i); //Dijkstra initialise
		ArrayList<Edge> es; //The edge list
		ArrayList<Node> Q = new ArrayList<Node>(); //The copy of the node's list 
		Q.addAll(nodes);
		Node u;
		//Randomize the weight of edges
		for(int cpt=0;cpt<edges.size();cpt++){
			edges.get(cpt).setWeight((int)(Math.random() * 20));
		}
		//Check all the edges and relax
		while (Q.size() != 0){
			//Take the minimum weight edge
			u = extractMin(Q);
			Q.remove(u);
			es = u.getEdges();
			//Relax the neighbours edges
			for(int cpt=0;cpt<es.size();cpt++){
				relax(u,es.get(cpt).getNeighbour(u),es.get(cpt));
			}
		}
		//Print the graph
		createImageDijkstra(image);
	}
	
	//Initialise the nodes
	public void initialiseSource(int i){
		for(int cpt=0;cpt<nodes.size();cpt++){
			if(cpt != i){
				nodes.get(cpt).setD(1000);
				nodes.get(cpt).setPi(null);
			}
			else{
				nodes.get(cpt).setD(0);
				nodes.get(cpt).setPi(null);
			}
		}
	}
	
	//Extract the minimum weight edge
	public Node extractMin(ArrayList<Node> q){
		Node min= q.get(0);
		for(int cpt=0;cpt<q.size();cpt++){
			if (min.getD()>q.get(cpt).getD()){
				min = q.get(cpt);
			}
		}
		return min;
	}
	
	//Relax an edge
	public void relax(Node u, Node v, Edge e){
		if (v.getD() > u.getD() + e.getWeight()){
			v.setD(u.getD()+e.getWeight());
			v.setPi(u);
		}
	}
	
	//Print dijkstra graph
	public void createImageDijkstra(Graph image){
		Node temp;
		for(int t=0 ;t<nodes.size();t++){
			temp=nodes.get(t);
			image.addNode(""+temp.getName());
			image.getNode(""+temp.getName()).addAttribute("ui.label", ""+temp.getName()+"-"+temp.getD());		
		}
		Edge e;
		for(int t=0;t<edges.size();t++){
			e=edges.get(t);
			image.addEdge(""+e.getChild().getName()+""+e.getFather().getName(), ""+e.getChild().getName(), ""+e.getFather().getName());
			image.getEdge(""+e.getChild().getName()+""+e.getFather().getName()).addAttribute("ui.label", ""+e.getWeight());
		}
		for(int t=0;t<nodes.size();t++){
			temp=nodes.get(t);
			if(temp.getPi()!=null){
				try{
					image.getEdge(""+temp.getPi().getName()+""+temp.getName()).addAttribute("ui.style", "fill-color : red;");
				}catch(Exception exception){
					image.getEdge(""+temp.getName()+""+temp.getPi().getName()).addAttribute("ui.style", "fill-color : red;");
				}
			}	
		}
	}
}
