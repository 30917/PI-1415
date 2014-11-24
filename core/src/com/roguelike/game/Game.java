package com.roguelike.game;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Game {
	
	public static final int DOWN = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	
	public Player player;
	public BigMap bigmap;
	public Level level;
	
	public Game(){
		
		this.player = new Player();
		
		this.bigmap = new BigMap();
		
	}
	
	public Game(String name){
		
		this.bigmap = new BigMap();
		
		try {
			this.level = importLevel(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.player = new Player();
		
	}
	
	public void move(int dir){
		Place p = level.map[player.position[0]][player.position[1]];
		if(p.links[dir] != null){
			player.position[0] = p.links[dir].position[0];
			player.position[1] = p.links[dir].position[1];
		}
		
	}
	
	public static void main(String [] args){
		Game game = new Game();
		try {
			game.level = game.importLevel("map1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class BigMap{
		Level[] levels;
		int currentlevel;
		
		public BigMap(){
			//TODO generate map
			this.levels = new Level[3];
			
			this.currentlevel = 0;
		}
		
	}	
	
	public class Player extends Unit{
		Effect[] powers;
		int viewrange;
		
		public Player(){
			super();
			this.position[0] = level.entry[0];
			this.position[1] = level.entry[1];
			this.powers = new Effect[3]; //TODO create effects/powers
			this.viewrange = 1;
		}
	}
	
	
	public Level importLevel(String name) throws IOException { //TODO make static?
		
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
				tile.position[0] = coluna;
				tile.position[1] = linha;
				tile.visible = true;
				
				if(token.equals("0")){

				}else if(token.equals("+")){
					linkTile(tile, lv, true, true, true, true);	//down,left,right,up
				}else if(token.equals("T")){
					linkTile(tile, lv, true, true, true, false);
				}else if(token.equals("{")){
					linkTile(tile, lv, true, true, false, true);
				}else if(token.equals("7")){
					linkTile(tile, lv, true, true, false, false);
				}else if(token.equals("}")){
					linkTile(tile, lv, true, false, true, true);
				}else if(token.equals("F")){
					linkTile(tile, lv, true, false, true, false);
				}else if(token.equals("|")){
					linkTile(tile, lv, true, false, false, true);
				}else if(token.equals("v")){
					linkTile(tile, lv, true, false, false, false);
				}else if(token.equals("A")){
					linkTile(tile, lv, false, true, true, true);
				}else if(token.equals("-")){
					linkTile(tile, lv, false, true, true, false);
				}else if(token.equals("J")){
					linkTile(tile, lv, false, true, false, true);
				}else if(token.equals("<")){
					linkTile(tile, lv, false, true, false, false);
				}else if(token.equals("L")){
					linkTile(tile, lv, false, false, true, true);
				}else if(token.equals(">")){
					linkTile(tile, lv, false, false, true, false);
				}else if(token.equals("^")){
					linkTile(tile, lv, false, false, false, true);
				}else
					tile = new Place();	//TODO error handling
				//tile.setId(id);
				
			}
		}
		
		//tiledMap.getLayers().add(layer);
		reader.close();
		return lv;
	}
	
	public void linkTile(Place tile, Level lv, boolean a, boolean b, boolean c, boolean d){
		if(a)
			tile.links[DOWN]=lv.map[tile.position[0]][tile.position[1]-1];
		if(b)
			tile.links[LEFT]=lv.map[tile.position[0]-1][tile.position[1]];
		if(c)
			tile.links[RIGHT]=lv.map[tile.position[0]+1][tile.position[1]];
		if(d)
			tile.links[UP]=lv.map[tile.position[0]][tile.position[1]+1];
	}
	
	public int getViewDistance(Place p){
		int xdist = player.position[0] - p.position[0];
		xdist = (xdist<0)? xdist*-1: xdist;
		int ydist = player.position[1] - p.position[1];
		ydist = (ydist<0)? ydist*-1: ydist;
		return (xdist > ydist)? xdist: ydist;
	}
	
	public LinkedList<Place> getPlacesAtViewDist(int d){
		LinkedList<Place> l = new LinkedList<Place>();
		
		
		
		return l;
	}
	
}
