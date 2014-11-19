package com.roguelike.game;

import java.io.IOException;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TiledMapStage extends Stage {

    public TiledMap tiledMap;
    
    //should be inside TiledMap, so we can save several maps (must extend TiledMap)
    public int[] entry;
    public int[] exit;

    public TiledMapStage(TiledMap tiledMap, Unit player) {
        this.tiledMap = tiledMap;
        
        
        try {	int[][] doors = TileUtils.importMap("maps/map1.txt",tiledMap);
        		entry = doors[0];
        		exit = doors[1];
		} catch (IOException e) {e.printStackTrace();}

        for (MapLayer layer : tiledMap.getLayers()) {
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
            createActorsForLayer(tiledLayer);
        }
    }

    private void createActorsForLayer(TiledMapTileLayer tiledLayer) {
        for (int x = 0; x < tiledLayer.getWidth(); x++) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                TileActor actor = new TileActor(tiledMap, tiledLayer, cell, x, y);
                actor.setBounds(x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(), tiledLayer.getTileWidth(),
                        tiledLayer.getTileHeight());
 //TODO               actor.setBounds(x * 32, y * 32, 32, 32);
                addActor(actor);
                EventListener eventListener = new TiledMapClickListener(actor);
                actor.addListener(eventListener);
            }
        }
    }
    
    public class TiledMapClickListener extends ClickListener {

        private TileActor actor;

        public TiledMapClickListener(TileActor actor) {
            this.actor = actor;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
        	actor.clicked(event, x, y);
//TODO            System.out.println(actor.cell + " has been clicked.");
//            actor.cell.getTile().getTextureRegion().setTexture(new Texture("pattern2.png"));
        }
    }
}