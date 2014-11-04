package com.roguelike.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
	
	
	public static void importMap(String name, TiledMap tiledMap) throws IOException {

		FileHandle handler = Gdx.files.internal(name);
		
		BufferedReader reader = handler.reader(100);
		String line = reader.readLine();
		StringTokenizer st = new StringTokenizer(line);
		
//		st.countTokens() e reader.mark() + reader.readLine()
//		para conseguir o tamanho do mapa
		
		
		
		//TODO define textures
		
		Texture text = new Texture("pattern.png");
		TextureRegion region = new TextureRegion(text);
		
		
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

		try {
			// ler as linhas
			for (int linha = 0; (line = reader.readLine()) != null; linha++) {
				st = new StringTokenizer(line);
				for (int coluna=0; st.hasMoreTokens(); coluna++) {
					String token = st.nextToken();
					if (token.startsWith("(")) {
//						StringTokenizer start_end = new StringTokenizer(token," (),");
//						entry = list.getNode(
//								Integer.parseInt(start_end.nextToken()),
//								Integer.parseInt(start_end.nextToken()));
//						entry.direction = "inicio";
//						line = reader.readLine();
//						start_end = new StringTokenizer(line, " (),");
//						exit = list.getNode(
//								Integer.parseInt(start_end.nextToken()),
//								Integer.parseInt(start_end.nextToken()));
						break;
					}
					//list.addNode(token.charAt(0));
					
					int id = linha * w + coluna;
					boolean flipHorizontally = false;
					boolean flipVertically = false;
					boolean flipDiagonally = false;
		
					StaticTiledMapTile tile = new StaticTiledMapTile(new TextureRegion(text));
					tile.setId(id);
					
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
			
			

		} catch (FileNotFoundException e) {	e.printStackTrace();
		} catch (IOException e) {	e.printStackTrace();
		} finally {
			if (reader != null) {
				try {	reader.close();
				} catch (IOException e) {	e.printStackTrace();
				}}}
	}	
	

}
