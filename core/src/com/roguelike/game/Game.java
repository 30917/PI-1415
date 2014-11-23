package com.roguelike.game;

import java.util.LinkedList;
import java.util.Stack;


public class Game {
	
	private static final int LEFT = 0;
	
	public Player player;
	
	public BigMap bigmap;
	
	public Level level;
	
	
	
	public Game(){
		
		this.player = new Player();
		
		this.bigmap = new BigMap();
		
		
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
		
		public Level(){
			int size = 5;
			//TODO generate level
			this.map = new Place[size][size];
			for(int i=0; i<size; i++){
				for(int j=0; j<size; j++){
					
					map[i][j] = new Place(-1);
				}
			}
		}
		
	}
	
	public class Place{
		Place[] links;
		
		LinkedList<Unit> units;
		LinkedList<Effect> effects;
		
		public Place(int cod){
			
			//TODO connections
			
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
	
	
}
