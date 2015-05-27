package com.roguelike.game;

import java.io.Serializable;
import java.util.LinkedList;

public class Place implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2668173007374278134L;

	Place[] links;
	
	int[] position;
	
	boolean visible;
	boolean seen;
	
	LinkedList<Unit> units;
	LinkedList<Obj> obj;
	//TODO save last seen state?
	
	public Place(){
		links = new Place[4];
		
		position = new int[2];
		
		visible = false;
		seen = false;
		this.units = new LinkedList<Unit>();
		this.obj = new LinkedList<Obj>();
	}

	public Place(int w, int h){
		this();
		this.position[0] = w;
		this.position[1] = h;
	}
	
	public void onTouch(Unit u){
		for(Obj o : obj){
			if(o.onTouch(u))
					obj.remove(o);
		}
	}
}