package com.roguelike.game;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

public class CoreGame implements Serializable{
	
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
	
	//Enemy e;
	
	public CoreGame(){
		this.bigmap = new BigMap();
		
		this.level = bigmap.levels[bigmap.currentlevel];
		
		this.player = new Player();
		
//		e = new Enemy(this.level);
//		e.following=player;
//		e.position[0] = 2;
//		e.position[1] = 2;
//		this.level.getPlace(e.position).units.add(e);
		player.update();
	}
	
	public CoreGame(String name){
		
		this.bigmap = new BigMap();
		
		try {
			this.level = Level.importLevel(this, name);
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
			turn();
			p.links[dir].onTouch(player);
			
			//map update
			//System.out.println(e.position[0]+" "+e.position[1]);
			if(player.position[0] == level.exit[0] && player.position[1] == level.exit[1]){
				System.out.println("test");
				bigmap.currentlevel = (bigmap.currentlevel+1) % bigmap.levels.length;
				level = bigmap.levels[bigmap.currentlevel];
				player.position[0] = level.entry[0];
				player.position[1] = level.entry[1];
			}
			
			player.update();
		}
		
		//winning
//		if(player.hp<=0){
//			
//		}
		
	}
	
	private void turn(){
		//Level update
		for(Enemy e : level.enemys){
			if(getViewDistance(e.level.getPlace(e.position))<=1){
				e.following=player;
			}
			e.update();
		}
	}
	
	public static void saveGame(CoreGame g, String file){
		
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
	
	public static CoreGame loadGame(String file){
		CoreGame gamenew = null;

        // Deserialize in to new class object
        try {
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream si = new ObjectInputStream(fi);
            gamenew = (CoreGame) si.readObject();
            si.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return gamenew;
		
	}
	
	public static CoreGame testsave(){
		CoreGame game = new CoreGame();
		
		saveGame(game, "cde.tmp");
		
		CoreGame gamenew = loadGame("cde.tmp");
		
        return gamenew;
	}
	
//	public static void main(String [] args){
//		CoreGame game = new CoreGame();
//		try {
//			game.level = game.level.importLevel(game, "map1.txt");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public class Player extends Unit{
		/**
		 * 
		 */
		private static final long serialVersionUID = 115193941679906843L;
		PU[] powers;
		LinkedList<PU> activepowers;
		public LinkedList<Place> view;
		int viewrange;
		
		public Player(){	//TODO
			super();
			this.ap = 20;
			this.position[0] = level.entry[0];
			this.position[1] = level.entry[1];
			this.powers = new PU[3]; //TODO create effects/powers
			this.viewrange = 0;
			this.view = new LinkedList<Place>();
			activepowers = new LinkedList<PU>();
		}
		
		public void update(){
			updateView();
			updatePowers();
		}
		
		public void updateView(){
			viewrange = 0;
			player.view.clear();
			Place p = level.getPlace(player.position);
			player.view.add(p);
			for(int dir = 0; dir<4; dir++){
				//view.add(level.getPlaceAtDir(p, dir));
				Place t = p.links[dir];
				if(t!=null){
					player.view.add(t);
					t=t.links[dir];
					if(t!=null)
						player.view.add(t);
				}
			}
		}
		
		public void updatePowers(){
			for(PU o : activepowers){
				if(!o.onAction(this)){
					activepowers.remove(o);
				}
			}
		}
		
		public void usePower(int i){
			if(powers[i]==null)
				return;
			if(powers[i].onAction(this))
				activepowers.add(powers[i]);
			if(powers[i].charges<=0)
				powers[i] = null;
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
