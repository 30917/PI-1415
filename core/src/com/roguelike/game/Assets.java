package com.roguelike.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets{
	//SpriteBatch batch;
	//Texture img;
	public Texture dropImage_small;
	public Texture dropImage;
	public Texture bucketImage;
	public Texture text;
	public Texture text2;
	public Texture dotclear;
	public Texture red_unit;
	public Texture power_green;
	public Texture power_orange;
	public Texture power_pink;
	public Texture power_blue;
	
	public Texture down_left_right_up;
	public Texture down_left_right;
	public Texture down_left_up;
	public Texture down_left;
	public Texture down_right_up;
	public Texture down_right;
	public Texture down_up;
	public Texture down;
	public Texture left_right_up;
	public Texture left_right;
	public Texture left_up;
	public Texture left;
	public Texture right_up;
	public Texture right;
	public Texture up;
	
	public BitmapFont font;

	public Assets() {
		
		dropImage_small = new Texture(Gdx.files.internal("droplet_small.png"));
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		text = new Texture("pattern.png");
		text2 = new Texture("pattern2.png");
		dotclear = new Texture("dot_clear.png");
		red_unit = new Texture(Gdx.files.internal("red_unit.png"));
		power_green = new Texture(Gdx.files.internal("power_green.png"));
		power_orange = new Texture(Gdx.files.internal("power_orange.png"));
		power_pink = new Texture(Gdx.files.internal("power_pink.png"));
		power_blue = new Texture(Gdx.files.internal("power_blue.png"));
		
		
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
		
		font = new BitmapFont();
        font.setColor(Color.RED);
	}
	
	public void dispose(){
		dropImage.dispose();
		bucketImage.dispose();
		font.dispose();
		text.dispose();
		text2.dispose();
	}

}
