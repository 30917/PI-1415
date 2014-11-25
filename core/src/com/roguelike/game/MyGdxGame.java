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
	
//    private TiledMap tiledMap;
//    private TiledMapRenderer tiledMapRenderer;
//    TiledMapStage stage;
//    
//    public Unit player;
	
    Game G;
    Assets A;
    
	@Override
	public void create () {
		
		//G = new Game(Gdx.files.getLocalStoragePath()+"bin/map1.txt");
		G = new Game();
		
		A=new Assets();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		
//		text = new Texture(Gdx.files.internal("pattern.png"));
//		text2 = new Texture(Gdx.files.internal("pattern2.png"));
		
//		tiledMap = new TiledMap();
//        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
//        
//        //TileUtils.fillTileLayer(new Texture("pattern.png"), 20, 15, "testlayer", tiledMap);
//        
//        stage = new TiledMapStage(tiledMap, player);
//        Gdx.input.setInputProcessor(stage);
//        
//        TileActor start = (TileActor) stage.hit(stage.entry[0], stage.entry[1], true);
//        
//        player.tileActor = start;
//        
//        start.unit = player;
        
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
				Texture t = findTileText(G.level.map[x][y]);
				batch.draw(t, t.getWidth()*x, t.getHeight()*y);
			}
		}
		batch.draw(A.text2, 
				A.text2.getWidth()*G.player.position[0], 
				A.text2.getHeight()*G.player.position[1]);
		batch.end();
		
		
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)) 
			G.move(Game.DOWN);
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)) 
			G.move(Game.LEFT);
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) 
			G.move(Game.RIGHT);
		if(Gdx.input.isKeyJustPressed(Keys.UP)) 
			G.move(Game.UP);
		
		
//        tiledMapRenderer.setView(camera);
//        tiledMapRenderer.render();
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
		A.dispose();
		batch.dispose();
	}
	
}
