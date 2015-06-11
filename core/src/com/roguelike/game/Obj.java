package com.roguelike.game;

import java.io.Serializable;


public abstract class Obj implements Serializable{
	private static final long serialVersionUID = -355704150946959794L;
	
	Place place;
	boolean playerOnly;
	int turnsleft;
	
	public Obj(Place p){
		place = p;
		this.playerOnly =true;
		turnsleft = 0;
	}
	public boolean onTouch(Unit u){return false;}
	public boolean onAction(CoreGame.Player p){return false;}

}
