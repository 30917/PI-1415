package com.roguelike.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {
	
	final MyGdxGame game;
	
	private OrthographicCamera camera;
	
    CoreGame G;
    boolean autosav;


    public GameScreen(final MyGdxGame gam, boolean load) {
    	game = gam;
    	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		autosav = true;
		//G = new Game(Gdx.files.getLocalStoragePath()+"bin/map1.txt");
		if(load)
			G = CoreGame.loadGame("cde.tmp");
		else
			G = new CoreGame();

    }


    @Override
    public void render(float delta) {
		// red green blue alpha, range [0,1] of color used to clear screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		game.batch.begin();
		Color color = game.batch.getColor();
		int sizex = G.level.map.length;
		int sizey = G.level.map[0].length;
		for(int y=0; y<sizey; y++){
			for(int x=0; x<sizex; x++){
				Place p = G.level.map[x][y];
				
				Texture t = null;
				int dist = G.getViewDistance(p);
				if(dist<=G.player.viewrange || G.player.view.contains(p)){
					p.lastseen = p.clone();
					t = findTileText(p);
					game.batch.draw(t, t.getWidth()*x, t.getHeight()*y);
//					for(Unit o : p.units){
//						if(o instanceof Enemy){
//						batch.draw(A.red_unit, 
//								A.red_unit.getWidth()*o.position[0], 
//								A.red_unit.getHeight()*o.position[1]);
//						A.font.draw(batch, Integer.toString(o.hp),
//								A.red_unit.getHeight()*o.position[0]+16,
//								A.red_unit.getHeight()*o.position[1]+16);
//						}
//					}
				} else if(p.lastseen != null){
					p = p.lastseen;
					t = findTileText(p);
//					Color.alpha(0.5f);
//					Color color = batch.getColor();
//					color.a = Color.alpha(0.5f);
					game.batch.setColor(color.r, color.g, color.b, 0.1f);
					game.batch.draw(t, t.getWidth()*x, t.getHeight()*y);
				}
				if(t!=null){
//					for(Obj o : p.obj){
//						if(o instanceof Potion)
//							batch.draw(A.dropImage_small, 
//								t.getWidth()*x+12, 
//								t.getHeight()*y+12);
//						else if(o instanceof PUvision)
//							batch.draw(A.power_green, 
//								t.getWidth()*x, 
//								t.getHeight()*y);
//					}
				}
				game.batch.setColor(color.r, color.g, color.b, 1);
				//batch.draw(A.dotclear, t.getWidth()*x, t.getHeight()*y);
			}
		}
		game.batch.draw(game.A.text2, 
				game.A.text2.getWidth()*G.player.position[0], 
				game.A.text2.getHeight()*G.player.position[1]);
//		for(Path.Node a : G.e.path.path){
//			batch.draw(A.dropImage_small, 
//					A.text2.getWidth()*a.place.position[0]+12, 
//					A.text2.getHeight()*a.place.position[1]+12);
//		}
		
		game.batch.draw(game.A.dotclear, 
				game.A.dotclear.getWidth()*G.level.exit[0], 
				game.A.dotclear.getHeight()*G.level.exit[1]);
		
		color = game.batch.getColor();
		for(int y=0; y<sizey; y++){
			for(int x=0; x<sizex; x++){
				Place p = G.level.map[x][y];
				
				boolean t = false;
				int dist = G.getViewDistance(p);
				if(dist<=G.player.viewrange || G.player.view.contains(p)){
					t = true;
					for(Unit o : p.units){
						if(o instanceof Enemy){
							System.out.println("m: "+x+" "+y+" - "+o.position[0]+" "+o.position[1]);
						game.batch.draw(game.A.red_unit, 
								game.A.red_unit.getWidth()*o.position[0], 
								game.A.red_unit.getHeight()*o.position[1]);
						game.A.font.draw(game.batch, Integer.toString(o.hp),
								game.A.red_unit.getWidth()*o.position[0]+16,
								game.A.red_unit.getHeight()*o.position[1]+16);
						}
					}
				} else if(p.lastseen != null){
					t = true;
					game.batch.setColor(color.r, color.g, color.b, 0.05f);
				}
				if(t){
					for(Obj o : p.obj){
						if(o instanceof Potion)
							game.batch.draw(game.A.dropImage_small, 
								32*x+12, 
								32*y+12);
						else if(o instanceof PUvision)
							game.batch.draw(game.A.power_green, 
								32*x, 
								32*y);
						else if(o instanceof PUcloak)
							game.batch.draw(game.A.power_blue, 
								32*x, 
								32*y);
					}
				}
				game.batch.setColor(color.r, color.g, color.b, 1);
				//batch.draw(A.dotclear, t.getWidth()*x, t.getHeight()*y);
			}
		}
		
		game.A.font.draw(game.batch, Integer.toString(G.player.hp),650,400);
		
		for(int i = 0,d=420; i<3; i++,d-=20){
			if(G.player.powers[i]!=null){
				game.A.font.draw(game.batch, G.player.powers[i].name,750,d);
				game.A.font.draw(game.batch, Integer.toString(G.player.powers[i].charges),700,d);
			}
		}
		
		game.A.font.draw(game.batch, Integer.toString(G.level.exit[0]),700,300);
		game.A.font.draw(game.batch, Integer.toString(G.level.exit[1]),720,300);
		game.batch.end();
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			game.setScreen(new PauseScreen(game,this));
		}

		if(Gdx.input.isKeyJustPressed(Keys.NUM_1)) 
			G.player.usePower(0);
		if(Gdx.input.isKeyJustPressed(Keys.NUM_2)) 
			G.player.usePower(1);
		if(Gdx.input.isKeyJustPressed(Keys.NUM_3)) 
			G.player.usePower(2);
		
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)) 
			G.move(CoreGame.DOWN);
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)) 
			G.move(CoreGame.LEFT);
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) 
			G.move(CoreGame.RIGHT);
		if(Gdx.input.isKeyJustPressed(Keys.UP)) 
			G.move(CoreGame.UP);

    }
    
	public Texture findTileText(Place p){
		if(p.links[CoreGame.DOWN]!=null){
			if(p.links[CoreGame.LEFT]!=null){
				if(p.links[CoreGame.RIGHT]!=null){
					if(p.links[CoreGame.UP]!=null)
						return game.A.down_left_right_up;
					return game.A.down_left_right;
				}
				if(p.links[CoreGame.UP]!=null)
					return game.A.down_left_up;
				return game.A.down_left;
			}
			if(p.links[CoreGame.RIGHT]!=null){
				if(p.links[CoreGame.UP]!=null)
					return game.A.down_right_up;
				return game.A.down_right;
			}
			if(p.links[CoreGame.UP]!=null)
				return game.A.down_up;
			return game.A.down;
		}
		if(p.links[CoreGame.LEFT]!=null){
			if(p.links[CoreGame.RIGHT]!=null){
				if(p.links[CoreGame.UP]!=null)
					return game.A.left_right_up;
				return game.A.left_right;
			}
			if(p.links[CoreGame.UP]!=null)
				return game.A.left_up;
			return game.A.left;
		}
		if(p.links[CoreGame.RIGHT]!=null){
			if(p.links[CoreGame.UP]!=null)
				return game.A.right_up;
			return game.A.right;
		}
		if(p.links[CoreGame.UP]!=null)
			return game.A.up;
		return game.A.text;
	}

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
		if(autosav)
			CoreGame.saveGame(G, "cde.tmp");
    }

}
