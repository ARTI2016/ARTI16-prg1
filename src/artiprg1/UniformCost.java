package artiprg1;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class UniformCost implements Search{
	
	private HashSet<State> marked;
	private PriorityQueue<SearchNode> frontier;
	private Comparator<SearchNode> comparator;
	private Stack<String> successMoves;
	private SearchNode root;
	
	
	
	
	
	public UniformCost(State startingState)
    {
		comparator = new CostComparator();
        frontier = new PriorityQueue<SearchNode>(6, comparator);
   
        root = new SearchNode(startingState, "TURN_ON", null);
        successMoves = new Stack<String>();
    
        successMoves.push("TURN_OFF");
        ucs(root);
        successMoves.push("TURN_ON");
    }
	
	private void ucs(SearchNode currentnode){
		frontier.add(currentnode);
        marked.add(currentnode.getState());
        while(!frontier.isEmpty())
        {
        	SearchNode topNode = frontier.poll();
            
            if( topNode.getState().isGoal()){
            	for(SearchNode i = topNode; i != root; i = i.getParent()){
            		successMoves.push(i.getAction());         	
            	}
            	return;
            }
            
            if( !marked.contains(topNode.getState()) )
                marked.add(topNode.getState());
           
			
            for(String action : topNode.getState().legalActions())
            {
             
				SearchNode newSearchNode = new SearchNode(topNode.getState().expandState(action), action,topNode);
				if( newSearchNode.getState().isGoal()){
	            	for(SearchNode i = newSearchNode; i != root; i = i.getParent()){
	            		successMoves.push(i.getAction());         	
	            	}
	            	return;
	            }
				
				if(!marked.contains(newSearchNode.getState()))
					frontier.add(newSearchNode);
      
            }
           
        }
        return;
	}
	
	@Override
	public String nextMove() {
		return successMoves.pop();
	}
	
	public class CostComparator implements Comparator<SearchNode>
	{
	    @Override
	    public int compare(SearchNode x, SearchNode y)
	    {
	        if (x.getCost() < y.getCost())
	        {
	            return -1;
	        }
	        if (x.getCost() > y.getCost())
	        {
	            return 1;
	        }
	        return 0;
	    }
	}
}
