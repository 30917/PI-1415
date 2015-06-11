package com.roguelike.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game{
	
	public SpriteBatch batch;
	
    Assets A;
    
	@Override
	public void create () {
		
		A=new Assets();
		
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	
	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose(){
		A.dispose();
		batch.dispose();
	}
	
}
