package com.roguelike.game;

import java.io.Serializable;



public class BigMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3401225709814594632L;
	Level[] levels;
	int currentlevel;
	
	public BigMap(){
		this.levels = new Level[3];
		this.levels[0] = Level.randomLevel();
		this.levels[1] = Level.randomLevel();
		this.levels[2] = Level.randomLevel();
		
		this.currentlevel = 0;
	}
	
}