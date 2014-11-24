package com.roguelike.game;

import java.util.LinkedList;

public class Place{
	Place[] links;
	
	int[] position;
	
	boolean visible;
	boolean seen;
	
	LinkedList<Unit> units;
	LinkedList<Effect> effects;
	//TODO save last seen state?
	
	public Place(){
		links = new Place[4];
		
		position = new int[2];
		
		visible = false;
		seen = false;
		this.units = new LinkedList<Unit>();
		this.effects = new LinkedList<Effect>();
	}
}