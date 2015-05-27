package com.roguelike.game;

import java.io.Serializable;

public class Obj implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -355704150946959794L;

	int[] position;
	
	boolean playerOnly;
	
	public Obj(){
		this.position = new int[2];
		this.position[0] = 0;
		this.position[1] = 0;
		this.playerOnly =true;
	}
	
	public boolean onTouch(Unit u){return false;}
	
	public boolean onAction(Game.Player p){return false;}

}
