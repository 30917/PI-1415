package com.roguelike.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {

    final MyGdxGame game;

    OrthographicCamera camera;

    public MainMenuScreen(final MyGdxGame gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.A.font.draw(game.batch, "Roguelite", 100, 150);
        game.A.font.draw(game.batch, "Load Game", 100, 100);
        game.A.font.draw(game.batch, "New game", 200, 100);

        game.batch.end();

        if (Gdx.input.isTouched()) {
        	Vector3 touchPos = new Vector3();
        	touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        	camera.unproject(touchPos);
//        	if(touchPos.y - 150 && touchPos.y + 50)
//        		if(touchPos.x - 150 && touchPos.x +50)
        			game.setScreen(new GameScreen(game,false));
        }
//        // process user input
//        if (Gdx.input.isTouched()) {
//            Vector3 touchPos = new Vector3();
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//            camera.unproject(touchPos);
//            bucket.x = touchPos.x - 64 / 2;
//        }
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}