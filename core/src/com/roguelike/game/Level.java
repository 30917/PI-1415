package com.roguelike.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;

public class Level implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6151731004362315378L;
	Place[][] map;
	int[] entry;
	int[] exit;
	
	static Random random = new Random();
	
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
		lv.entry[0] = random.nextInt(14);
		lv.entry[1] = random.nextInt(14);
		lv.exit[0] = random.nextInt(14);
		lv.exit[1] = random.nextInt(14);
		
		Place current = lv.map[lv.entry[0]][lv.entry[1]];
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
				//add potions-----
				int c = random.nextInt(100);
				if(c<10){
					current.obj.add(new Potion());
				}
				//---------------
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
				Level.linkTile(p, this, true,false,false,false);
				Level.linkTile(c, this, false,false,false,true);
				return p.links[Game.DOWN];
			}
			c=getPlace(p,Game.LEFT);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, false,true,false,false);
				Level.linkTile(c, this, false,false,true,false);
				return p.links[Game.LEFT];
			}
			c=getPlace(p,Game.RIGHT);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, false,false,true,false);
				Level.linkTile(c, this, false,true,false,false);
				return p.links[Game.RIGHT];
			}
			c=getPlace(p,Game.UP);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, false,false,false,true);
				Level.linkTile(c, this, true,false,false,false);
				return p.links[Game.UP];
			}
		} else 		if(r1){
			c=getPlace(p,Game.UP);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, false,false,false,true);
				Level.linkTile(c, this, true,false,false,false);
				return p.links[Game.UP];
			}
			c=getPlace(p,Game.RIGHT);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, false,false,true,false);
				Level.linkTile(c, this, false,true,false,false);
				return p.links[Game.RIGHT];
			}
			c=getPlace(p,Game.LEFT);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, false,true,false,false);
				Level.linkTile(c, this, false,false,true,false);
				return p.links[Game.LEFT];
			}
			c =getPlace(p,Game.DOWN);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, true,false,false,false);
				Level.linkTile(c, this, false,false,false,true);
				return p.links[Game.DOWN];
			}
		} else 		if(r2){
			c=getPlace(p,Game.LEFT);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, false,true,false,false);
				Level.linkTile(c, this, false,false,true,false);
				return p.links[Game.LEFT];
			}
			c=getPlace(p,Game.UP);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, false,false,false,true);
				Level.linkTile(c, this, true,false,false,false);
				return p.links[Game.UP];
			}
			c =getPlace(p,Game.DOWN);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, true,false,false,false);
				Level.linkTile(c, this, false,false,false,true);
				return p.links[Game.DOWN];
			}
			c=getPlace(p,Game.RIGHT);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, false,false,true,false);
				Level.linkTile(c, this, false,true,false,false);
				return p.links[Game.RIGHT];
			}
		} else 		if(true){
			c=getPlace(p,Game.RIGHT);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, false,false,true,false);
				Level.linkTile(c, this, false,true,false,false);
				return p.links[Game.RIGHT];
			}
			c =getPlace(p,Game.DOWN);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, true,false,false,false);
				Level.linkTile(c, this, false,false,false,true);
				return p.links[Game.DOWN];
			}
			c=getPlace(p,Game.UP);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, false,false,false,true);
				Level.linkTile(c, this, true,false,false,false);
				return p.links[Game.UP];
			}
			c=getPlace(p,Game.LEFT);
			if(c!=null && !c.visible){
				Level.linkTile(p, this, false,true,false,false);
				Level.linkTile(c, this, false,false,true,false);
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
		if(cod ==Game.RIGHT && (p.position[0]+1)<map.length)//TODO altura pode ser diferente da largura?
			return map[p.position[0]+1][p.position[1]];
		if(cod ==Game.UP && (p.position[1]+1)<map.length)
			return map[p.position[0]][p.position[1]+1];
		return null;
	}
	
	public Level importLevel(Game game, String name) throws IOException { //TODO make static?
		
		BufferedReader reader = new BufferedReader(new FileReader(name));
		String line = reader.readLine();
		StringTokenizer st = new StringTokenizer(line);
		String token="fail";
		
		//map size
		int w = Integer.parseInt(st.nextToken());
		int h = Integer.parseInt(st.nextToken());
		
	//		boolean visible = true;
	//		float opacity = 1.0f;
		
		Level lv = new Level(w,h);
		
		// ler as linhas
		for (int linha = h-1; (line = reader.readLine()) != null; linha--) {
			st = new StringTokenizer(line);
			for (int coluna=0; st.hasMoreTokens(); coluna++) {
				token = st.nextToken();
				if (token.startsWith("(")) {
					st = new StringTokenizer(token," (),");
					lv.entry[0] = Integer.parseInt(st.nextToken());
					lv.entry[1] = Integer.parseInt(st.nextToken());
					line = reader.readLine();
					st = new StringTokenizer(line, " (),");
					lv.exit[0] = Integer.parseInt(st.nextToken());
					lv.exit[1] = Integer.parseInt(st.nextToken());
					break;
				}
				
				//int id = Integer.parseInt(token);
	
				Place tile = lv.map[coluna][linha];
				tile.visible = true;
				
				if(token.equals("0")){
	
				}else if(token.equals("+")){
					Level.linkTile(tile, lv, true, true, true, true);	//down,left,right,up
				}else if(token.equals("T")){
					Level.linkTile(tile, lv, true, true, true, false);
				}else if(token.equals("{")){
					Level.linkTile(tile, lv, true, true, false, true);
				}else if(token.equals("7")){
					Level.linkTile(tile, lv, true, true, false, false);
				}else if(token.equals("}")){
					Level.linkTile(tile, lv, true, false, true, true);
				}else if(token.equals("F")){
					Level.linkTile(tile, lv, true, false, true, false);
				}else if(token.equals("|")){
					Level.linkTile(tile, lv, true, false, false, true);
				}else if(token.equals("v")){
					Level.linkTile(tile, lv, true, false, false, false);
				}else if(token.equals("A")){
					Level.linkTile(tile, lv, false, true, true, true);
				}else if(token.equals("-")){
					Level.linkTile(tile, lv, false, true, true, false);
				}else if(token.equals("J")){
					Level.linkTile(tile, lv, false, true, false, true);
				}else if(token.equals("<")){
					Level.linkTile(tile, lv, false, true, false, false);
				}else if(token.equals("L")){
					Level.linkTile(tile, lv, false, false, true, true);
				}else if(token.equals(">")){
					Level.linkTile(tile, lv, false, false, true, false);
				}else if(token.equals("^")){
					Level.linkTile(tile, lv, false, false, false, true);
				}else
					tile = new Place();	//TODO error handling
				//tile.setId(id);
				
			}
		}
		
		//tiledMap.getLayers().add(layer);
		reader.close();
		return lv;
	}
	
	public static void linkTile(Place tile, Level lv, boolean a, boolean b, boolean c, boolean d){
		if(a)
			tile.links[Game.DOWN]=lv.map[tile.position[0]][tile.position[1]-1];
		if(b)
			tile.links[Game.LEFT]=lv.map[tile.position[0]-1][tile.position[1]];
		if(c)
			tile.links[Game.RIGHT]=lv.map[tile.position[0]+1][tile.position[1]];
		if(d)
			tile.links[Game.UP]=lv.map[tile.position[0]][tile.position[1]+1];
	}
	
	public static void linkPlaces(Place a, Place b){
		
	}
	
}