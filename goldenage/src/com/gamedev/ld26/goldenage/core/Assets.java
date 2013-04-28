package com.gamedev.ld26.goldenage.core;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Assets {
	
	public static Random random;
	public static SpriteBatch batch;
	public static ShapeRenderer shapes;
	
	public static Texture sheet;
	public static Texture background;
	
	public static Sound beep;
	
	public static TextureRegion[][] letters;
	public static TextureRegion[][] digits;
	public static TextureRegion[][] symbols;
	
	public static void load() {
		random = new Random();
		batch  = new SpriteBatch();
		shapes = new ShapeRenderer();
		
		sheet  = new Texture(Gdx.files.internal("data/spritesheet.png"));
		sheet.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		background = new Texture(Gdx.files.internal("data/background.png"));
		background.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);	
		
		beep = Gdx.audio.newSound(Gdx.files.internal("data/audio/beep.wav"));
		
		letters = splitAndGet(sheet, 8, 8, 0, 30, 26, 1);
		digits  = splitAndGet(sheet, 8, 8, 0, 31, 10, 1);
		symbols = splitAndGet(sheet, 8, 8, 10, 31, 18, 1);
	}
	
	public static void dispose() {
		background.dispose();
		sheet.dispose();
		batch.dispose();
	}
	
	private static TextureRegion[][] splitAndGet(Texture texture, int width, int height, int col, int row, int xTiles, int yTiles) {
		TextureRegion[][] allRegions = TextureRegion.split(texture, width, height);
		TextureRegion[][] regions = new TextureRegion[yTiles][xTiles];
		for (int y = 0; y < yTiles; ++y) {
			for (int x = 0; x < xTiles; ++x) {
				regions[y][x] = allRegions[row + y][col + x];
			}
		}
		return regions;
	}
	
}
