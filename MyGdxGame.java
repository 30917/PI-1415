package com.roguelike.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame extends ApplicationAdapter {
	//SpriteBatch batch;
	//Texture img;
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle bucket, col;
	private Array<Rectangle> raindrops;
	private long lastDropTime;
	
	private BitmapFont font;
	private int counter;
	
	@Override
	public void create () {
		
		font = new BitmapFont();
        font.setColor(Color.RED);
        counter = 0;
        
	    // load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
	
	    // load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		
	    // start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();
		rainMusic.setVolume(0.2f);
		
	    // create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		
	    // create a Rectangle to logically represent the bucket		
		bucket = new Rectangle();
		bucket.x = 800/ 2 - 64 / 2; // center the bucket horizontally
		bucket.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
		bucket.width = 64;
		bucket.height = 64;
		
		col = new Rectangle();
		col.x = 800/ 2 - 30 / 2; // center the bucket horizontally
		col.y = 60; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
		col.width = 30;
		col.height = 5;
		
	    // create the raindrops array and spawn the first raindrop
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
		
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		// clear the screen with a dark blue color. The
	    // arguments to glClearColor are the red, green
	    // blue and alpha component in the range [0,1]
	    // of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    // tell the camera to update its matrices.
		camera.update();
		
		// tell the SpriteBatch to render in the
	    // coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);
		
		// begin a new batch and draw the bucket and
	    // all drops
		batch.begin();
		font.draw(batch, Integer.toString(counter),700,400);
		batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle raindrop: raindrops){
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();
		
		// process user input
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) 
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) 
			bucket.x += 200 * Gdx.graphics.getDeltaTime();
		
	    // make sure the bucket stays within the screen bounds
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;
		
		col.x = bucket.x-17;
	
	    // check if we need to create a new raindrop		
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnRaindrop();
		
		// move the raindrops, remove any that are beneath the bottom edge of
	    // the screen or that hit the bucket. In the later case we play back
	    // a sound effect as well.
		Iterator<Rectangle> iter = raindrops.iterator();
		while(iter.hasNext()){
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0 )
				iter.remove();
			if(raindrop.overlaps(col)){
				dropSound.play();
				iter.remove();
				counter++;
			}
		}
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
	}
	
	@Override
	public void dispose(){
		// dispose of all the native resources
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
		font.dispose();
	}
	
	private void spawnRaindrop(){
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 14;
		raindrop.height = 14;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}
}
