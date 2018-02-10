

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

public class Main {
	private static ArrayList<Graphic> graphs = new ArrayList<Graphic>(); //Graph List
	private static Scanner sc;
	private static Scanner scan;
	private static Graph image;
	
	//Main
	public static void main(String []args) throws InterruptedException{
		createCycle(5,new Graphic("Cycle_Pentagon_test"));
		createComplete(5,new Graphic("Complete_Pentagon_Test"));
		sc = new Scanner(System.in); //Scanner
		image = new MultiGraph("Graph Theory");
		image.addAttribute("ui.stylesheet", "graph { padding: 50px; } node { size : 20px;size-mode: fit; shape: rounded-box;text-style:bold; fill-color: #EEE; stroke-mode: plain; padding: 3px, 2px; }");
		image.display();
		String choose;
		//Main Loop
		System.out.println("Welcome !");
		while(true){
			System.out.println("Choose an Action :\n1-Create a graph\n2-Info\n3-Delete node\n4-BFS\n5-Dijkstra\n6-Quitter");
			choose = sc.nextLine();
			switch(choose){
				case "1": createGraph();
					    break;
				case "2": Info();
					    break;
				case "3": suppressNode();
					    break;			
				case "4" : bfs();
						 break;
				case "5" : dijkstra();
						 break;
				case "6" : System.out.println("Goodbye !");
						 return;
				default : break;
			}
		}
	}
	
	//Procedure for create a graph
	public static void createGraph(){
		scan = new Scanner(System.in);
		String name;//Name of the graph
		int nbNode = 0;//Number of nodes
		String type="-1"; //Type of Graph
		boolean isNumber;
		
		System.out.println("Choose the name of the graph :");
		name = sc.nextLine();
		Graphic newGraph = new Graphic(name);
		System.out.println("How many node ?");
		do{
			sc = new Scanner(System.in);
			try{
				isNumber = true;
				nbNode = sc.nextInt();
			}
			catch(InputMismatchException e){
				System.out.println("It's not a number, choose the number of node : ");
				isNumber = false;
			}
		}while(!isNumber);
		while (type == "-1") {
			System.out.println("What is the type of your graph ?\n1-cycle\n2-complete");
			type = scan.nextLine();
			switch(type){
				case "1": createCycle(nbNode, newGraph);
						break;
				case "2": createComplete(nbNode,newGraph);
						break;
				default : type = "-1";
			}
		}
		newGraph.AfficheGraph(image);
	}
	
	//Create a cycle graph
	public static void createCycle(int nbNode, Graphic newGraph){
		Node node;
		//Create all the nodes
		for(int i=0;i<nbNode;i++){
			node = new Node(i);
			newGraph.addNode(node);
		}
		for(int i=0;i<nbNode;i++){
			if(i==nbNode-1){
				newGraph.addEdge(new Edge(newGraph.getNode(i),newGraph.getNode(0)));
			}else{
				newGraph.addEdge(new Edge(newGraph.getNode(i),newGraph.getNode(i+1)));
			}
		}
		newGraph.changeType("cycle");
		graphs.add(newGraph);
	}
	
	//Create a complete graph
	public static void createComplete(int nbNode, Graphic newGraph){
		Node node;
		//Create all the nodes
		for(int i=0;i<nbNode;i++){
			node = new Node(i);
			newGraph.addNode(node);
		}
		for(int i=0;i<nbNode;i++){
			for(int j=i+1;j<nbNode;j++){
				newGraph.addEdge(new Edge(newGraph.getNode(i),newGraph.getNode(j)));
			}
		}
		newGraph.changeType("complete");
		graphs.add(newGraph);
	}
	
	//Function for choose a graph
	public static Graphic chooseGraph(){
		Graphic graph = new Graphic("");
		if(graphs.size()!=0){
			String choose;			
			System.out.println("Choose a graph :");
			boolean trouve = false;
			while(!trouve){
				sc = new Scanner(System.in);
				for(int i=0;i<graphs.size();i++){
					System.out.println(i+" - "+graphs.get(i).getName());
				}
				choose = sc.nextLine();
				try{
					graph = graphs.get(Integer.parseInt(choose));
				}catch(IndexOutOfBoundsException e){
					graph = null;
				}catch(NumberFormatException e){
					graph = null;
				}
				trouve = (graph!=null);
			}
		}
		return graph;
	}
	
	//Print a graph
	public static void Info(){
		Graphic graph = chooseGraph();
		graph.AfficheGraph(image);
	}
	
	public static void suppressNode(){
		Graphic graph = chooseGraph();
		if(graph.getNbNodes()>0){
			graphs.remove(graph);
			int result;
			System.out.println("Choose the Node want to delete : ");
			result = findNode(graph);
			graph.suppressNode(result,image);
			graphs.add(graph);
		}
		else System.out.println("This Graph has no nodes");
	}
	
	public static void bfs(){
		Graphic graph = chooseGraph();
		int result;
		System.out.println("Choose the start node : ");
		result = findNode(graph);
		graph.bfs(result,image);
	}
	
	public static void dijkstra(){
		Graphic graph = chooseGraph();
		int result;
		System.out.println("Choose the start node : ");
		result = findNode(graph);
		graph.dijkstra(result,image);
	}
	
	public static int findNode(Graphic graph){
		boolean find = false;
		int result;
		do{
			sc = new Scanner(System.in);
			for(int i=0;i<graph.getNbNodes();i++){
				System.out.println(i+" - "+graph.getNode(i).getName());
			}
			try{result = sc.nextInt();}
			catch(InputMismatchException e){result = 1000;}
			find = (graph.getNode(result)!=null);
		}while(!find);
		return result;
	}
}
