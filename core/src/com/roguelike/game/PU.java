package com.roguelike.game;

public class PU extends Obj{
	private static final long serialVersionUID = -7412384759008067405L;
	
	public String name;
	
	public int charges;
	
	public PU(Place p){
		super(p);
		turnsleft = 3;
		name = "PU";
		charges = 0;
	}
	public boolean onTouch(Unit u){
		if(u instanceof CoreGame.Player){
			for(int i=0; i<3; i++){
				if(((CoreGame.Player) u).powers[i]==null){
					((CoreGame.Player) u).powers[i] = this;
					return true;
				}
			}
		}
		return false;
	}
	public boolean onAction(CoreGame.Player p){
		return false;
	}
}