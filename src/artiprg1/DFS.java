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
	private ArrayList<String> legalActions;
	private Stack<String> solution;
	private SearchNode current;
	
	public DFS(State initial) {
		frontier.add(new SearchNode(initial, null, null));
		if(search()) {
			
		};
	}
	
	public boolean search() {
		while(true) {
			current = frontier.poll();
			if(current == null) {
				return false;
			}
			if(current.getState().isGoal()) {
				//Trace back up and construct solution
				for(SearchNode n = current; n != null; n = n.getParent()) {
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
		return solution.pop();
	}
}
