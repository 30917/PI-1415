package com.roguelike.game;


public class PUjump extends PU{
	private static final long serialVersionUID = 7065959223356015308L;
	
	public int charges = 2;
	
	public PUjump(Place p){
		super(p);
		turnsleft = 0;
		name = "Jump";
	}
	public boolean onAction(CoreGame.Player p){
		charges--;
		//TODO
		
		if(turnsleft<=0)
			return false;
		return true;
	}
}