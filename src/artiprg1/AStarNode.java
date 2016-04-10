package artiprg1;

class AStarNode extends SearchNode{
	private int f;
	private int g;

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public AStarNode(State state, String action, AStarNode parentNode, int cost) {
		super(state, action, (AStarNode)parentNode, cost);
		if( parentNode == null){
			g = 0;
		}else{
			g = parentNode.g + 1; // Action Cost = 1;
		}
		f = g + heuristic();
	}
	
	public int heuristic() {
		int h = 0;
		if(!state.dirt.isEmpty()){
			h = (state.dirt.size()-1);
			if(h > 1){
				int sum = 0;
				for(int i = 0; i < state.dirt.size()-1; i++){
					int temp = (state.dirt.get(i).distanceTo(state.dirt.get(i+1)));
					for(int j = i+1; j < state.dirt.size(); j++){
						if(state.dirt.get(j).distanceTo(state.dirt.get(i)) < temp)
							temp = state.dirt.get(j).distanceTo(state.dirt.get(i));
					}
				}
				h *= sum;
			} else if(h == 1){
				h *= state.dirt.get(0).distanceTo(state.dirt.get(1));
			}
			
			int temp = 0;
			for(int i = 0; i < state.dirt.size(); i++){
				if(state.world.getHome().distanceTo(state.dirt.get(i)) > temp){
					temp = state.world.getHome().distanceTo(state.dirt.get(i));
				}
			}
			h += temp;
			
			temp = 0;
			for(int i = 0; i < state.dirt.size(); i++){
				if(state.getPos().distanceTo(state.dirt.get(i)) > temp){
					temp = state.getPos().distanceTo(state.dirt.get(i));
				}
			}
			
			h += temp;
		}else{
			h = state.getPos().distanceTo(state.world.getHome());
		}
		
		return h;
	}
}
