package com.roguelike.game;

import java.util.Random;
import java.util.Stack;

public class Level{
	Place[][] map;
	int[] entry;
	int[] exit;
	
	static Random random = new Random();
	
	//TODO generate level
	public Level(){
		entry = new int[2];
		exit = new int[2];
		int size = 15;
		this.map = new Place[size][size];
		for(int i=0; i<size; i++)
			for(int j=0; j<size; j++)
				map[i][j] = new Place(i,j);
	}
	public Level(int w, int h){
		entry = new int[2];
		exit = new int[2];
		this.map = new Place[w][h];
		for(int i=0; i<w; i++){
			for(int j=0; j<h; j++){
				
				map[i][j] = new Place(i,j);
			}
		}
	}
	
	public static Level randomLevel(){
		Level lv = new Level();
		int[] entry = {0,7};
		Place current = lv.map[entry[0]][entry[1]];
		current.visible = true;
		
		Stack<Place> s = new Stack<Place>();
		
		while(true){
			Place unvisited = lv.linkRandomUnvisited(current);
			if(unvisited != null){
				s.push(current);
				current = unvisited;
				current.visible=true;
				continue;
			} else if(!s.isEmpty()){
				current = s.pop();
			} else{
				for(int i=0; i<15; i++){
					for(int j=0; j<15; j++){
						if(!lv.map[i][j].visible){
							current = lv.map[i][j];
							current.visible = true;
							continue;
						}
					}
				}
				break;
			}
		}
		return lv;
	}
	
	public Place linkRandomUnvisited(Place p){
		boolean r1 = random.nextBoolean();
		boolean r2 = random.nextBoolean();
		
		Place c;
		if(r1 && r2){
			c =getPlace(p,Game.DOWN);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, true,false,false,false);
				Game.linkTile(c, this, false,false,false,true);
				return p.links[Game.DOWN];
			}
			c=getPlace(p,Game.LEFT);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, false,true,false,false);
				Game.linkTile(c, this, false,false,true,false);
				return p.links[Game.LEFT];
			}
			c=getPlace(p,Game.RIGHT);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, false,false,true,false);
				Game.linkTile(c, this, false,true,false,false);
				return p.links[Game.RIGHT];
			}
			c=getPlace(p,Game.UP);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, false,false,false,true);
				Game.linkTile(c, this, true,false,false,false);
				return p.links[Game.UP];
			}
		} else 		if(r1){
			c=getPlace(p,Game.UP);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, false,false,false,true);
				Game.linkTile(c, this, true,false,false,false);
				return p.links[Game.UP];
			}
			c=getPlace(p,Game.RIGHT);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, false,false,true,false);
				Game.linkTile(c, this, false,true,false,false);
				return p.links[Game.RIGHT];
			}
			c=getPlace(p,Game.LEFT);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, false,true,false,false);
				Game.linkTile(c, this, false,false,true,false);
				return p.links[Game.LEFT];
			}
			c =getPlace(p,Game.DOWN);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, true,false,false,false);
				Game.linkTile(c, this, false,false,false,true);
				return p.links[Game.DOWN];
			}
		} else 		if(r2){
			c=getPlace(p,Game.LEFT);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, false,true,false,false);
				Game.linkTile(c, this, false,false,true,false);
				return p.links[Game.LEFT];
			}
			c=getPlace(p,Game.UP);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, false,false,false,true);
				Game.linkTile(c, this, true,false,false,false);
				return p.links[Game.UP];
			}
			c =getPlace(p,Game.DOWN);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, true,false,false,false);
				Game.linkTile(c, this, false,false,false,true);
				return p.links[Game.DOWN];
			}
			c=getPlace(p,Game.RIGHT);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, false,false,true,false);
				Game.linkTile(c, this, false,true,false,false);
				return p.links[Game.RIGHT];
			}
		} else 		if(true){
			c=getPlace(p,Game.RIGHT);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, false,false,true,false);
				Game.linkTile(c, this, false,true,false,false);
				return p.links[Game.RIGHT];
			}
			c =getPlace(p,Game.DOWN);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, true,false,false,false);
				Game.linkTile(c, this, false,false,false,true);
				return p.links[Game.DOWN];
			}
			c=getPlace(p,Game.UP);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, false,false,false,true);
				Game.linkTile(c, this, true,false,false,false);
				return p.links[Game.UP];
			}
			c=getPlace(p,Game.LEFT);
			if(c!=null && !c.visible){
				Game.linkTile(p, this, false,true,false,false);
				Game.linkTile(c, this, false,false,true,false);
				return p.links[Game.LEFT];
			}
		}

			
		return null;
	}
	
	public Place getPlace(Place p, int cod){
		if(cod ==Game.DOWN && (p.position[1]-1)>=0)
			return map[p.position[0]][p.position[1]-1];
		if(cod ==Game.LEFT && (p.position[0]-1)>=0)
			return map[p.position[0]-1][p.position[1]];
		if(cod ==Game.RIGHT && (p.position[0]+1)<map.length)	//TODO altura pode ser diferente da largura
			return map[p.position[0]+1][p.position[1]];
		if(cod ==Game.UP && (p.position[1]+1)<map.length)
			return map[p.position[0]][p.position[1]+1];
		return null;
	}
	
	public static void linkPlaces(Place a, Place b){
		
	}
	
}