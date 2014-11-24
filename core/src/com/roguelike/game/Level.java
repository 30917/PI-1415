package com.roguelike.game;

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
	public Level(int w, int h){
		entry = new int[2];
		exit = new int[2];
		this.map = new Place[w][h];
		for(int i=0; i<w; i++){
			for(int j=0; j<h; j++){
				
				map[i][j] = new Place();
			}
		}
	}
	
}