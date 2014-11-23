package com.roguelike.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter {
	
	public static Texture dropImage;
	public static Texture bucketImage;
	public static Texture text;
	public static Texture text2;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private BitmapFont font;
	
//    private TiledMap tiledMap;
//    private TiledMapRenderer tiledMapRenderer;
//    TiledMapStage stage;
//    
//    public Unit player;
	
    Game game;
    
    
	@Override
	public void create () {
		
		game = new Game();
		
		
		
		font = new BitmapFont();
        font.setColor(Color.RED);
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		
		text = new Texture(Gdx.files.internal("pattern.png"));
		text2 = new Texture(Gdx.files.internal("pattern2.png"));
		
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
		int size = 5; //game.bigmap.levels[game.bigmap.currentlevel]. ...
		for(int y=0; y<size; y++){
			for(int x=0; x<size; x++){
				batch.draw(text, text.getWidth()*x, text.getHeight()*y);
			}
		}
		batch.draw(text2, text2.getWidth()*game.player.position[0], text2.getHeight()*game.player.position[1]);
		batch.end();
		
		
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)) 
			game.move(-1,0);
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) 
			game.move(1,0);
		if(Gdx.input.isKeyJustPressed(Keys.UP)) 
			game.move(0,1);
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)) 
			game.move(0,-1);
		
		
//        tiledMapRenderer.setView(camera);
//        tiledMapRenderer.render();
//        stage.draw();
	}
	
	
	@Override
	public void dispose(){
		dropImage.dispose();
		bucketImage.dispose();
		batch.dispose();
		font.dispose();
		text.dispose();
		text2.dispose();
	}
	
}
