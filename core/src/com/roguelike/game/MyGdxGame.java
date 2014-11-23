package com.roguelike.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter {
	
	public static Texture dropImage;
	public static Texture bucketImage;
	public static Texture text;
	public static Texture text2;
	
	public static Texture down_left_right_up;
	public static Texture down_left_right;
	public static Texture down_left_up;
	public static Texture down_left;
	public static Texture down_right_up;
	public static Texture down_right;
	public static Texture down_up;
	public static Texture down;
	public static Texture left_right_up;
	public static Texture left_right;
	public static Texture left_up;
	public static Texture left;
	public static Texture right_up;
	public static Texture right;
	public static Texture up;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private BitmapFont font;
	
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    TiledMapStage stage;
    
    public Unit player;
	
    
    
	@Override
	public void create () {
		
		font = new BitmapFont();
        font.setColor(Color.RED);
        
	    // load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		
	    // create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		text = new Texture("pattern.png");
		text2 = new Texture("pattern2.png");
		
		down_left_right_up = new Texture("tiles/down_left_right_up.png");
		down_left_right = new Texture("tiles/down_left_right.png");
		down_left_up = new Texture("tiles/down_left_up.png");
		down_left = new Texture("tiles/down_left.png");
		down_right_up = new Texture("tiles/down_right_up.png");
		down_right = new Texture("tiles/down_right.png");
		down_up = new Texture("tiles/down_up.png");
		down = new Texture("tiles/down.png");
		left_right_up = new Texture("tiles/left_right_up.png");
		left_right = new Texture("tiles/left_right.png");
		left_up = new Texture("tiles/left_up.png");
		left = new Texture("tiles/left.png");
		right_up = new Texture("tiles/right_up.png");
		right = new Texture("tiles/right.png");
		up = new Texture("tiles/up.png");
		
		tiledMap = new TiledMap();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        //TileUtils.fillTileLayer(new Texture("pattern.png"), 20, 15, "testlayer", tiledMap);

        player = new Unit(text2);
        
        stage = new TiledMapStage(tiledMap, player);
        Gdx.input.setInputProcessor(stage);
        
        //TiledMapTileLayer tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        
        TileActor start = (TileActor) stage.hit(stage.entry[0], stage.entry[1], true);
        
        player.tileActor = start;
        
        start.unit = player;
	}

	@Override
	public void render () {
		// arguments to glClearColor are red green blue alpha, range [0,1]
	    // of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    // tell the camera to update its matrices.
		camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        stage.draw();
        
		
		// tell the SpriteBatch to render in the
	    // coordinate system specified by the camera.
//		batch.setProjectionMatrix(camera.combined); //gives error
		
		
		
		
		
		
		// process user input
//		if(Gdx.input.isTouched()){
//			Vector3 touchPos = new Vector3();
//			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//			camera.unproject(touchPos);
//		}
		
//		if(Gdx.input.isKeyPressed(Keys.LEFT)) 
//			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
//		if(Gdx.input.isKeyPressed(Keys.RIGHT)) 
//			bucket.x += 200 * Gdx.graphics.getDeltaTime();
//		if(Gdx.input.isKeyPressed(Keys.UP)) 
//			bucket.y += 200 * Gdx.graphics.getDeltaTime();
//		if(Gdx.input.isKeyPressed(Keys.DOWN)) 
//			bucket.y -= 200 * Gdx.graphics.getDeltaTime();
	}
	
	@Override
	public void dispose(){
		// dispose of all the native resources
		dropImage.dispose();
		bucketImage.dispose();
		batch.dispose();
		font.dispose();
	}
	
}
