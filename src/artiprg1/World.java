package artiprg1;

import java.util.ArrayList;

public class World {
	public ArrayList<Coordinate> obstacles;
	private Coordinate home;
	private Coordinate size;
	
	public ArrayList<Coordinate> getObstacles() {
		return obstacles;
	}

	public void setObstacles(ArrayList<Coordinate> obstacles) {
		this.obstacles = obstacles;
	}

	public Coordinate getHome() {
		return home;
	}

	public void setHome(Coordinate home) {
		this.home = home;
	}

	public Coordinate getSize() {
		return size;
	}

	public void setSize(Coordinate size) {
		this.size = size;
	}

	public World(){
		this.obstacles = new ArrayList<Coordinate>();
		this.home = new Coordinate();
		this.size = new Coordinate();
	}
	
	public World(ArrayList<Coordinate> obstacles, Coordinate size, Coordinate home){
		this.obstacles = new ArrayList<Coordinate>();
		this.home = new Coordinate();
		this.size = new Coordinate();
		
		for(Coordinate o : obstacles) {
			this.obstacles.add(new Coordinate(o.getX(), o.getY()));
		}
		
		this.home.set(home.getX(), home.getY());
		this.size.set(size.getX(), size.getY());
	}
}
