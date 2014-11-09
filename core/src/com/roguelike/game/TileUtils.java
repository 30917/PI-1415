package com.roguelike.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public final class TileUtils {
	
	private TileUtils(){};
	
	
	public static void fillTileLayer(Texture text, int w, int h, String name, TiledMap tiledMap){
		boolean visible = true;
		float opacity = 1.0f;
		
		TextureRegion region = new TextureRegion(text);
		
		
		
		int tileWidth = region.getRegionWidth();
		int tileHeight = region.getRegionHeight();
		
		TiledMapTileLayer layer = new TiledMapTileLayer(w, h, tileWidth, tileHeight);
		layer.setVisible(visible);
		layer.setOpacity(opacity);
		layer.setName(name);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int id = y * w + x;
				boolean flipHorizontally = false;
				boolean flipVertically = false;
				boolean flipDiagonally = false;
	
				StaticTiledMapTile tile = new StaticTiledMapTile(new TextureRegion(text));
				tile.setId(id);
				
				if (tile != null) {
					Cell cell = createTileLayerCell(flipHorizontally, flipVertically, flipDiagonally);
					cell.setTile(tile);
					layer.setCell(x, h - 1 - y, cell);
				}
			}
		}
		
//		Element properties = element.getChildByName("properties");
//		if (properties != null) {
//			loadProperties(layer.getProperties(), properties);
//		}
		tiledMap.getLayers().add(layer);
	}
	
	public static Cell createTileLayerCell (boolean flipHorizontally, boolean flipVertically, boolean flipDiagonally) {
		Cell cell = new Cell();
		if (flipDiagonally) {
			if (flipHorizontally && flipVertically) {
				cell.setFlipHorizontally(true);
				cell.setRotation(Cell.ROTATE_270);
			} else if (flipHorizontally) {
				cell.setRotation(Cell.ROTATE_270);
			} else if (flipVertically) {
				cell.setRotation(Cell.ROTATE_90);
			} else {
				cell.setFlipVertically(true);
				cell.setRotation(Cell.ROTATE_270);
			}
		} else {
			cell.setFlipHorizontally(flipHorizontally);
			cell.setFlipVertically(flipVertically);
		}
		return cell;
	}
	
	
	public static int[][] importMap(String name, TiledMap tiledMap) throws IOException {

		FileHandle handler = Gdx.files.internal(name);
		
		BufferedReader reader = handler.reader(100);
		String line = reader.readLine();
		StringTokenizer st = new StringTokenizer(line);
		String token="fail";
		
//		st.countTokens() e reader.mark() + reader.readLine()
//		para conseguir o tamanho do mapa
		
		
		
		//TODO define textures
		
		TextureRegion region = new TextureRegion(MyGdxGame.text);
		TextureRegion region2 = new TextureRegion(MyGdxGame.text2);
		
		TextureRegion down_left_right_up = new TextureRegion(MyGdxGame.down_left_right_up);
		TextureRegion down_left_right = new TextureRegion(MyGdxGame.down_left_right);
		TextureRegion down_left_up = new TextureRegion(MyGdxGame.down_left_up);
		TextureRegion down_left = new TextureRegion(MyGdxGame.down_left);
		TextureRegion down_right_up = new TextureRegion(MyGdxGame.down_right_up);
		TextureRegion down_right = new TextureRegion(MyGdxGame.down_right);
		TextureRegion down_up = new TextureRegion(MyGdxGame.down_up);
		TextureRegion down = new TextureRegion(MyGdxGame.down);
		TextureRegion left_right_up = new TextureRegion(MyGdxGame.left_right_up);
		TextureRegion left_right = new TextureRegion(MyGdxGame.left_right);
		TextureRegion left_up = new TextureRegion(MyGdxGame.left_up);
		TextureRegion left = new TextureRegion(MyGdxGame.left);
		TextureRegion right_up = new TextureRegion(MyGdxGame.right_up);
		TextureRegion right = new TextureRegion(MyGdxGame.right);
		TextureRegion up = new TextureRegion(MyGdxGame.up);
		
		
		//map size
		int w = Integer.parseInt(st.nextToken());
		int h = Integer.parseInt(st.nextToken());
		
		int tileWidth = region.getRegionWidth();
		int tileHeight = region.getRegionHeight();
		
		
		boolean visible = true;
		float opacity = 1.0f;
		
		TiledMapTileLayer layer = new TiledMapTileLayer(w, h, tileWidth, tileHeight);
		layer.setVisible(visible);
		layer.setOpacity(opacity);
		layer.setName(name);
		
		int[] entry = new int[2];
		int[] exit = new int[2];
		
		// ler as linhas
		for (int linha = 0; (line = reader.readLine()) != null; linha++) {
			st = new StringTokenizer(line);
			for (int coluna=0; st.hasMoreTokens(); coluna++) {
				token = st.nextToken();
				if (token.startsWith("(")) {
					st = new StringTokenizer(token," (),");
					entry[0] = Integer.parseInt(st.nextToken());
					entry[1] = Integer.parseInt(st.nextToken());
					line = reader.readLine();
					st = new StringTokenizer(line, " (),");
					exit[0] = Integer.parseInt(st.nextToken());
					exit[1] = Integer.parseInt(st.nextToken());
					break;
				}
				
				//int id = Integer.parseInt(token);
				boolean flipHorizontally = false;
				boolean flipVertically = false;
				boolean flipDiagonally = false;
	
				StaticTiledMapTile tile;
				
				if(token.equals("0"))
					tile = new StaticTiledMapTile(region);
				else if(token.equals("+"))
					tile = new StaticTiledMapTile(down_left_right_up);
				else if(token.equals("T"))
					tile = new StaticTiledMapTile(down_left_right);
				else if(token.equals("{"))
					tile = new StaticTiledMapTile(down_left_up);
				else if(token.equals("7"))
					tile = new StaticTiledMapTile(down_left);
				else if(token.equals("}"))
					tile = new StaticTiledMapTile(down_right_up);
				else if(token.equals("F"))
					tile = new StaticTiledMapTile(down_right);
				else if(token.equals("|"))
					tile = new StaticTiledMapTile(down_up);
				else if(token.equals("v"))
					tile = new StaticTiledMapTile(down);
				else if(token.equals("A"))
					tile = new StaticTiledMapTile(left_right_up);
				else if(token.equals("-"))
					tile = new StaticTiledMapTile(left_right);
				else if(token.equals("J"))
					tile = new StaticTiledMapTile(left_up);
				else if(token.equals("<"))
					tile = new StaticTiledMapTile(left);
				else if(token.equals("L"))
					tile = new StaticTiledMapTile(right_up);
				else if(token.equals(">"))
					tile = new StaticTiledMapTile(right);
				else if(token.equals("^"))
					tile = new StaticTiledMapTile(up);
				else
					tile = new StaticTiledMapTile(region2);
				//tile.setId(id);
				
				if (tile != null) {
					Cell cell = TileUtils.createTileLayerCell(flipHorizontally, flipVertically, flipDiagonally);
					cell.setTile(tile);
					layer.setCell(coluna, h - 1 - linha, cell);
				}
			}
		}
		
//			Element properties = element.getChildByName("properties");
//			if (properties != null) {
//				loadProperties(layer.getProperties(), properties);
//			}
		tiledMap.getLayers().add(layer);
		
		int[][] doors = {entry,exit};
		
		return doors;
	}	
	

}
