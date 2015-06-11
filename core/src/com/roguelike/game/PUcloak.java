package com.roguelike.game;


public class PUcloak extends PU{
	private static final long serialVersionUID = -1822558588974195414L;
	
	public int charges = 2;
	
	public PUcloak(Place p){
		super(p);
		turnsleft = 0;
		name = "Cloak";
	}
	public boolean onAction(CoreGame.Player p){
		charges--;
		for(Enemy e : this.place.level.enemys)
			e.following = null;
		
		if(turnsleft<=0)
			return false;
		return true;
	}
}