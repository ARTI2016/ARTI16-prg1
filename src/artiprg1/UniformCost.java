package artiprg1;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class UniformCost implements Search{
	
	private PriorityQueue<SearchNode> frontier;
	private Set<Object> explored;
	private Comparator<SearchNode> comparator;
	private Stack<String> successMoves;
	private SearchNode root;
	
	public UniformCost(State startingState)
    {
		comparator = new CostComparator();
        frontier = new PriorityQueue<SearchNode>(6, comparator);
        
        root = new SearchNode(startingState, "TURN_ON", null);
        successMoves = new Stack<String>();
        SearchNode successNode = ucs(root);
        
        successMoves.push("TURN_OFF");
        while(successNode != null)
            successMoves.push(successNode.getAction());
    }
	
	private SearchNode ucs(SearchNode currentnode){
			
		return null;
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
