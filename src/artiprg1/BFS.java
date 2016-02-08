package artiprg1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS implements Search {
	private HashMap<State, SearchNode> marked;
	private Stack<String> successMoves;
	private SearchNode root;
	private Queue<SearchNode> frontier;
    
    public BFS(State startingState)
    {
    	frontier = new LinkedList<SearchNode>();
    	marked = new HashMap<>();
        successMoves = new Stack<String>();
        root = new SearchNode(startingState, null, null);        
        successMoves.push("TURN_OFF");
        bfs();
    }
    
    private void bfs()
    {
        frontier.add(root);
        
        while(!frontier.isEmpty())
        {
            SearchNode topNode = frontier.poll();
            State currentState = topNode.getState();
            if(currentState.isGoal()){
				for(SearchNode i = topNode; i.getParent() != null; i = i.getParent()){
					successMoves.push(i.getAction());
				}
				return;
			}
            if(!marked.containsKey(currentState)) {
            	marked.put(currentState, topNode);
				for(String action : topNode.state.legalActions()) {
					SearchNode newNode = new SearchNode(topNode.state.expandState(action), action, topNode);
						frontier.add(newNode);
				}
			}
        }
        return;
    }

    @Override
    public String nextMove()
    {
        return successMoves.pop();
    }
}
