package com.roguelike.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.roguelike.game.Game.*;

public class MyGdxGame extends ApplicationAdapter {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
    Game G;
    Assets A;
    
    boolean sav;
    boolean load;
    
	@Override
	public void create () {
		
		sav =true;
		load = false;
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
		Color color = batch.getColor();
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
					batch.draw(t, t.getWidth()*x, t.getHeight()*y);
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
					batch.setColor(color.r, color.g, color.b, 0.05f);
					batch.draw(t, t.getWidth()*x, t.getHeight()*y);
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
				batch.setColor(color.r, color.g, color.b, 1);
				//batch.draw(A.dotclear, t.getWidth()*x, t.getHeight()*y);
			}
		}
		batch.draw(A.text2, 
				A.text2.getWidth()*G.player.position[0], 
				A.text2.getHeight()*G.player.position[1]);
//		for(Path.Node a : G.e.path.path){
//			batch.draw(A.dropImage_small, 
//					A.text2.getWidth()*a.place.position[0]+12, 
//					A.text2.getHeight()*a.place.position[1]+12);
//		}
		
		batch.draw(A.dotclear, 
				A.dotclear.getWidth()*G.level.exit[0], 
				A.dotclear.getHeight()*G.level.exit[1]);
		
		color = batch.getColor();
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
						batch.draw(A.red_unit, 
								A.red_unit.getWidth()*o.position[0], 
								A.red_unit.getHeight()*o.position[1]);
						A.font.draw(batch, Integer.toString(o.hp),
								A.red_unit.getWidth()*o.position[0]+16,
								A.red_unit.getHeight()*o.position[1]+16);
						}
					}
				} else if(p.lastseen != null){
					t = true;
					batch.setColor(color.r, color.g, color.b, 0.05f);
				}
				if(t){
					for(Obj o : p.obj){
						if(o instanceof Potion)
							batch.draw(A.dropImage_small, 
								32*x+12, 
								32*y+12);
						else if(o instanceof PUvision)
							batch.draw(A.power_green, 
								32*x, 
								32*y);
						else if(o instanceof PUcloak)
							batch.draw(A.power_blue, 
								32*x, 
								32*y);
					}
				}
				batch.setColor(color.r, color.g, color.b, 1);
				//batch.draw(A.dotclear, t.getWidth()*x, t.getHeight()*y);
			}
		}
		
		A.font.draw(batch, Integer.toString(G.player.hp),650,400);
		
		for(int i = 0,d=420; i<3; i++,d-=20){
			if(G.player.powers[i]!=null){
				A.font.draw(batch, G.player.powers[i].name,750,d);
				A.font.draw(batch, Integer.toString(G.player.powers[i].charges),700,d);
			}
		}
		
		A.font.draw(batch, Integer.toString(G.level.exit[0]),700,300);
		A.font.draw(batch, Integer.toString(G.level.exit[1]),720,300);
		batch.end();
		
		if(Gdx.input.isKeyJustPressed(Keys.NUM_1)) 
			G.player.usePower(0);
		if(Gdx.input.isKeyJustPressed(Keys.NUM_2)) 
			G.player.usePower(1);
		if(Gdx.input.isKeyJustPressed(Keys.NUM_3)) 
			G.player.usePower(2);
		
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
