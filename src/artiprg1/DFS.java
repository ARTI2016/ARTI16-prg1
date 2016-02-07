package artiprg1;

import java.util.Stack;

public class DFS implements Search {
	private Stack<SearchNode> frontier = new Stack<SearchNode>();
	private Stack<String> solution = new Stack<String>();
	private SearchNode current;
	
	public DFS(State initial) {
		frontier.add(new SearchNode(initial, null, null));
		boolean searchworked = search();
		if(!searchworked) {
			System.out.println("Search failed!");
		};
		if(searchworked) {
			System.out.println("Search worked!!");
		};
	}
	
	public boolean search() {
		while(true) {
			if(frontier.isEmpty()) {
				System.out.println("Frontier is empty!");
				return false;
			}
			current = frontier.pop();
			System.out.println("Current state:");
			current.getState().printState();
			if(current.getState().isGoal()) {
				//Trace back up and construct solution
				for(SearchNode n = current; n != null; n = n.getParent()) {
					System.out.println("Adding action to solution list");
					solution.add(n.getAction());
				}
				return true;
			}
			expand: for(String a : current.getState().legalActions()) {
				System.out.println("Legal actions in current state:");
				for(String s : current.getState().legalActions()) {
					System.out.println(s);
				}
				System.out.println("Expanding with action " + a);
				//Create next search node
				SearchNode nextNode = new SearchNode(current.getState().expandState(a), a, current);
				//Check for duplicates in path
				State s = nextNode.getState();
				for(SearchNode n = nextNode.getParent(); n != null; n = n.getParent()) {
					if(n.getState().equals(s)) {
						continue expand;
					}
				}
				frontier.push(nextNode);
				System.out.println("Pushed node to frontier!");
			}
		}
	}

	@Override
	public String nextMove() {
		if(solution.isEmpty()) {
			System.out.println("NOW YOU FUCKED UP");
			System.exit(-49);
		}
		return solution.pop();
	}
}
