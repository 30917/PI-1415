package com.roguelike.game;


public class PUvision extends PU{
	private static final long serialVersionUID = 1847360828403821079L;
	
	public PUvision(Place p){
		super(p);
		turnsleft = 3;
		name = "Vision";
	}
	public boolean onAction(CoreGame.Player p){
		p.viewrange = 2;
		turnsleft--;
		if(turnsleft<=0)
			return false;
		return true;
	}
}
