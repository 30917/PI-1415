package com.roguelike.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Unit {
	
	public int hp;
	public Texture text;
	//public TextureRegion region;
	public TileActor tileActor;
	
	public Unit(Texture t){
		text = t;
		tileActor = null;
	}
	
	public void draw(Batch batch, float x, float y) {
		//batch.draw(text, tileActor.getX(), tileActor.getY());
		
	}

}
