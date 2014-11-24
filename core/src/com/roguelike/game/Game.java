package com.roguelike.game;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Game {
	
	//private static final int LEFT = 0;
	
	public Player player;
	
	public BigMap bigmap;
	
	public Level level;
	
	
	public Game(){
		
		this.player = new Player();
		
		this.bigmap = new BigMap();

	}
	public Game(String name){
		
		this.player = new Player();
		
		this.bigmap = new BigMap();
		
		try {
			this.level = importLevel(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void move(int x, int y){
		int ax = player.position[0]+x;
		int ay = player.position[1]+y;
		if(ax>=0 && ax<5){
			player.position[0] = ax;
		}
		if(ay>=0 && ay<5){
			player.position[1] = ay;
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
	
	public class Level{
		Place[][] map;
		int[] entry;
		int[] exit;
		
		//TODO generate level
		public Level(){
			entry = new int[2];
			exit = new int[2];
			int size = 5;
			this.map = new Place[size][size];
			for(int i=0; i<size; i++)
				for(int j=0; j<size; j++)
					map[i][j] = new Place();
		}
		public Level(int h, int w){
			entry = new int[2];
			exit = new int[2];
			this.map = new Place[h][w];
			for(int i=0; i<h; i++){
				for(int j=0; j<w; j++){
					
					map[i][j] = new Place();
				}
			}
		}
		
	}
	
	public class Place{
		Place left;
		Place right;
		Place up;
		Place down;
		
		int[] position;
		
		boolean visible;
		boolean seen;
		
		LinkedList<Unit> units;
		LinkedList<Effect> effects;
		//TODO save last seen state?
		
		public Place(){
			
			position = new int[2];
			
			visible = false;
			seen = false;
			this.units = new LinkedList<Game.Unit>();
			this.effects = new LinkedList<Game.Effect>();
		}
	}
	
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
	
	
	public class Player extends Unit{
		Effect[] powers;
		int viewrange;
		
		public Player(){
			super();
			this.powers = new Effect[3]; //TODO create effects/powers
			this.viewrange = 1;
		}
	}
	
	public class Effect{
		
	}
	
	public Level importLevel(String name) throws IOException { //TODO make static?
		
		BufferedReader reader = new BufferedReader(new FileReader(name));
		String line = reader.readLine();
		StringTokenizer st = new StringTokenizer(line);
		String token="fail";
		
		//map size
		int w = Integer.parseInt(st.nextToken());
		int h = Integer.parseInt(st.nextToken());
		
		boolean visible = true;
		float opacity = 1.0f;
		
		Level lv = new Level(h,w);
		
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
	
				Place tile = lv.map[linha][coluna];
				tile.position[0] = linha;
				tile.position[1] = coluna;
				tile.visible = true;
				
				if(token.equals("0")){

				}else if(token.equals("+")){
					tile.down=lv.map[tile.position[0]-1][tile.position[1]];
					tile.left=lv.map[tile.position[0]][tile.position[1]-1];
					tile.right=lv.map[tile.position[0]][tile.position[1]+1];
					tile.up=lv.map[tile.position[0]+1][tile.position[1]];
				}else if(token.equals("T")){
					tile.down=lv.map[tile.position[0]-1][tile.position[1]];
					tile.left=lv.map[tile.position[0]][tile.position[1]-1];
					tile.right=lv.map[tile.position[0]][tile.position[1]+1];
					
				}else if(token.equals("{")){
					tile.down=lv.map[tile.position[0]-1][tile.position[1]];
					tile.left=lv.map[tile.position[0]][tile.position[1]-1];

					tile.up=lv.map[tile.position[0]+1][tile.position[1]];
				}else if(token.equals("7")){
					tile.down=lv.map[tile.position[0]-1][tile.position[1]];
					tile.left=lv.map[tile.position[0]][tile.position[1]-1];


				}else if(token.equals("}")){
					tile.down=lv.map[tile.position[0]-1][tile.position[1]];

					tile.right=lv.map[tile.position[0]][tile.position[1]+1];
					tile.up=lv.map[tile.position[0]+1][tile.position[1]];
				}else if(token.equals("F")){
					tile.down=lv.map[tile.position[0]-1][tile.position[1]];

					tile.right=lv.map[tile.position[0]][tile.position[1]+1];

				}else if(token.equals("|")){
					tile.down=lv.map[tile.position[0]-1][tile.position[1]];


					tile.up=lv.map[tile.position[0]+1][tile.position[1]];
				}else if(token.equals("v")){
					tile.down=lv.map[tile.position[0]-1][tile.position[1]];



				}else if(token.equals("A")){

					tile.left=lv.map[tile.position[0]][tile.position[1]-1];
					tile.right=lv.map[tile.position[0]][tile.position[1]+1];
					tile.up=lv.map[tile.position[0]+1][tile.position[1]];
				}else if(token.equals("-")){

					tile.left=lv.map[tile.position[0]][tile.position[1]-1];
					tile.right=lv.map[tile.position[0]][tile.position[1]+1];

				}else if(token.equals("J")){

					tile.left=lv.map[tile.position[0]][tile.position[1]-1];

					tile.up=lv.map[tile.position[0]+1][tile.position[1]];
				}else if(token.equals("<")){

					tile.left=lv.map[tile.position[0]][tile.position[1]-1];


				}else if(token.equals("L")){


					tile.right=lv.map[tile.position[0]][tile.position[1]+1];
					tile.up=lv.map[tile.position[0]+1][tile.position[1]];
				}else if(token.equals(">")){


					tile.right=lv.map[tile.position[0]][tile.position[1]+1];

				}else if(token.equals("^")){



					tile.up=lv.map[tile.position[0]+1][tile.position[1]];
				}else
					tile = new Place();	//TODO error handling
				//tile.setId(id);
				
			}
		}
		
		//tiledMap.getLayers().add(layer);
		reader.close();
		return lv;
	}	
	
}
