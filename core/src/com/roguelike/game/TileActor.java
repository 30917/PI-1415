package com.roguelike.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class TileActor extends Actor {

    private TiledMap tiledMap;
    private TiledMapTileLayer tiledLayer;
    private TiledMapTileLayer.Cell cell;
    
    private int x;
    private int y;
    
    public Unit unit;

    public TileActor(TiledMap tiledMap, TiledMapTileLayer tiledLayer, 
    		TiledMapTileLayer.Cell cell, int x, int y) {
        this.tiledMap = tiledMap;
        this.tiledLayer = tiledLayer;
        this.cell = cell;
        
        this.x = x;
        this.y = y;
        
        this.unit = null;
    }
    
	@Override
	public void draw (Batch batch, float parentAlpha) {
		if (unit != null)
			unit.draw(batch, getX(), getY());
		
		//batch.draw(MyGdxGame.dropImage, this.getX(), this.getY());
	}
	
	public void clicked(InputEvent event, float x, float y) {
		
	}

}