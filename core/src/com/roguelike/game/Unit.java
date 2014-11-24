package com.roguelike.game;

public class Unit{
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
	
}