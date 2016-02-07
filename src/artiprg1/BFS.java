package artiprg1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BFS implements Search {
	private HashSet<State> explored;
	private Stack<String> successMoves;
	private SearchNode root;
    
    public BFS(State startingState)
    {
        explored = new HashSet<State>();
        SearchNode root = new SearchNode(startingState, "TURN_ON", null);
        successMoves = new Stack<String>();
        SearchNode successNode = bfs(root);
        
        successMoves.push("TURN_OFF");
        while(successNode != null)
            successMoves.push(successNode.getAction());
    }
    
    private SearchNode bfs(SearchNode currentNode)
    {
        Queue<SearchNode> q = new LinkedList<SearchNode>();
        q.add(currentNode);
        explored.add(currentNode.getState());
        while(!q.isEmpty())
        {
            SearchNode topNode = q.poll();
            
            if( topNode.getState() == root.getState())
               return topNode;
            List<SearchNode> adjacentSearchNodes = new ArrayList<SearchNode>();
            for(String move : topNode.getState().legalActions())
            {
                SearchNode newSearchNode = new SearchNode(topNode.getState().expandState(move),move,topNode);
                adjacentSearchNodes.add(newSearchNode);
            }
            for(SearchNode sn : adjacentSearchNodes)
            {
                if( !explored.contains(sn.getState()) )
                {
                    explored.add(sn.getState());
                    q.add(sn);
                }
            }
        }
        return null;
    }

    @Override
    public String nextMove()
    {
        return successMoves.pop();
    }
}
