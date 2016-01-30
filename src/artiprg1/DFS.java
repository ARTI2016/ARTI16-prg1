package artiprg1;

import java.util.Queue;

public class DFS {
	/**
	 * TODO: Frontier, 
	 */
	private Queue<SearchNode> frontier;
	
	public DFS(State initial) {
		frontier.add(new SearchNode(initial, null, null));
	}
}
