package com.roguelike.game;


public class Enemy extends Unit{
	private static final long serialVersionUID = 1999342710476670490L;
	
	public Unit following;
	public Path path;
	public Level level;
	public boolean move;

	public Enemy(Level l){
		super();
		this.ap = 25;
		this.following = null;
		this.path = new Path(l, this);
		this.level = l;
		move = false;
	}
	
	public Enemy(Level l, Place p){
		this(l);
		this.position[0] = p.position[0];
		this.position[1] = p.position[1];
		p.units.add(this);
	}
	
	public void update(){
		if(move == false){
			move = true;
			return;
		}
		if(following != null){
			//follow(following); 
			//replace with updatePath?
			path = new Path(level, this);
			path.updatePath(level);
			path.getPathTo(following);
			follow();
		} else {
			randomPatrol();
		}
		move = false;
		
	}
	
    public void follow(){
    	if(path.path.isEmpty()){
    		randomPatrol();
    		return;
    	}
    	Place p = path.path.get(0).place;
//    	if(path.path.size() == 2)
//    		p = path.path.get(1).place;
    	Place a = level.map[this.position[0]][this.position[1]];
    	a.units.remove(this);
    	p.units.add(this);
    	this.position[0] = p.position[0];
    	this.position[1] = p.position[1];
    }
	
	public void randomPatrol(){
		int r = Level.random.nextInt(4);
		move(r);
		
	}
	
	public void move(int dir){
		Place p = level.map[this.position[0]][this.position[1]];
		if(p.links[dir] != null){
			this.position[0] = p.links[dir].position[0];
			this.position[1] = p.links[dir].position[1];
			//p.links[dir].onTouch(player);
			p.units.remove(this);
			p.links[dir].units.add(this);
		}
		
	}
	

	public boolean onTouch(Unit u){
		u.hp-=ap;
		hp -= u.ap;
		if(hp<=0)
			return true;
		return false;
	}

}
