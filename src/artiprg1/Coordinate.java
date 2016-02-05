package artiprg1;

public class Coordinate {
	private int x;
	private int y;
	
	public Coordinate(){
		this.x = 0;
		this.y = 0;
	}
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void set(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int distanceTo(Coordinate c){
		int deltaX = this.x - c.x;
        int deltaY = this.y - c.y;

        if (deltaX < 0)
        	deltaX *= -1;
        if (deltaY < 0)
        	deltaY *= -1;

        return deltaX + deltaY;
	}
	
	@Override
    public boolean equals(Object obj)
    {
		Coordinate c = (Coordinate) obj;
        if (this.x == c.x && this.y == c.y)
            return true;

        return false;
    }
}
