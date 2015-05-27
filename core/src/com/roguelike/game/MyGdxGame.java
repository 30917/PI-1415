package com.roguelike.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.roguelike.game.Game.*;

public class MyGdxGame extends ApplicationAdapter {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
//    TiledMapStage stage;
	
    Game G;
    Assets A;
    
    boolean sav;
    boolean load;
    
	@Override
	public void create () {
		
		sav =true;
		load = true;
		//G = new Game(Gdx.files.getLocalStoragePath()+"bin/map1.txt");
		if(load)
			G = Game.loadGame("cde.tmp");
		else
			G = new Game();
		
		A=new Assets();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		
//        //TileUtils.fillTileLayer(new Texture("pattern.png"), 20, 15, "testlayer", tiledMap);
//        
//        stage = new TiledMapStage(tiledMap, player);
//        Gdx.input.setInputProcessor(stage);
//        
//        TileActor start = (TileActor) stage.hit(stage.entry[0], stage.entry[1], true);
        
	}

	
	@Override
	public void render () {
		// red green blue alpha, range [0,1] of color used to clear screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		batch.begin();
		int sizex = G.level.map.length;
		int sizey = G.level.map[0].length;
		for(int y=0; y<sizey; y++){
			for(int x=0; x<sizex; x++){
				Place p = G.level.map[x][y];
				Texture t = findTileText(p);
				batch.draw(t, t.getWidth()*x, t.getHeight()*y);
				for(Obj o : p.obj){
					batch.draw(A.dropImage_small, 
							t.getWidth()*x+12, 
							t.getHeight()*y+12);
				}
				//batch.draw(A.dotclear, t.getWidth()*x, t.getHeight()*y);
			}
		}
		batch.draw(A.text2, 
				A.text2.getWidth()*G.player.position[0], 
				A.text2.getHeight()*G.player.position[1]);
		
		batch.draw(A.dotclear, 
				A.dotclear.getWidth()*G.level.exit[0], 
				A.dotclear.getHeight()*G.level.exit[1]);
		
		A.font.draw(batch, Integer.toString(G.player.hp),700,400);
		A.font.draw(batch, Integer.toString(G.level.exit[0]),700,300);
		A.font.draw(batch, Integer.toString(G.level.exit[1]),720,300);
		batch.end();
		
		
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)) 
			G.move(Game.DOWN);
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)) 
			G.move(Game.LEFT);
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) 
			G.move(Game.RIGHT);
		if(Gdx.input.isKeyJustPressed(Keys.UP)) 
			G.move(Game.UP);
		

//        stage.draw();
	}
	
	public Texture findTileText(Place p){
//		if(G.getViewDistance(p)>1){
//			return A.text;
//		}
		if(p.links[Game.DOWN]!=null){
			if(p.links[Game.LEFT]!=null){
				if(p.links[Game.RIGHT]!=null){
					if(p.links[Game.UP]!=null)
						return A.down_left_right_up;
					return A.down_left_right;
				}
				if(p.links[Game.UP]!=null)
					return A.down_left_up;
				return A.down_left;
			}
			if(p.links[Game.RIGHT]!=null){
				if(p.links[Game.UP]!=null)
					return A.down_right_up;
				return A.down_right;
			}
			if(p.links[Game.UP]!=null)
				return A.down_up;
			return A.down;
		}
		if(p.links[Game.LEFT]!=null){
			if(p.links[Game.RIGHT]!=null){
				if(p.links[Game.UP]!=null)
					return A.left_right_up;
				return A.left_right;
			}
			if(p.links[Game.UP]!=null)
				return A.left_up;
			return A.left;
		}
		if(p.links[Game.RIGHT]!=null){
			if(p.links[Game.UP]!=null)
				return A.right_up;
			return A.right;
		}
		if(p.links[Game.UP]!=null)
			return A.up;
		return A.text;
	}
	
	@Override
	public void dispose(){
		if(sav)
			Game.saveGame(G, "cde.tmp");
		A.dispose();
		batch.dispose();
	}
	
}
