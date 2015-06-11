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
	
	LinkedList<Unit> units;
	LinkedList<Obj> obj;
	//TODO save last seen state?
	Place lastseen;
	
	Level level;
	
	
	public Place(Level l){
		links = new Place[4];
		
		position = new int[2];
		
		visible = true;
		this.units = new LinkedList<Unit>();
		this.obj = new LinkedList<Obj>();
		lastseen = null;
		
		level = l;
	}

	public Place(Level l, int w, int h){
		this(l);
		this.position[0] = w;
		this.position[1] = h;
	}
	
	public void onTouch(Unit u){
		for(int i =0; i<units.size(); i++){
			Unit o = units.get(i);
			if(o.onTouch(u)){
				units.remove(o);
				i--;
				if(o instanceof Enemy)
					level.enemys.remove(o);
		}
		}
//		for(Unit o : units){	//TODO FIX OTHERS
//			if(o.onTouch(u)){
//					units.remove(o);
//					if(o instanceof Enemy)
//						level.enemys.remove(o);
//			}
//		}
		for(Obj o : obj){
			if(o.onTouch(u))
					obj.remove(o);
		}
	}
	
//	public void update(){
//		LinkedList<Unit> un = (LinkedList<Unit>) this.units.clone();
//		for(Unit o : un){
//			if(o instanceof Enemy)
//				((Enemy) o).update();
//		}
//		this.units = un;
//	}
	
	public Place clone(){
		Place p = new Place(this.level);
		p.links = this.links.clone();
		p.obj = (LinkedList<Obj>) this.obj.clone();
		p.position = this.position.clone();
		p.units = (LinkedList<Unit>) this.units.clone();
		p.visible = this.visible;
		
		return p;
	}
}