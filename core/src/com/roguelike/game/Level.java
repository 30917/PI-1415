package com.roguelike.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
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
	LinkedList<Enemy> enemys;
	
	static Random random = new Random();
	
	public Level(){
		entry = new int[2];
		exit = new int[2];
		int size = 15;
		this.map = new Place[size][size];
		for(int i=0; i<size; i++)
			for(int j=0; j<size; j++)
				map[i][j] = new Place(this,i,j);
		enemys = new LinkedList<Enemy>();
	}
	public Level(int w, int h){
		entry = new int[2];
		exit = new int[2];
		this.map = new Place[w][h];
		for(int i=0; i<w; i++){
			for(int j=0; j<h; j++){
				
				map[i][j] = new Place(this,i,j);
			}
		}
		enemys = new LinkedList<Enemy>();
	}
	
	public static Level randomLevel(){
		Level lv = new Level();
		lv.entry[0] = random.nextInt(14);
		lv.entry[1] = random.nextInt(14);
		lv.exit[0] = random.nextInt(14);
		lv.exit[1] = random.nextInt(14);
		
		Place current = lv.map[lv.entry[0]][lv.entry[1]];
		current.visible = false;
		
		Stack<Place> s = new Stack<Place>();
		
		while(true){
			Place unvisited = lv.linkRandomUnvisited(current);
			if(unvisited != null){
				s.push(current);
				current = unvisited;
				current.visible=false;
				continue;
			} else if(!s.isEmpty()){
				//add extra passages
				int c = random.nextInt(100);
				if(c<10){
					lv.linkRandom(current);
				}
				//add potions-----
				c = random.nextInt(100);
				if(c<10){
					current.obj.add(new Potion(current));
				}
				if(c>88 && c<90)
					current.obj.add(new PUcloak(current));
				if(c>95){
					current.obj.add(new PUvision(current));
				}
				if(c>90 && c<=95){
					Enemy e = new Enemy(lv,current);
					lv.enemys.add(e);
					//current.units.add(e);
				}
				//---------------
				current = s.pop();
			} else{
				for(int i=0; i<15; i++){
					for(int j=0; j<15; j++){
						if(lv.map[i][j].visible){
							current = lv.map[i][j];
							current.visible = false;
							continue;
						}
					}
				}
				break;
			}
		}
		return lv;
	}
	
	public boolean linkRandom(Place p){
		int i = random.nextInt(3);
		
		for(int a = 0; a<4; a++, i=(i+1) % 4){
			if(p.links[i]==null){
				if(linkPlace(p, i))
					return true;
			}
		}
		return false;
	}
	
	public Place linkRandomUnvisited(Place p){
		boolean r1 = random.nextBoolean();
		boolean r2 = random.nextBoolean();
		
		Place c;
		if(r1 && r2){
			c =getPlaceAtDir(p,CoreGame.DOWN);
			if(c!=null && c.visible){
				Level.linkTile(p, this, true,false,false,false);
				Level.linkTile(c, this, false,false,false,true);
				return p.links[CoreGame.DOWN];
			}
			c=getPlaceAtDir(p,CoreGame.LEFT);
			if(c!=null && c.visible){
				Level.linkTile(p, this, false,true,false,false);
				Level.linkTile(c, this, false,false,true,false);
				return p.links[CoreGame.LEFT];
			}
			c=getPlaceAtDir(p,CoreGame.RIGHT);
			if(c!=null && c.visible){
				Level.linkTile(p, this, false,false,true,false);
				Level.linkTile(c, this, false,true,false,false);
				return p.links[CoreGame.RIGHT];
			}
			c=getPlaceAtDir(p,CoreGame.UP);
			if(c!=null && c.visible){
				Level.linkTile(p, this, false,false,false,true);
				Level.linkTile(c, this, true,false,false,false);
				return p.links[CoreGame.UP];
			}
		} else 		if(r1){
			c=getPlaceAtDir(p,CoreGame.UP);
			if(c!=null && c.visible){
				Level.linkTile(p, this, false,false,false,true);
				Level.linkTile(c, this, true,false,false,false);
				return p.links[CoreGame.UP];
			}
			c=getPlaceAtDir(p,CoreGame.RIGHT);
			if(c!=null && c.visible){
				Level.linkTile(p, this, false,false,true,false);
				Level.linkTile(c, this, false,true,false,false);
				return p.links[CoreGame.RIGHT];
			}
			c=getPlaceAtDir(p,CoreGame.LEFT);
			if(c!=null && c.visible){
				Level.linkTile(p, this, false,true,false,false);
				Level.linkTile(c, this, false,false,true,false);
				return p.links[CoreGame.LEFT];
			}
			c =getPlaceAtDir(p,CoreGame.DOWN);
			if(c!=null && c.visible){
				Level.linkTile(p, this, true,false,false,false);
				Level.linkTile(c, this, false,false,false,true);
				return p.links[CoreGame.DOWN];
			}
		} else 		if(r2){
			c=getPlaceAtDir(p,CoreGame.LEFT);
			if(c!=null && c.visible){
				Level.linkTile(p, this, false,true,false,false);
				Level.linkTile(c, this, false,false,true,false);
				return p.links[CoreGame.LEFT];
			}
			c=getPlaceAtDir(p,CoreGame.UP);
			if(c!=null && c.visible){
				Level.linkTile(p, this, false,false,false,true);
				Level.linkTile(c, this, true,false,false,false);
				return p.links[CoreGame.UP];
			}
			c =getPlaceAtDir(p,CoreGame.DOWN);
			if(c!=null && c.visible){
				Level.linkTile(p, this, true,false,false,false);
				Level.linkTile(c, this, false,false,false,true);
				return p.links[CoreGame.DOWN];
			}
			c=getPlaceAtDir(p,CoreGame.RIGHT);
			if(c!=null && c.visible){
				Level.linkTile(p, this, false,false,true,false);
				Level.linkTile(c, this, false,true,false,false);
				return p.links[CoreGame.RIGHT];
			}
		} else 		if(true){
			c=getPlaceAtDir(p,CoreGame.RIGHT);
			if(c!=null && c.visible){
				Level.linkTile(p, this, false,false,true,false);
				Level.linkTile(c, this, false,true,false,false);
				return p.links[CoreGame.RIGHT];
			}
			c =getPlaceAtDir(p,CoreGame.DOWN);
			if(c!=null && c.visible){
				Level.linkTile(p, this, true,false,false,false);
				Level.linkTile(c, this, false,false,false,true);
				return p.links[CoreGame.DOWN];
			}
			c=getPlaceAtDir(p,CoreGame.UP);
			if(c!=null && c.visible){
				Level.linkTile(p, this, false,false,false,true);
				Level.linkTile(c, this, true,false,false,false);
				return p.links[CoreGame.UP];
			}
			c=getPlaceAtDir(p,CoreGame.LEFT);
			if(c!=null && c.visible){
				Level.linkTile(p, this, false,true,false,false);
				Level.linkTile(c, this, false,false,true,false);
				return p.links[CoreGame.LEFT];
			}
		}

			
		return null;
	}
	
	public Place getPlace(int[] p){
		return map[p[0]][p[1]];
	}
	public Place getPlace(int a, int b){
		return map[a][b];
	}
	
	public Place getPlaceAtDir(Place p, int cod){
		if(cod ==CoreGame.DOWN && (p.position[1]-1)>=0)
			return map[p.position[0]][p.position[1]-1];
		if(cod ==CoreGame.LEFT && (p.position[0]-1)>=0)
			return map[p.position[0]-1][p.position[1]];
		if(cod ==CoreGame.RIGHT && (p.position[0]+1)<map.length)//TODO altura pode ser diferente da largura?
			return map[p.position[0]+1][p.position[1]];
		if(cod ==CoreGame.UP && (p.position[1]+1)<map.length)
			return map[p.position[0]][p.position[1]+1];
		return null;
	}
	
	public static Level importLevel(CoreGame game, String name) throws IOException { //TODO make static?
		
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
					tile = new Place(lv);	//TODO error handling
				//tile.setId(id);
				
			}
		}
		
		//tiledMap.getLayers().add(layer);
		reader.close();
		return lv;
	}
	
	public static void linkTile(Place tile, Level lv, boolean a, boolean b, boolean c, boolean d){
		if(a)
			tile.links[CoreGame.DOWN]=lv.map[tile.position[0]][tile.position[1]-1];
		if(b)
			tile.links[CoreGame.LEFT]=lv.map[tile.position[0]-1][tile.position[1]];
		if(c)
			tile.links[CoreGame.RIGHT]=lv.map[tile.position[0]+1][tile.position[1]];
		if(d)
			tile.links[CoreGame.UP]=lv.map[tile.position[0]][tile.position[1]+1];
	}
	
	public boolean linkPlace(Place p, int cod){
		Place c;
		c = getPlaceAtDir(p, cod);
		if(c == null)
			return false;
		if(cod == 0){
			p.links[CoreGame.DOWN]=c;
			c.links[CoreGame.UP]=p;
		} else if(cod == 1){
			p.links[CoreGame.LEFT]=c;
			c.links[CoreGame.RIGHT]=p;
		}else if(cod == 2){
			p.links[CoreGame.RIGHT]=c;
			c.links[CoreGame.LEFT]=p;
		}else if(cod == 3){
			p.links[CoreGame.UP]=c;
			c.links[CoreGame.DOWN]=p;
		}
		return true;
	}
	
}