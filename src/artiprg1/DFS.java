package artiprg1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DFS implements Search {
	/**
	 * TODO: Frontier, 
	 */
	private Queue<SearchNode> frontier = new LinkedList<SearchNode>();
	private ArrayList<String> legalActions = new ArrayList<String>();
	private Stack<String> solution = new Stack<String>();
	private SearchNode current;
	
	public DFS(State initial) {
		frontier.add(new SearchNode(initial, null, null));
		if(search()) {
			System.out.println("Search failed!");
		};
	}
	
	public boolean search() {
		while(true) {
			if(frontier.isEmpty()) {
				return false;
			}
			current = frontier.remove();
			if(current.getState().isGoal()) {
				//Trace back up and construct solution
				for(SearchNode n = current; n != null; n = n.getParent()) {
					System.out.println("Adding action to solution list");
					solution.add(n.getAction());
				}
				return true;
			}
			expand: for(String a : current.getState().legalActions()) {
				//Create next search node
				SearchNode nextNode = new SearchNode(current.getState().expandState(a), a, current);
				//Check for duplicates
				State s = nextNode.getState();
				for(SearchNode n = nextNode.getParent(); n != null; n = n.getParent()) {
					if(n.getState().equals(s)) {
						continue expand;
					}
				}
				if(!frontier.offer(nextNode)) {
					System.out.println("Failed to add node to frontier!");
				}
			}
		}
	}

	@Override
	public String nextMove() {
		if(solution.isEmpty()) {
			System.out.println("NOW YOU FUCKED UP");
			System.exit(-49);;
		}
		return solution.pop();
	}
}
