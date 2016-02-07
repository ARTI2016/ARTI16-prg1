package artiprg1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BFS implements Search {
	HashSet<State> marked;
    Stack<String> successMoves;
    List<SearchNode> frontier = new ArrayList<SearchNode>();
    SearchNode root;
    
    public BFS(State startingState)
    {
        marked = new HashSet<State>();
        SearchNode root = new SearchNode(startingState, "TURN_ON", null);
        successMoves = new Stack<String>();
        successMoves.push("TURN_OFF");
        bfs(root);
        successMoves.push("TURN_ON");
    }
    
    private void bfs(SearchNode currentNode)
    {
        Queue<SearchNode> q = new LinkedList<SearchNode>();
        q.add(currentNode);
        marked.add(currentNode.getState());
        while(!q.isEmpty())
        {
            SearchNode topNode = q.poll();
            
            if( topNode.getState().isGoal()){
            	for(SearchNode i = topNode; i != root; i = i.getParent())
            		successMoves.push(i.getAction());
            	
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
                    q.add(sn);
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
