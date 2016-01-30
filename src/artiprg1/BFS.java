package artiprg1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BFS implements Search {
	HashSet<State> marked;
    Stack<String> successMoves;
    List<SearchNode> branches = new ArrayList<SearchNode>();
    SearchNode root;
    
    public BFS(State startingState)
    {
        marked = new HashSet<State>();
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
        marked.add(currentNode.getState());
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
                if( !marked.contains(sn.getState()) )
                {
                    marked.add(sn.getState());
                    q.add(sn);
                }
            }
        }
        return null;
    }

    public String nextMove()
    {
        return successMoves.pop();
    }
}
