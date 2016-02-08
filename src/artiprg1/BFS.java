package artiprg1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BFS implements Search {
	private HashSet<State> marked;
	private Stack<String> successMoves;
	private SearchNode root;
	private Queue<SearchNode> frontier;
    
    public BFS(State startingState)
    {
        marked = new HashSet<State>();
        frontier.add(new SearchNode(startingState, null, null));
        successMoves = new Stack<String>();
        successMoves.push("TURN_OFF");
        bfs(root);
        successMoves.push("TURN_ON");
    }
    
    private void bfs(SearchNode currentNode)
    {
    	frontier = new LinkedList<SearchNode>();
    	frontier.add(currentNode);
        marked.add(currentNode.getState());
        while(!frontier.isEmpty())
        {
            SearchNode topNode = frontier.poll();
            
            if( topNode.getState().isGoal()){
            	for(SearchNode i = topNode; i != root; i = i.getParent()){
            		successMoves.push(i.getAction());         	
            	}
            	return;
            }
               
            List<SearchNode> adjacentSearchNodes = new ArrayList<SearchNode>();
			for(String action : topNode.getState().legalActions())
            {
                SearchNode newSearchNode = new SearchNode(topNode.getState().expandState(action), action,topNode);
                adjacentSearchNodes.add(newSearchNode);
            }
            for(SearchNode sn : adjacentSearchNodes)
            {
                if( !marked.contains(sn.getState()) )
                {
                    marked.add(sn.getState());
                    frontier.add(sn);
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
