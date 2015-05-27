package com.roguelike.game;

import java.io.Serializable;

public class Unit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -759754524079076571L;
	int[] position;
	int hp;
	int ap;
	
	public Unit(){
		this.position = new int[2];
		this.position[0] = 0;
		this.position[1] = 0;
		this.hp = 100;
		this.ap = 10;	
	}
	
	public int getDistance(Unit u){
		int x = (this.position[0] > u.position[0])? 
				this.position[0]-u.position[0] : u.position[0]-this.position[0];
		int y = (this.position[1] > u.position[1])? 
				this.position[1]-u.position[1] : u.position[1]-this.position[1];
		//return h*h= (x*x + y*y);
				return 0;
	}
	
}