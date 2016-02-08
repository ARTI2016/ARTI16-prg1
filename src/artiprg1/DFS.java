package artiprg1;

import java.util.HashMap;
import java.util.Stack;

public class DFS implements Search {
	private HashMap<State, SearchNode> marked;
	private Stack<String> successMoves;
	private SearchNode root;
	private Stack<SearchNode> frontier;
    
    public DFS(State startingState)
    {
    	frontier = new Stack<SearchNode>();
    	marked = new HashMap<>();
        successMoves = new Stack<String>();
        root = new SearchNode(startingState, null, null);        
        successMoves.push("TURN_OFF");
        dfs();
    }
    
    private void dfs()
    {
        frontier.push(root);
        
        while(!frontier.isEmpty())
        {
            SearchNode topNode = frontier.pop();
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
						frontier.push(newNode);
				}
			}
        }
        return;
    }
    
	@Override
	public String nextMove() {
		return successMoves.pop();
	}
}
