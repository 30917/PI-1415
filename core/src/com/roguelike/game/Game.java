package com.roguelike.game;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

public class Game implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5624055458025129729L;
	
	public static final int DOWN = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	
	public Player player;
	public BigMap bigmap;
	public Level level;
	
	public Game(){
		this.bigmap = new BigMap();
		
		this.level = bigmap.levels[bigmap.currentlevel];
		
		this.player = new Player();
	}
	
	public Game(String name){
		
		this.bigmap = new BigMap();
		
		try {
			this.level = level.importLevel(this, name);
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
			p.links[dir].onTouch(player);
			turn();
		}
		
	}
	
	private void turn(){
		if(player.position[0] == level.exit[0] && player.position[1] == level.exit[1]){
			System.out.println("test");
			bigmap.currentlevel = (bigmap.currentlevel+1) % bigmap.levels.length;
			level = bigmap.levels[bigmap.currentlevel];
			player.position[0] = level.entry[0];
			player.position[1] = level.entry[1];
		}
		
	}
	
	public static void saveGame(Game g, String file){
		
        // Serialize the original class object
        try {
            FileOutputStream fo = new FileOutputStream(file);
            ObjectOutputStream so = new ObjectOutputStream(fo);
            so.writeObject(g);
            so.flush();
            so.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
	}
	
	public static Game loadGame(String file){
		Game gamenew = null;

        // Deserialize in to new class object
        try {
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream si = new ObjectInputStream(fi);
            gamenew = (Game) si.readObject();
            si.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return gamenew;
		
	}
	
	public static Game testsave(){
		Game game = new Game();
		
		saveGame(game, "cde.tmp");
		
		Game gamenew = loadGame("cde.tmp");
		
        return gamenew;
	}
	
	public static void main(String [] args){
		Game game = new Game();
		try {
			game.level = game.level.importLevel(game, "map1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class Player extends Unit{
		/**
		 * 
		 */
		private static final long serialVersionUID = 115193941679906843L;
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
